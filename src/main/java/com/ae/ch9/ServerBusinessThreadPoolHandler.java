package com.ae.ch9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@ChannelHandler.Sharable
public class ServerBusinessThreadPoolHandler extends ServerBusinessHandler {

    public static final ChannelHandler INSTANCE = new ServerBusinessThreadPoolHandler();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(1000);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        ByteBuf data = Unpooled.directBuffer();
        data.writeBytes(msg);
        threadPool.submit(() -> {
            Object result = getResult(data);
            ctx.channel().writeAndFlush(result);
        });
    }

    protected Object getResult(ByteBuf data) {
        // 90.0% == 1ms
        // 95.0% == 10ms  1000 50 > 10ms
        // 99.0% == 100ms 1000 10 > 100ms
        // 99.9% == 1000ms1000 1 > 1000ms
        int level = ThreadLocalRandom.current().nextInt(1, 1000);

        int time;
        if (level <= 900) {
            time = 1;
        } else if (level <= 950) {
            time = 10;
        } else if (level <= 990) {
            time = 100;
        } else {
            time = 1000;
        }

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }

        return data;
    }
}
