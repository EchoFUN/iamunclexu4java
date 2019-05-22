package com.iamunclexu.controllers;

import com.iamunclexu.confs.TemplateConf;
import com.iamunclexu.database.DBUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
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
        String output = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id, title, content from post");

            while (resultSet.next()) {
                output += String.valueOf(resultSet.getInt("id"));
            }


            Configuration configuration = TemplateConf.fetchConfiguration();
            Template template = configuration.getTemplate("home.ftl");

            Map<String, String> root = new HashMap<>();
            root.put("name", "cxl");
            root.put("age", "25");
            StringWriter stringWriter = new StringWriter();
            template.process(root, stringWriter);

            output = stringWriter.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    DBUtils.releaseConnection(connection);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(output.getBytes()));
    }
}
