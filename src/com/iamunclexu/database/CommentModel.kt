package com.iamunclexu.database

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.ArrayList
import java.util.HashMap

class CommentModel {

    private val logger = LoggerFactory.getLogger(LinkModel::class.java)

    private var connection: Connection? = null
    private var statement: Statement? = null
    private var resultSet: ResultSet? = null

    fun saveComment(queryData: Map<String, String>): String? {
        val result: String
        try {
            connection = DBUtils.connection
            statement = connection!!.createStatement()

            var webside = queryData["webside"]
            if (webside == "") {
                webside = "null"
            } else {
                webside = "\"" + webside + "\""
            }
            val status = statement!!.executeUpdate("INSERT INTO `comment` (`id`, `pid`, `name`, `email`, `webside`, `content`, `date`, `approved`) VALUES (" +
                    "null , " +
                    queryData["pid"] + ", \"" +
                    queryData["author"] + "\", \"" +
                    queryData["mail"] + "\", " +
                    webside + ", \"" +
                    queryData["comment"] + "\", " +
                    System.currentTimeMillis() + ", '0');"
            )
            result = status.toString()
        } catch (e: Exception) {
            logger.error(e.message)
            return e.message
        } finally {
            try {
                if (statement != null) {
                    statement!!.close()
                }
                if (connection != null) {
                    DBUtils.releaseConnection(connection)
                }
            } catch (e: SQLException) {
                logger.error(e.message)
            }

        }
        return result
    }

    fun queryCounterByPost(postId: Int): Int {
        var counter = 0

        try {
            connection = DBUtils.connection
            statement = connection!!.createStatement()
            resultSet = statement!!.executeQuery("select count(*) as counter from comment where approved = 1 and pid = $postId")
            while (resultSet!!.next()) {
                counter = resultSet!!.getInt("counter")
            }
        } catch (e: Exception) {
            logger.error(e.message)
        } finally {
            try {
                if (resultSet != null) {
                    resultSet!!.close()
                }
                if (statement != null) {
                    statement!!.close()
                }
                if (connection != null) {
                    DBUtils.releaseConnection(connection)
                }
            } catch (e: SQLException) {
                logger.error(e.message)
            }

        }
        return counter
    }

    fun fetchCListByPost(postId: Int): List<Map<String, String>> {
        val comments = ArrayList<HashMap<String, String>>()

        try {
            connection = DBUtils.connection
            statement = connection!!.createStatement()
            resultSet = statement!!.executeQuery("select id, name, content, date from comment where approved = 1 and pid = $postId")
            while (resultSet!!.next()) {
                val fieldDataSet = HashMap<String, String>()
                fieldDataSet["id"] = resultSet!!.getInt("id").toString()
                fieldDataSet["name"] = resultSet!!.getString("name")
                fieldDataSet["content"] = resultSet!!.getString("content")
                fieldDataSet["date"] = resultSet!!.getString("date")
                comments.add(fieldDataSet)
            }

        } catch (e: Exception) {
            logger.error(e.message)
        } finally {
            try {
                if (resultSet != null) {
                    resultSet!!.close()
                }
                if (statement != null) {
                    statement!!.close()
                }
                if (connection != null) {
                    DBUtils.releaseConnection(connection)
                }
            } catch (e: SQLException) {
                logger.error(e.message)
            }

        }
        return comments
    }

    fun fetchUnApproved(): List<Map<Int, String>> {
        val result = ArrayList<HashMap<Int, String>>()
        try {
            connection = DBUtils.connection
            statement = connection!!.createStatement()

            resultSet = statement!!.executeQuery("select * from comment;")
            while (resultSet!!.next()) {
                val fieldDataSet = HashMap<Int, String>()
                fieldDataSet[resultSet!!.getInt("id")] = resultSet!!.getString("approved")
                result.add(fieldDataSet)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            return emptyList()
        } finally {
            try {
                if (statement != null) {
                    statement!!.close()
                }
                if (connection != null) {
                    DBUtils.releaseConnection(connection)
                }
            } catch (e: SQLException) {
                logger.error(e.message)
            }
        }
        return result
    }

}
