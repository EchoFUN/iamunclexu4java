package com.iamunclexu.http

import com.iamunclexu.confs.RequestConf
import com.iamunclexu.controllers.Controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.DefaultFullHttpResponse
import io.netty.handler.codec.http.FullHttpRequest
import io.netty.handler.codec.http.HttpHeaderValues
import io.netty.handler.codec.http.HttpHeaders

import com.iamunclexu.confs.Constant.CONTENT_TYPE_HTML
import io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE
import io.netty.handler.codec.rtsp.RtspHeaderNames.CONNECTION
import io.netty.handler.codec.rtsp.RtspHeaderNames.CONTENT_LENGTH

class HttpHandler : SimpleChannelInboundHandler<FullHttpRequest>() { // 1


    override fun channelRead0(ctx: ChannelHandlerContext, request: FullHttpRequest) {
        val requestUri = request.uri()

        val handler = RequestConf.inst().fetchControllerByUrl(requestUri)
        val response = handler?.process(request) as DefaultFullHttpResponse

        val headers = response.headers()
        if (headers.get(CONTENT_TYPE) == null) {
            headers.add(CONTENT_TYPE, CONTENT_TYPE_HTML)
        }
        headers.add(CONTENT_LENGTH, response.content().readableBytes())
        headers.add(CONNECTION, HttpHeaderValues.KEEP_ALIVE)
        ctx.write(response)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        // LOGGER.info("channelReadComplete");
        try {
            super.channelReadComplete(ctx)
        } catch (e: Exception) {
            LOGGER.error(e.message)
        }

        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable?) {
        LOGGER.info("exceptionCaught")
        cause?.printStackTrace()
        ctx?.close()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(HttpHandler::class.java)
    }
}
