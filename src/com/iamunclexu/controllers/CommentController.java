package com.iamunclexu.controllers;

import com.iamunclexu.database.CommentModel;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class CommentController extends Controller {

    @Override
    public HttpResponse process(HttpRequest request) {
        CommentModel commentModel = new CommentModel();
        String result = commentModel.saveComment(this.queryData);


        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(result.getBytes()));
    }
}
