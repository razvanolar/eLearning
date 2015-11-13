package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.server.JDBC.UserJDBCImpl;
import com.google.gwt.sample.elearning.shared.ELearningException;

/**
 * Created by Horea on 01/11/2015.
 */
public class AuthenticationServiceImpl {
    private UserJDBCImpl userJDBC;

    public boolean authenticate(String userId, String password) {
        try {
            LoginData loginData = userJDBC.getLoginData(userId, password);
            return password.equals(loginData.getPassword());
        } catch (ELearningException e) {
            e.printStackTrace();
        }
        return false;
    }
}
