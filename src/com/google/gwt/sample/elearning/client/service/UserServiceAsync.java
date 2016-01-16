package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface UserServiceAsync {

  void getAllUsers(AsyncCallback<List<UserData>> async);

  void getUserById(int id, AsyncCallback<UserData> async);

  void createUser(UserData user, AsyncCallback<Void> async);

  void updateUser(UserData newUser, AsyncCallback<Void> async);

  void removeUser(List<Long> ids, AsyncCallback<Void> async);

  void getAllUsersByRole(UserRoleTypes role, AsyncCallback<List<? extends UserData>> async);

  void getUsersByLecture(long id, AsyncCallback<List<? extends UserData>> async);

  void getEnrolledStudentsByLectureId(long lectureId, AsyncCallback<List<UserData>> async);
}
