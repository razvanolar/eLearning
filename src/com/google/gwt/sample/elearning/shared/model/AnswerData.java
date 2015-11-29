package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * Created by razvanolar on 29.11.2015.
 */
public class AnswerData implements IsSerializable {

  private String value;
  private boolean isTrue;

  public AnswerData() {}

  public AnswerData(String value, boolean isTrue) {
    this.value = value;
    this.isTrue = isTrue;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isTrue() {
    return isTrue;
  }

  public void setIsTrue(boolean isTrue) {
    this.isTrue = isTrue;
  }
}
