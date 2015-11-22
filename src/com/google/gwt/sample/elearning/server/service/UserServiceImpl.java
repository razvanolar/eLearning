package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.UserService;
import com.google.gwt.sample.elearning.server.JDBC.UserJDBCImpl;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by razvanolar on 17.11.2015.
 */
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

  UserJDBCImpl userJDBC = new UserJDBCImpl();
  private long id = 0;

  @Override
  public List<UserData> getAllUsers() throws ELearningException {
    List<UserData> result = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      result.add(new UserData(id, "userName" + id, "pwd" + id, "firstName" + id, "lastName" + id,
              "email" + id + "@gmail.com",UserRoleTypes.USER));
      id++;
    }
    return result;
  }

  @Override
  public List<? extends UserData> getAllUsersByRole(UserRoleTypes role) throws ELearningException {
    return userJDBC.getAllUsersByRole(role);
  }

  @Override
  public UserData getUserById(int id) throws ELearningException {
    UserData userData;

    userData = userJDBC.getUserById(id);
    return userData;
  }

  @Override
  public void createUser(UserData user) throws ELearningException {
    userJDBC.createUser(user);
  }

  @Override
  public void updateUser(UserData newUser) throws ELearningException {
    userJDBC.updateUser(newUser);
  }

  @Override
  public void removeUser(List<String> id) throws ELearningException {
    userJDBC.removeUser(id);
  }
}
