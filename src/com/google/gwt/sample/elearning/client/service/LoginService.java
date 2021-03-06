package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.exception.IncorrectLoginException;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/***
 * Created by razvanolar on 19.10.2015.
 */
@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {

  UserData loginServer(String user, String pwd) throws ELearningException, IncorrectLoginException;

  UserData loginFromSessionServer();

  void logout();

  boolean isSessionAlive();
}
