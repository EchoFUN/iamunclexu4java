package com.iamunclexu.controllers;

import com.iamunclexu.database.LinkModel;
import com.iamunclexu.database.MenuModel;
import com.iamunclexu.database.MicroBlogsModel;
import com.iamunclexu.database.PostModel;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.util.HashMap;
import java.util.Map;

public class NFTController extends Controller {

  PostModel postModel = new PostModel();

  @Override
  public HttpResponse process(HttpRequest request) {
    Map<String, Object> root = new HashMap<>();
    root.put("menus", (new MenuModel()).fetchMenus());
    root.put("title", "NFT");
    root.put("microblogs", (new MicroBlogsModel()).fetchMicroBlogs());
    root.put("links", (new LinkModel()).fetchLinks());
    root.put("recent_post", postModel.fetchRecentPost());
    root.put("archived", postModel.fetchArchived());
    root.put("url", "/nft");
    return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(render("nft.ftl", root).getBytes()));
  }
}
