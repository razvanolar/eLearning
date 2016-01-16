package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.LogService;
import com.google.gwt.sample.elearning.server.ServerUtil;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.LogData;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Horea on 16/01/2016.
 */
public class LogServiceImpl implements LogService {
  @Override
  public List<LogData> getLogDataFromFile(String filename) {
    File file  = new File(filename);
    if(!file.exists()) {
      throw new ELearningException("Log file does not exist!");
    }
    return parseLogFile(file);
  }

  @Override
  public List<String> getAllLogFiles() {
    List<String> rez = new ArrayList<>();
    String logsPath = ServerUtil.getLogsPath();
    File file = new File(logsPath);
    if(file.exists() && file.isDirectory()) {
      Collections.addAll(rez, file.list());
    }
    return rez;
  }

  @Override
  public String getLogFileContent(String filename) {
    StringBuilder rez = new StringBuilder();
    try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
      while(bufferedReader.ready()) {
        rez.append(bufferedReader.readLine());
      }
    } catch (IOException e) {
      throw new ELearningException("Could not open log file!", e);
    }
    return rez.toString();
  }

  private List<LogData> parseLogFile(File file) {
    List<LogData> rez = new ArrayList<>();
    try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
      while(bufferedReader.ready()) {
        rez.add(new LogData(bufferedReader.readLine()));
      }
    } catch (IOException e) {
      throw new ELearningException("Could not open log file!", e);
    }
    return rez;
  }
}
