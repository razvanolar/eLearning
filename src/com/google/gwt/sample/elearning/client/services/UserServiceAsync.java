package com.google.gwt.sample.elearning.client.services;

import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

public interface UserServiceAsync {

  void getAllUsers(AsyncCallback<List<UserData>> async);
}
