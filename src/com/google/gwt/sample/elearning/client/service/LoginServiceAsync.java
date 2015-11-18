package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
  void loginServer(String user, String pwd, AsyncCallback<UserData> async);

  void loginFromSessionServer(AsyncCallback<String> async);

  void logout(AsyncCallback<Void> async);
}
