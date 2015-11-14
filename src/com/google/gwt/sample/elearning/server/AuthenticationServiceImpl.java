package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.server.JDBC.UserJDBCImpl;
import com.google.gwt.sample.elearning.shared.ELearningException;
import com.google.gwt.sample.elearning.shared.UserData;

/**
 * Created by Horea on 01/11/2015.
 */
public class AuthenticationServiceImpl {
    private UserJDBCImpl userJDBC;

    public boolean authenticate(String userId, String password) {
        try {
            UserData userData = userJDBC.getUserData(userId, password);
            return password.equals(userData.getPassword());
        } catch (ELearningException e) {
            e.printStackTrace();
        }
        return false;
    }
}
