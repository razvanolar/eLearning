package com.google.gwt.sample.elearning.server.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Horea on 01/11/2015.
 */
public class JDBCUtil {
    public static Connection getDbConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "elearn", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
