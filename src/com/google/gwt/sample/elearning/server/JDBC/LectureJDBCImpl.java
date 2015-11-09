package com.google.gwt.sample.elearning.server.JDBC;

import java.sql.Connection;

/**
 * Created by Horea on 09/11/2015.
 */
public class LectureJDBCImpl {
    Connection dbConnection;

    public LectureJDBCImpl() {
        dbConnection = JDBCUtil.getDbConnection();
    }
}
