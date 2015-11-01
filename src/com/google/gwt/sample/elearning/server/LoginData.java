package com.google.gwt.sample.elearning.server;

/**
 * Created by Horea on 01/11/2015.
 */
public class LoginData {
    private String userId;
    private String password;
    private String role;

    public LoginData(String userId, String password, String role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
