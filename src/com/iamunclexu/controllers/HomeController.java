package com.iamunclexu.controllers;

import com.iamunclexu.confs.TemplateConf;
import com.iamunclexu.database.ModelMenu;
import com.iamunclexu.database.ModelPost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
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

public class HomeController extends Controller {
    private static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Override
    public HttpResponse process(HttpRequest request) {
        String output;

        String pager = queryData.get("p");
        LOGGER.info("Current pager is : " + pager);




        List<Map<String, String>> posts = (new ModelPost()).fetchPosts();
        List<Map<String, String>> menus = (new ModelMenu()).fetchMenus();

        Configuration configuration = TemplateConf.fetchConfiguration();
        Template template = null;
        try {
            template = configuration.getTemplate("home.ftl");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        Map<String, List<Map<String, String>>> root = new HashMap<>();
        root.put("posts", posts);
        root.put("menus", menus);
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
