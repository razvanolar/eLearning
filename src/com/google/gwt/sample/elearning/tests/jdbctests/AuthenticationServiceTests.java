package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.shared.IncorrectLoginException;
import com.google.gwt.sample.elearning.server.JDBC.UserJDBCImpl;
import com.google.gwt.sample.elearning.shared.ELearningException;
import com.google.gwt.sample.elearning.shared.UserData;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Horea on 09/11/2015.
 */
public class AuthenticationServiceTests {
    private UserJDBCImpl userJDBC;

    @Before
    public void build() {
        try {
            userJDBC = new UserJDBCImpl();
        } catch (ELearningException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAuthenticateAdmin(){
        String user = "admin";
        String pass = "admin";
        UserData userData = null;
        try {
            userData = userJDBC.getUserData(user, pass);
            assert userData.getUsername().equals(user) && userData.getPassword().equals(pass);
        } catch (ELearningException e) {
            e.printStackTrace();
        } catch (IncorrectLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAuthenticateProfessor(){
        String user = "prof";
        String pass = "prof";
        try {
            UserData userData = userJDBC.getUserData(user, pass);
            assert userData.getUsername().equals(user) && userData.getPassword().equals(pass);
        } catch (ELearningException e) {
            e.printStackTrace();
        } catch (IncorrectLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAuthenticateStudent(){
        String user = "abir1234";
        String pass = "student";
        try {
            UserData userData = userJDBC.getUserData(user, pass);
            assert userData.getUsername().equals(user) && userData.getPassword().equals(pass);
        } catch (ELearningException e) {
            e.printStackTrace();
        } catch (IncorrectLoginException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IncorrectLoginException.class)
    public void testAuthenticateFail(){
        String user = "abir1234";
        String pass = "wrongPass";
        try {
            UserData userData = userJDBC.getUserData(user, pass);
        } catch (ELearningException e) {
            e.printStackTrace();
        } catch (IncorrectLoginException e) {
            e.printStackTrace();
        }
    }
}
