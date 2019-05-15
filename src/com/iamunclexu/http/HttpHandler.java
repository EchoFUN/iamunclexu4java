package com.iamunclexu.http;

import com.iamunclexu.confs.RequestConf;
import com.iamunclexu.controllers.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;

import static com.iamunclexu.confs.RequestConf.inst;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> { // 1
    private static Logger LOGGER = LoggerFactory.getLogger(HttpHandler.class);

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        Controller handler = RequestConf.inst().fetchControllerByUrl(request.getUri());
        DefaultFullHttpResponse response = (DefaultFullHttpResponse) handler.process(request);

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("exceptionCaught");
        if (null != cause) cause.printStackTrace();
        if (null != ctx) ctx.close();
    }
}
