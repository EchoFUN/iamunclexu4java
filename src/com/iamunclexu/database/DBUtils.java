package com.iamunclexu.database;

import com.iamunclexu.confs.SysConf;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static com.iamunclexu.confs.Constant.CONNECTION_POOL_SIZE;
import static com.iamunclexu.confs.Constant.PASSWORD;
import static com.iamunclexu.confs.Constant.URL;
import static com.iamunclexu.confs.Constant.USERNAME;

public class DBUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);
    public static ComboPooledDataSource dataSource;

    public static DBUtils dbUtils;

    public static DBUtils inst() {
        if (dbUtils == null) {
            dbUtils = new DBUtils();
        }
        return dbUtils;
    }

    public void init() {
        dataSource = new ComboPooledDataSource();

        Map<String, String> databaseInfo = SysConf.fetchDatabaseInfo();
        dataSource.setJdbcUrl(databaseInfo.get(URL));
        dataSource.setUser(databaseInfo.get(USERNAME));
        dataSource.setPassword(databaseInfo.get(PASSWORD));

        dataSource.setMaxPoolSize(Integer.parseInt(databaseInfo.get(CONNECTION_POOL_SIZE), 10));
    }

    public static void releaseConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }


    public static Connection getConnection() throws Exception {

        /**
         * Get the connection from the database connection Pool .
         *
         *
         *
         *
         */
        return dataSource.getConnection();


        /**
         * Simple way of use the database connection .
         *
         *
         *
         *
         */

        /*
        if (conn == null) {
            conn = DriverManager.getConnection(databaseInfo.get(URL), databaseInfo.get(USERNAME), databaseInfo.get(PASSWORD));
            return conn;
        }
        return conn;
         */

    }
}


