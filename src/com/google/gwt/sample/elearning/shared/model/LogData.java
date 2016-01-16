package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Horea on 16/01/2016.
 */
public class LogData implements IsSerializable {
  private String message;

  public LogData() {
  }

  public LogData(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
