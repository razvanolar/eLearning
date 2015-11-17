package com.google.gwt.sample.elearning.shared.model;

/**
 * Created by Horea on 14/11/2015.
 */
public class Admin extends UserData {
  public Admin() {
  }

  public Admin(long id, String username, String password, String firstName, String lastName, String email) {
    super(id, username, password, firstName, lastName, email);
  }
}
