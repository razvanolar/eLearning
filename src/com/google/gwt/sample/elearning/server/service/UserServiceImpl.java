package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.UserService;
import com.google.gwt.sample.elearning.server.repository.DAOFactory;
import com.google.gwt.sample.elearning.server.repository.JDBC.UserDAO;
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

  DAOFactory factory = DAOFactory.getInstance();
  UserDAO userDAO = factory.getUserDAO();
  private long id = 0;

  @Override
  public List<UserData> getAllUsers() throws ELearningException {
    return userDAO.getAllUsers();
  }

  @Override
  public List<? extends UserData> getAllUsersByRole(UserRoleTypes role) throws ELearningException {
    List<UserData> result = new ArrayList<>();
    List<? extends UserData> allUsers = userDAO.getAllUsers();
    for(UserData user : allUsers) {
      if(user.getRole().getId() == role.getId()) {
        result.add(user);
      }
    }
    return result;
  }

  @Override
  public UserData getUserById(int id) throws ELearningException {
    UserData userData;
    userData = userDAO.getUserById(id);
    return userData;
  }

  @Override
  public void createUser(UserData user) throws ELearningException {
    userDAO.insertUser(user);
  }

  @Override
  public void updateUser(UserData newUser) throws ELearningException {
    userDAO.updateUser(newUser);
  }

  @Override
  public void removeUser(List<Long> ids) throws ELearningException {
    for(long id : ids) {
      userDAO.deleteUser(id);
    }
  }

  @Override
  public List<UserData> getUsersByLecture(long id) throws ELearningException {
    return userDAO.getAllUsersByLecture(id);
  }

  @Override
  public List<UserData> getEnrolledStudentsByProfessorId(long id) throws ELearningException {
    return userDAO.getAllUsersByProfessor(id);
  }

  @Override
  public void removeUserFromLecture(long lectureId, long userId) throws ELearningException {
    userDAO.removeUserFromLecture(lectureId, userId);
  }
}
