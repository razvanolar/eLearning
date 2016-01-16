package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.LogData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 * Created by Horea on 16/01/2016.
 */
@RemoteServiceRelativePath("LogService")
public interface LogService extends RemoteService {

  List<LogData> getLogDataFromFile(String filename);

  List<String> getAllLogFiles();

  String getLogFileContent(String filename);

}
