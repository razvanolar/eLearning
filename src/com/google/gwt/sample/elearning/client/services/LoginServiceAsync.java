package com.google.gwt.sample.elearning.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface LoginServiceAsync {
  void loginServer(String user, String pwd, AsyncCallback<String> async);

  void loginFromSessionServer(AsyncCallback<String> async);

  void logout(AsyncCallback<Void> async);
}
