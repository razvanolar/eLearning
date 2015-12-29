package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface EmailServiceAsync {
  void sendForgetEmailPassword(String email, AsyncCallback<Void> async);
}
