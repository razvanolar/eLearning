package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.rpc.IsSerializable;

/***
 * Created by Horea on 14/11/2015.
 */
public class UserData implements IsSerializable {
  private long id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private String sessionId;
  private UserRoleTypes role;
  private String imagePath = "elearning\\app_files\\user_icons\\default.png";

  public UserData() {
  }

  public UserData(long id, String firstName) {
    this.id = id;
    this.firstName = firstName;
  }

  public UserData(String username, String firstName, String lastName, String email,  UserRoleTypes role) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.role =role;
  }

  public UserData(long id, String username, String password, String firstName, String lastName, String email, UserRoleTypes role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.role = role;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setRole(UserRoleTypes role) {
    this.role = role;
  }

  public UserData(String username, String password, String firstName, String lastName, String email, UserRoleTypes role) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.role = role;
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

  public UserRoleTypes getRole() {
    return role;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return id + " " + username;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }
}
