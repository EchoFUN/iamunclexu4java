package com.iamunclexu.database

import com.iamunclexu.confs.Constant.*
import com.iamunclexu.confs.SysConf
import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.SQLException

class DBUtils {

    fun init() {
        dataSource = ComboPooledDataSource()

        val databaseInfo = SysConf.fetchDatabaseInfo()
        dataSource.jdbcUrl = databaseInfo[URL]
        dataSource.user = databaseInfo[USERNAME]
        dataSource.password = databaseInfo[PASSWORD]

        dataSource.maxPoolSize = Integer.parseInt(databaseInfo[CONNECTION_POOL_SIZE], 10)
    }

    companion object {

        private val LOGGER = LoggerFactory.getLogger(DBUtils::class.java)
        lateinit var dataSource: ComboPooledDataSource

        private var dbUtils: DBUtils? = null
            get() {
                if (field == null) {
                    field = DBUtils()
                }
                return field
            }

        @JvmStatic
        fun inst(): DBUtils {
            return dbUtils!!
        }

        fun releaseConnection(connection: Connection?) {
            try {
                if (connection != null && !connection.isClosed) {
                    connection.close()
                }
            } catch (e: SQLException) {
                LOGGER.error(e.message)
            }
        }

        /**
         * Get the connection from the database connection Pool .
         *
         *
         *
         *
         */
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
        val connection: Connection
            @Throws(Exception::class)
            get() = dataSource.connection
    }
}


