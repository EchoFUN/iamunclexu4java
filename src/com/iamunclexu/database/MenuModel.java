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

public class MenuModel {
    private static Logger LOGGER = LoggerFactory.getLogger(PostModel.class);

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public List<Map<String, String>> fetchMenus() {
        List<Map<String, String>> menus = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select id, title, url from menu");
            while (resultSet.next()) {
                Map<String, String> fieldDataSet = new HashMap<>();
                fieldDataSet.put("id", String.valueOf(resultSet.getInt("id")));
                fieldDataSet.put("title", resultSet.getString("title"));
                fieldDataSet.put("url", resultSet.getString("url"));
                menus.add(fieldDataSet);
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
        return menus;
    }
}
