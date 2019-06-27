package com.iamunclexu.controllers;

import com.iamunclexu.confs.TemplateConf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public abstract class Controller {
    private static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    Map<String, String> queryData;

    public abstract HttpResponse process(HttpRequest request);

    public String render(String tpl, Map<String, Object> root) {
        Configuration configuration = TemplateConf.fetchConfiguration();
        StringWriter builder = new StringWriter();

        try {
            configuration.getTemplate(tpl).process(root, builder);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return builder.toString();
    }

    public void setQueryData(Map<String, String> queryData) {
        this.queryData = queryData;
    }
}
