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

public class ModelMicroBlogs {
    private static Logger LOGGER = LoggerFactory.getLogger(ModelMicroBlogs.class);

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public List<Map<String, String>> fetchMicroBlogs() {
        List<Map<String, String>> microblogs = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select id, date, text from microblogs;");
            while (resultSet.next()) {
                Map<String, String> fieldDataSet = new HashMap<>();
                fieldDataSet.put("id", String.valueOf(resultSet.getInt("id")));
                fieldDataSet.put("date", resultSet.getString("date"));
                fieldDataSet.put("text", resultSet.getString("text"));
                microblogs.add(fieldDataSet);
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
        return microblogs;
    }
}
