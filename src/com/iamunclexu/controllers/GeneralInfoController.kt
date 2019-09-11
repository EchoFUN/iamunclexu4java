package com.iamunclexu.controllers

import com.google.gson.JsonObject
import io.netty.buffer.Unpooled
import io.netty.handler.codec.http.*
import org.slf4j.LoggerFactory

import io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE

class GeneralInfoController : Controller() {
    private var logger = LoggerFactory.getLogger(GeneralInfoController::class.java)

    override fun process(request: HttpRequest?): HttpResponse {
        logger.info("hi")
        var json = JsonObject()
        json.addProperty("2", 2)


        var response = DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(json.toString().toByteArray()))
        val headers = response.headers()
        headers.add(CONTENT_TYPE, "application/json; charset=utf-8")

        return response
    }
}
