package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/***
 * Created by razvanolar on 17.11.2015.
 */
@RemoteServiceRelativePath("UserService")
public interface UserService extends RemoteService {
  List<UserData> getAllUsers() throws ELearningException;

  List<? extends UserData> getAllUsersByRole(UserRoleTypes role) throws ELearningException;

  UserData getUserById(int id) throws ELearningException;

  void createUser(UserData user) throws ELearningException;

  void updateUser(UserData newUser) throws ELearningException;

  void removeUser(List<Long> ids) throws ELearningException;

  List<UserData> getEnrolledStudentsByLectureId(long lectureId) throws ELearningException;

  List<? extends UserData> getUsersByLecture(long id) throws ELearningException;
}
