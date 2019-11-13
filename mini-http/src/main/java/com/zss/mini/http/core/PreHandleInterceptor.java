package com.zss.mini.http.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/7 17:17
 */
public abstract class PreHandleInterceptor extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext cxt, Object msg) {
        preHandle(cxt,(FullHttpRequest)msg);
        cxt.fireChannelRead(msg);
    }

    protected abstract void preHandle(ChannelHandlerContext cxt, FullHttpRequest fullHttpRequest);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        completeHandle();
    }

    protected abstract void completeHandle();
}
