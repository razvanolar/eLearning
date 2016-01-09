package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Valy on 12/28/2015.
 */
public class Grade implements IsSerializable {

  long id;
  int grade;
  long studentId;
  long testId;

  public Grade(){
  }

  public Grade(long id, long studentId, long testId, int grade){
    this.id = id;
    this.grade = grade;
    this.studentId = studentId;
    this.testId = testId;
  }

  public Grade(long studentId, long testId, int grade){
    this.grade = grade;
    this.studentId = studentId;
    this.testId = testId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public long getStudentId() {
    return studentId;
  }

  public void setStudentId(long studentId) {
    this.studentId = studentId;
  }

  public long getTestId() {
    return testId;
  }

  public void setTestId(long testId) {
    this.testId = testId;
  }
}
