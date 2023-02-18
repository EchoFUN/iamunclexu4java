package com.iamunclexu.controllers;

import com.iamunclexu.database.CommentModel;
import com.iamunclexu.database.LinkModel;
import com.iamunclexu.database.MenuModel;
import com.iamunclexu.database.MicroBlogsModel;
import com.iamunclexu.database.PostModel;
import com.iamunclexu.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.*;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class PostController extends Controller {
  PostModel postModel = new PostModel();
  CommentModel commentModel = new CommentModel();

  @Override
  public HttpResponse process(HttpRequest request) {
    Map<String, Object> root = new HashMap<>();
    int postId = Integer.parseInt(queryData.get("id"));

    Map<String, String> post = postModel.fetchPostByID(postId);
    if (post != null) {
      for (String key : post.keySet()) {
        if (Objects.equals(key, "date")) {
          post.put("date", Utils.dateFormatter(post.get("date")));
          break;
        }
      }
      post.put("counter", String.valueOf(commentModel.queryCounterByPost(Integer.parseInt(post.get("id")))));
      root.put("post_details", post);
      root.put("title", post.get("title"));
    }
    List<Map<String, String>> comments = (new CommentModel()).fetchCListByPost(postId);
    for (int i = 0; i < comments.size(); i++) {
      Map<String, String> comment = comments.get(i);
      comment.put("formatted", Utils.dateFormatter(comment.get("date"), "yyyy.MM.dd"));
    }

    root.put("menus", (new MenuModel()).fetchMenus());
    root.put("microblogs", (new MicroBlogsModel()).fetchMicroBlogs());
    root.put("links", (new LinkModel()).fetchLinks());
    root.put("recent_post", postModel.fetchRecentPost());
    root.put("archived", postModel.fetchArchived());
    root.put("comments", comments);
    return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(render("post.ftl", root).getBytes()));
  }
}
