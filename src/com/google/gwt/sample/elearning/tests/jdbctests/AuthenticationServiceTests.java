package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.server.IncorrectLoginException;
import com.google.gwt.sample.elearning.server.JDBC.UserJDBCImpl;
import com.google.gwt.sample.elearning.server.LoginData;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Horea on 09/11/2015.
 */
public class AuthenticationServiceTests {
    private UserJDBCImpl userJDBC;

    @Before
    public void build() {
        userJDBC = new UserJDBCImpl();
    }

    @Test
    public void testAuthenticateAdmin(){
        String user = "admin";
        String pass = "admin";
        LoginData loginData = userJDBC.getLoginData(user, pass);
        assert loginData.getUserId().equals(user) && loginData.getPassword().equals(pass);
    }

    @Test
    public void testAuthenticateProfessor(){
        String user = "prof";
        String pass = "prof";
        LoginData loginData = userJDBC.getLoginData(user, pass);
        assert loginData.getUserId().equals(user) && loginData.getPassword().equals(pass);
    }

    @Test
    public void testAuthenticateStudent(){
        String user = "abir1234";
        String pass = "student";
        LoginData loginData = userJDBC.getLoginData(user, pass);
        assert loginData.getUserId().equals(user) && loginData.getPassword().equals(pass);
    }

    @Test(expected = IncorrectLoginException.class)
    public void testAuthenticateFail(){
        String user = "abir1234";
        String pass = "wrongPass";
        LoginData loginData = userJDBC.getLoginData(user, pass);
    }
}
