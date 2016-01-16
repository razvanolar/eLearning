package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.LogData;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface LogServiceAsync {
  void getLogDataFromFile(String filename, AsyncCallback<List<LogData>> async);

  void getAllLogFiles(AsyncCallback<List<String>> async);

  void getLogFileContent(String filename, AsyncCallback<String> async);
}
