package com.iamunclexu.controllers;

import com.iamunclexu.database.ModelPost;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class PostController extends Controller {

    @Override
    public HttpResponse process(HttpRequest request) {
        Map<String, Object> root = new HashMap<>();
        int postId = Integer.parseInt(queryData.get("id"));

        root.put("post_details", (new ModelPost()).fetchPostByID(postId));
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(render("post.ftl", root).getBytes()));
    }
}
