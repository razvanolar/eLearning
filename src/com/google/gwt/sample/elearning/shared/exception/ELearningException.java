package com.google.gwt.sample.elearning.shared.exception;

/***
 * Created by Cristi on 11/13/2015.
 */
public class ELearningException extends RuntimeException {
  public ELearningException(String e) {
    super(e);
  }

  public ELearningException(String message, Exception e) {
    super(message, e);
  }

  public ELearningException(Throwable cause) {
    super(cause);
  }

  public ELearningException() {
  }
}
