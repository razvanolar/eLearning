package com.google.gwt.sample.elearning.shared.types;

/***
 * Created by razvanolar on 17.11.2015.
 */
public enum UserRolTypes {

  ADMIN(1), PROFESSOR(2), USER(3);

  long id;

  UserRolTypes(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public static UserRolTypes getRolById(long id) {
    for (UserRolTypes type : values())
      if (type.getId() == id)
        return type;
    return null;
  }
}
