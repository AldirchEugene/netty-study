package com.ae.netty.ch6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public class OutBoundHandlerA extends ChannelOutboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("OutBoundHandlerA.exceptionCaught()");
        ctx.fireExceptionCaught(cause);
    }
}
