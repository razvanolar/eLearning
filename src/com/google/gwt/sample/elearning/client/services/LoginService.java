package com.google.gwt.sample.elearning.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/***
 * Created by razvanolar on 19.10.2015.
 */
@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {

  String loginServer(String user, String pwd);

  String loginFromSessionServer();

  void logout();
}
