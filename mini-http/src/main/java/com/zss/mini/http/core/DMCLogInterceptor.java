package com.zss.mini.http.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/7 17:22
 */
public class DMCLogInterceptor extends PreHandleInterceptor {

    private static final String SESSION_KEY = "sessionId";
    @Override
    public void preHandle(ChannelHandlerContext cxt, FullHttpRequest fullHttpRequest) {
        String token = UUID.randomUUID().toString();
        MDC.put(SESSION_KEY,token);
    }

    @Override
    public void completeHandle() {
        MDC.remove(SESSION_KEY);
    }
}
