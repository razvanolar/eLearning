package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.server.JDBC.JDBCUtil;
import com.google.gwt.sample.elearning.shared.ELearningException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by Horea on 09/11/2015.
 */
public class JDBCUtilTests {

    private Connection dbConnection;

    @Before
    public void build() {
        try {
            dbConnection = JDBCUtil.getDbConnection();
        } catch (ELearningException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDBConnection() {
        try{
            dbConnection.isValid(10);
        } catch (SQLException ex) {
            assert false;
        }
    }

}
