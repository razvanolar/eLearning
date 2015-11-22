package com.google.gwt.sample.elearning.shared.types;

import com.google.gwt.user.client.rpc.IsSerializable;

/***
 * Created by razvanolar on 17.11.2015.
 */
public enum UserRoleTypes implements IsSerializable {

  ADMIN(1), PROFESSOR(2), USER(3);

  long id;

  UserRoleTypes(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public static UserRoleTypes getRoleById(long id) {
    for (UserRoleTypes type : values())
      if (type.getId() == id)
        return type;
    return null;
  }
}
