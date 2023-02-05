package com.ae.netty.ch9;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import static com.ae.netty.ch9.Constant.PORT;

public class Server {

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup businessGroup = new NioEventLoopGroup(1000);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(new FixedLengthFrameDecoder(Long.BYTES));
                // 方式一:handler中处理自己的业务
                //ch.pipeline().addLast(ServerBusinessHandler.INSTANCE);

                // 方式二:使用自己的线程池来处理
                ch.pipeline().addLast(ServerBusinessThreadPoolHandler.INSTANCE);

                // 方式三:直接指定线程池
                //ch.pipeline().addLast(businessGroup, ServerBusinessHandler.INSTANCE);
            }
        });


        bootstrap.bind(PORT).addListener((ChannelFutureListener) future -> System.out.println("bind success in port: " + PORT));
    }

}
