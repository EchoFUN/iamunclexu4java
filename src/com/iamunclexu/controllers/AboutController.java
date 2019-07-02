package com.iamunclexu.controllers;

import com.iamunclexu.database.LinkModel;
import com.iamunclexu.database.MenuModel;
import com.iamunclexu.database.MicroBlogsModel;
import com.iamunclexu.database.PostModel;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class AboutController extends Controller {
    @Override
    public HttpResponse process(HttpRequest request) {
        Map<String, Object> root = new HashMap<>();
        root.put("menus", (new MenuModel()).fetchMenus());
        root.put("microblogs", (new MicroBlogsModel()).fetchMicroBlogs());
        root.put("links", (new LinkModel()).fetchLinks());
        root.put("recent_post", (new PostModel()).fetchRecentPost());
        root.put("url", "/about");
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(render("about.ftl", root).getBytes()));
    }
}
