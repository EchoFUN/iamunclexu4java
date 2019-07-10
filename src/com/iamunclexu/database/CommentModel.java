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

public class CommentModel {
    private static Logger LOGGER = LoggerFactory.getLogger(LinkModel.class);

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public String saveComment(Map<String, String> queryData) {
        String result;
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();

            String webside = queryData.get("webside");
            if (webside.equals("")) {
                webside = "null";
            } else {
                webside = "\"" + webside + "\"";
            }
            int status = statement.executeUpdate("INSERT INTO `comment` (`id`, `pid`, `name`, `email`, `webside`, `content`, `date`, `approved`) VALUES (" +
                    "null , " +
                    queryData.get("pid") + ", \"" +
                    queryData.get("author") + "\", \"" +
                    queryData.get("mail") + "\", " +
                    webside + ", \"" +
                    queryData.get("comment") + "\", " +
                    System.currentTimeMillis() + ", '0');"
            );
            result = String.valueOf(status);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return e.getMessage();
        } finally {
            try {
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
        return result;
    }

    public int queryCounterByPost(int postId) {
        int counter = 0;

        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select count(*) from comment where approved = 1 and pid = " + postId);
            while (resultSet.next()) {
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

    public List<Map<String, String>> fetchCListByPost(int postId) {
        List<Map<String, String>> comments = new ArrayList();

        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id, name, content from comment where approved = 1 and pid = " + postId);
            while (resultSet.next()) {
                Map<String, String> fieldDataSet = new HashMap<>();
                fieldDataSet.put("id", String.valueOf(resultSet.getInt("id")));
                fieldDataSet.put("name", resultSet.getString("name"));
                fieldDataSet.put("content", resultSet.getString("content"));
                comments.add(fieldDataSet);
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
        return comments;
    }
}
