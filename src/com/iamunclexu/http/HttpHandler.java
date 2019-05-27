package com.iamunclexu.http;

import com.iamunclexu.confs.RequestConf;
import com.iamunclexu.controllers.Controller;
import com.iamunclexu.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;

import static com.iamunclexu.confs.Constant.CONTENT_TYPE_CSS;
import static com.iamunclexu.confs.Constant.CONTENT_TYPE_HTML;
import static com.iamunclexu.confs.Constant.CONTENT_TYPE_IMAGE_JPG;
import static com.iamunclexu.confs.Constant.CONTENT_TYPE_JAVASCRIPT;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONNECTION;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONTENT_LENGTH;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> { // 1
    private static Logger LOGGER = LoggerFactory.getLogger(HttpHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        String requestUri = request.uri();

        Controller handler = RequestConf.inst().fetchControllerByUrl(requestUri);
        DefaultFullHttpResponse response = (DefaultFullHttpResponse) handler.process(request);

        HttpHeaders heads = response.headers();
        heads.add(CONTENT_TYPE, CONTENT_TYPE_HTML);
        heads.add(CONTENT_LENGTH, response.content().readableBytes());
        heads.add(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        if (Utils.isStaticUri(requestUri)) {


            /*
            if (requestUri.contains(".js")) {
                heads.set(CONTENT_TYPE, CONTENT_TYPE_JAVASCRIPT);
            }
            if (requestUri.contains(".css")) {
                heads.set(CONTENT_TYPE, CONTENT_TYPE_CSS);
            }
            if (requestUri.contains(".jpg")) {
                heads.set(CONTENT_TYPE, CONTENT_TYPE_IMAGE_JPG);
            }
            */

        }
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
