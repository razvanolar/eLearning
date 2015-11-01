package com.google.gwt.sample.elearning.server;

/**
 * Created by Horea on 01/11/2015.
 */
public class AuthenticationServiceImpl {
    private UserJDBCImpl userJDBC;

    public boolean authenticate(String userId, String password) {
        LoginData loginData = userJDBC.getLoginData(userId);
        return password.equals(loginData.getPassword());
    }
}
