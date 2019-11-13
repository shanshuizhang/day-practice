package com.zss.mini.http.core.handle.response;

import com.zss.mini.http.util.CommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.RandomAccessFile;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 16:29
 */
public class ResponCoreHandle {

    public static FullHttpResponse responseHtml(HttpResponseStatus status,String result){
        ByteBuf content = copiedBuffer(result, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,content);
        if(content != null){
            response.headers().set("Content-Type", "text/html;charset=UTF-8");
            response.headers().set("Content_Length", response.content().readableBytes());
        }
        return response;
    }

    /**
     * 响应html请求给客户端
     *
     * @return
     */
    public static FullHttpResponse response404Html() throws IOException {
        String result = CommonUtil.read404Html();
        ByteBuf content = copiedBuffer(result, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        if (content != null) {
            response.headers().set("Content-Type", "text/html;charset=UTF-8");
            response.headers().set("Content_Length", response.content().readableBytes());
        }
        return response;
    }

    /**
     * 响应js请求给客户端
     *
     * @param status
     * @param result
     * @return
     */
    public static FullHttpResponse responseJs(HttpResponseStatus status, String result) {
        ByteBuf content = copiedBuffer(result, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        if (content != null) {
            response.headers().set("Content-Type", "application/x-javascript");
            response.headers().set("Content_Length", response.content().readableBytes());
        }
        return response;
    }

    /**
     * 响应css请求给客户端
     *
     * @param status
     * @param result
     * @return
     */
    public static FullHttpResponse responseCss(HttpResponseStatus status, String result) {
        ByteBuf content = copiedBuffer(result, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        if (content != null) {
            response.headers().set("Content-Type", "text/css; charset=UTF-8");
            response.headers().set("Content_Length", response.content().readableBytes());
        }
        return response;
    }

    /**
     * 响应图片请求给客户端
     *
     * @param status
     * @param content
     * @return
     */
    public static FullHttpResponse responsePic(HttpResponseStatus status, ByteBuf content) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        if (content != null) {
            response.headers().set("Content-Type", "image/png");
            response.headers().set("Content_Length", response.content().readableBytes());
        }
        return response;
    }


    /**
     * 响应text信息
     *
     * @param status
     * @param content
     * @return
     */
    public static FullHttpResponse responseText(HttpResponseStatus status, ByteBuf content) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        if (content != null) {
            response.headers().set("Content-Type", "text/plain;charset=UTF-8");
            response.headers().set("Content_Length", response.content().readableBytes());
        }
        return response;
    }

    /**
     * 响应json格式数据
     *
     * @param status
     * @param content
     * @return
     */
    public static FullHttpResponse responseJson(HttpResponseStatus status, ByteBuf content) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        if (content != null) {
            response.headers().set("Content-Type", "application/json;charset=UTF-8");
            response.headers().set("Content_Length", response.content().readableBytes());
        }
        return response;
    }


    /**
     * 写图片
     *
     * @param path
     * @param ctx
     * @param request
     * @throws IOException
     */
    public static void writePic(String path, ChannelHandlerContext ctx, FullHttpRequest request) throws IOException {
        RandomAccessFile file = new RandomAccessFile(CommonUtil.readFile(path), "r");
        HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);

        boolean keepAlive = HttpHeaders.isKeepAlive(request);

        if (keepAlive) {
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ctx.write(response);
        if (ctx.pipeline().get(SslHandler.class) == null) {
            ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
        } else {
            ctx.write(new ChunkedNioFile(file.getChannel()));
        }

        // 写入文件尾部
        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
        file.close();
    }
}
