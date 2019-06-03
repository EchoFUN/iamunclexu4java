package com.iamunclexu.controllers;

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

import static com.iamunclexu.confs.SysConf.PAGE_COUNT;

public class HomeController extends Controller {

    @Override
    public HttpResponse process(HttpRequest request) {
        Map<String, Object> root = new HashMap<>();
        PostModel postModel = new PostModel();

        int pager;
        try {
            pager = Integer.parseInt(queryData.get("p"));
        } catch (Exception e) {
            pager = 0;
        }
        int starter = pager * PAGE_COUNT;
        List<Map<String, String>> posts = postModel.fetchPostsByPager(starter);

        for (int i = 0; i < posts.size(); i++) {
            Map<String, String> post = posts.get(i);
            search:
            for (String key : post.keySet()) {
                if (key == "date") {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
                    Date date = new Date(Long.parseLong(post.get("date")));
                    post.put("date", sf.format(date));
                    break search;
                }
            }
        }
        root.put("posts", posts);
        root.put("menus", (new MenuModel()).fetchMenus());
        root.put("microblogs", (new MicroBlogsModel()).fetchMicroBlogs());
        root.put("links", (new LinkModel()).fetchLinks());
        root.put("recent_post", postModel.fetchRecentPost());

        int counter = postModel.fetchPostCount();
        if ((pager + 1) * PAGE_COUNT <= counter) {
            root.put("has_next", true);
            root.put("has_prev", false);
        }
        if ((pager) != 0) {
            root.put("has_prev", true);
            root.put("has_next", false);
        }
        root.put("current", pager);
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(render("home.ftl", root).getBytes()));
    }
}
