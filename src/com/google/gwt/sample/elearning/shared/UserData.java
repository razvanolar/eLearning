package com.google.gwt.sample.elearning.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/***
 * Created by Horea on 14/11/2015.
 */
public class UserData implements IsSerializable{
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String sessionId;

    public UserData() {
    }

    public UserData(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
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
