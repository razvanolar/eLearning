package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/***
 * Created by razvanolar on 17.11.2015.
 */
@RemoteServiceRelativePath("UserService")
public interface UserService extends RemoteService {
  List<UserData> getAllUsers() throws ELearningException;

  UserData getUserById(int id);

  void createUser(UserData user);

  void updateUser(UserData newUser);

  void removeUser(List<String> ids);
}
