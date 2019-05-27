package com.iamunclexu.controllers;

import com.iamunclexu.confs.TemplateConf;
import com.iamunclexu.database.DBUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            // Post Data .
            resultSet = statement.executeQuery("select id, title, content from post");
            List<Map<String, String>> posts = new ArrayList();
            while (resultSet.next()) {
                Map<String, String> fieldDataSet = new HashMap<>();
                fieldDataSet.put("id", String.valueOf(resultSet.getInt("id")));
                fieldDataSet.put("title", resultSet.getString("title"));
                posts.add(fieldDataSet);
            }

            // Menu data .
            resultSet = statement.executeQuery("select id, title, url from menu");
            List<Map<String, String>> menus = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> fieldDataSet = new HashMap<>();
                fieldDataSet.put("id", String.valueOf(resultSet.getInt("id")));
                fieldDataSet.put("title", resultSet.getString("title"));
                fieldDataSet.put("url", resultSet.getString("url"));
                menus.add(fieldDataSet);
            }


            Configuration configuration = TemplateConf.fetchConfiguration();
            Template template = configuration.getTemplate("home.ftl");

            Map<String, List<Map<String, String>>> root = new HashMap<>();
            root.put("posts", posts);
            root.put("menus", menus);
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
