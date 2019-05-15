package com.iamunclexu.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloworldController extends Controller {

    @Override
    public void process(Request request, Response response) {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id from post");

            String output = "";
            while (resultSet.next()) {
                output += String.valueOf(resultSet.getInt("id"));
            }
            response.write(output);
        } catch (Exception e) {
            Logger.error(e);
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
                Logger.error(e);
            }
        }
    }
}
