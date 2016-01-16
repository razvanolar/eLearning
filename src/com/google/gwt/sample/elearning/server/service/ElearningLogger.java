package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.server.ServerUtil;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Horea on 16/01/2016.
 */
public class ElearningLogger {
  public static void log(String message) {
    try {
      File file = getLogFile();
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
      DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
      Date date = new Date();
      String time = dateFormat.format(date);
      bufferedWriter.write("[" + time + "]" + message);
      bufferedWriter.newLine();
      bufferedWriter.close();
    } catch (IOException e) {
      throw new ELearningException("Could not create log file!", e);
    }
  }

  private static File getLogFile() throws IOException {
    checkTheLogDirectory();
    File file = new File(ServerUtil.getLogFile());
    if(file.exists()) {
      return file;
    } else {
      return createNewLogFile();
    }

  }

  private static void checkTheLogDirectory() {
    File file = new File(ServerUtil.getLogsPath());
    if(!file.exists() && !file.mkdirs()) {
      throw new ELearningException("Cannot create the log directory!");
    }
  }

  private static File createNewLogFile() throws IOException {
    File file = new File(ServerUtil.getLogFile());
    boolean succes = file.createNewFile();
    return file;
  }

}
