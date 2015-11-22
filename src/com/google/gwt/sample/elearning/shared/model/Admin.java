package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;

/**
 * Created by Horea on 14/11/2015.
 */
public class Admin extends UserData {
  public Admin() {
  }

  public Admin(long id, String username, String password, String firstName, String lastName, String email) {
    super(id, username, password, firstName, lastName, email, UserRoleTypes.ADMIN);
  }

  public Admin(String username, String password, String firstName, String lastName, String email) {
    super(username, password, firstName, lastName, email, UserRoleTypes.ADMIN);
  }
}
