package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/***
 * Created by razvanolar on 14.11.2015.
 */
public interface UserDataProperties extends PropertyAccess<UserData> {

  @Editor.Path("id")
  ModelKeyProvider<UserData> key();

  ValueProvider<UserData, Long> id();
  ValueProvider<UserData, String> username();
  ValueProvider<UserData, String> firstName();
  ValueProvider<UserData, String> lastName();
  ValueProvider<UserData, String> email();
  ValueProvider<UserData, UserRoleTypes> role();
}
