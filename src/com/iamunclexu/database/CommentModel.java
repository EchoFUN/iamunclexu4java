package com.iamunclexu.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class CommentModel {
    private static Logger LOGGER = LoggerFactory.getLogger(LinkModel.class);

    Connection connection = null;
    Statement statement = null;

    public String saveComment(Map<String, String> queryData) {
        String result;
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();

            int status = statement.executeUpdate("INSERT INTO `comment` (`id`, `pid`, `name`, `email`, `webside`, `content`, `date`, `approved`) VALUES (" +
                    "null , " +
                    queryData.get("pid") + ", " +
                    queryData.get("name") + ", " +
                    queryData.get("email") + ", " +
                    queryData.get("website") + ", " +
                    queryData.get("content") + ", " +
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
}
