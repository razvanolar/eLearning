package com.google.gwt.sample.elearning.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Horea on 14/11/2015.
 */
public class UserData implements IsSerializable{
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String sessionId;

    public UserData() {
    }

    public UserData(String userId, String password, String firstName, String lastName, String email) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
