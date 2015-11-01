package com.google.gwt.sample.elearning.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Horea on 01/11/2015.
 */
public class UserJDBCImpl {
    Connection dbConnection;
    PreparedStatement getLoginDataStatement;

    public UserJDBCImpl() {
        dbConnection = JDBCUtil.getDbConnection();
        getLoginDataStatement = prepareGetLoginDataStatement();
    }

    private PreparedStatement prepareGetLoginDataStatement() {
        try {
            return dbConnection.prepareStatement("SELECT userId, parola, rol FROM utilizatori WHERE userId = ?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginData getLoginData(String userId) {
        ResultSet resultSet;
        LoginData loginData = null;
        try {
            getLoginDataStatement.setString(1, userId);
            resultSet = getLoginDataStatement.executeQuery();
            if (resultSet.next()) {
                loginData = new LoginData(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginData;

    }

}
