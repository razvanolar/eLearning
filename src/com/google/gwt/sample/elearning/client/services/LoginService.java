package com.google.gwt.sample.elearning.client.services;

import com.google.gwt.sample.elearning.shared.IncorrectLoginException;
import com.google.gwt.sample.elearning.shared.ELearningException;
import com.google.gwt.sample.elearning.shared.UserData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/***
 * Created by razvanolar on 19.10.2015.
 */
@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {

  UserData loginServer(String user, String pwd) throws ELearningException, IncorrectLoginException;

  String loginFromSessionServer();

  void logout();
}
