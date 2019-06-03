package com.iamunclexu.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iamunclexu.confs.SysConf.PAGE_COUNT;

public class ModelPost {
    private static Logger LOGGER = LoggerFactory.getLogger(ModelPost.class);

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public List<Map<String, String>> fetchPostsByPager(int starter) {
        List<Map<String, String>> posts = new ArrayList();

        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();

            // ModelPost Data .
            resultSet = statement.executeQuery("select id, title, content, author, date from post where visiable = 1 order by date desc  limit " + PAGE_COUNT + " offset " + starter);
            while (resultSet.next()) {
                Map<String, String> fieldDataSet = new HashMap<>();
                fieldDataSet.put("id", String.valueOf(resultSet.getInt("id")));
                fieldDataSet.put("title", resultSet.getString("title"));
                fieldDataSet.put("content", resultSet.getString("content"));
                fieldDataSet.put("date", resultSet.getString("date"));
                fieldDataSet.put("author", resultSet.getString("author"));
                posts.add(fieldDataSet);
            }

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
        return posts;
    }


    public int fetchPostCount() {
        int counter = 0;
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select count(*) as counter from post where visiable = 1");
            while (resultSet.next()) {
                counter = resultSet.getInt("counter");
            }
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
        return counter;
    }

    public List<Map<String, String>> fetchRecentPost() {
        List<Map<String, String>> posts = new ArrayList();

        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();

            // ModelPost Data .
            resultSet = statement.executeQuery("select id, title from post where visiable = 1 order by date desc  limit 10");
            while (resultSet.next()) {
                Map<String, String> fieldDataSet = new HashMap<>();
                fieldDataSet.put("id", String.valueOf(resultSet.getInt("id")));
                fieldDataSet.put("title", resultSet.getString("title"));
                posts.add(fieldDataSet);
            }

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
        return posts;
    }
}
