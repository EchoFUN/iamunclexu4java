package com.iamunclexu.controllers;

import com.iamunclexu.database.CommentModel;
import com.iamunclexu.database.LinkModel;
import com.iamunclexu.database.MenuModel;
import com.iamunclexu.database.MicroBlogsModel;
import com.iamunclexu.database.PostModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        PostModel postModel = new PostModel();
        Map<String, Object> root = new HashMap<>();
        int postId = Integer.parseInt(queryData.get("id"));

        Map<String, String> post = postModel.fetchPostByID(postId);
        if (post != null) {
            for (String key : post.keySet()) {
                if (key == "date") {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
                    Date date = new Date(Long.parseLong(post.get("date")));
                    post.put("date", sf.format(date));
                    break;
                }
            }
            root.put("post_details", post);
        }
        root.put("menus", (new MenuModel()).fetchMenus());
        root.put("microblogs", (new MicroBlogsModel()).fetchMicroBlogs());
        root.put("links", (new LinkModel()).fetchLinks());
        root.put("recent_post", postModel.fetchRecentPost());
        root.put("comments", (new CommentModel()).fetchCListByPost(postId));
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(render("post.ftl", root).getBytes()));
    }
}
