package com.google.gwt.sample.elearning.server;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public enum ProjectDirPath {
  PROJECT_DIR_PATH("C:\\Users\\razvanolar\\Desktop\\eLearning\\eLearning\\war\\");

  String path;

  ProjectDirPath(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
