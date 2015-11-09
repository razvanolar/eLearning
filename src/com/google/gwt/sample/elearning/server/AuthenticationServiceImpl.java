package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.server.JDBC.UserJDBCImpl;

/**
 * Created by Horea on 01/11/2015.
 */
public class AuthenticationServiceImpl {
    private UserJDBCImpl userJDBC;

    public boolean authenticate(String userId, String password) {
        LoginData loginData = userJDBC.getLoginData(userId, password);
        return password.equals(loginData.getPassword());
    }
}
