package com.google.gwt.sample.elearning.server.JDBC;

import com.google.gwt.sample.elearning.shared.exception.ELearningException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Horea on 01/11/2015.
 */
public class JDBCUtil {
    public static Connection getDbConnection() throws ELearningException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/testdb", "elearn", "test");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new ELearningException(ex);
        }
    }
}
