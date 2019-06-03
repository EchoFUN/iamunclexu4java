package com.iamunclexu.controllers;

import com.iamunclexu.confs.TemplateConf;
import com.iamunclexu.database.ModelLink;
import com.iamunclexu.database.ModelMenu;
import com.iamunclexu.database.ModelMicroBlogs;
import com.iamunclexu.database.ModelPost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import static com.iamunclexu.confs.SysConf.PAGE_COUNT;

public class HomeController extends Controller {
    private static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Override
    public HttpResponse process(HttpRequest request) {
        Map<String, Object> root = new HashMap<>();

        String output;
        ModelPost modelPost = new ModelPost();

        int pager;
        try {
            pager = Integer.parseInt(queryData.get("p"));
        } catch (Exception e) {
            pager = 0;
        }
        int starter = pager * PAGE_COUNT;
        List<Map<String, String>> posts = modelPost.fetchPostsByPager(starter);

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
        root.put("menus", (new ModelMenu()).fetchMenus());
        root.put("microblogs", (new ModelMicroBlogs()).fetchMicroBlogs());
        root.put("links", (new ModelLink()).fetchLinks());
        root.put("recent_post", modelPost.fetchRecentPost());

        int counter = modelPost.fetchPostCount();
        if ((pager + 1) * PAGE_COUNT <= counter) {
            root.put("has_next", true);
            root.put("has_prev", false);
        }
        if ((pager) != 0) {
            root.put("has_prev", true);
            root.put("has_next", false);
        }
        root.put("current", pager);

        Configuration configuration = TemplateConf.fetchConfiguration();
        Template template = null;
        try {
            template = configuration.getTemplate("home.ftl");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        StringWriter stringWriter = new StringWriter();
        try {
            template.process(root, stringWriter);
        } catch (TemplateException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        output = stringWriter.toString();
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(output.getBytes()));
    }
}
