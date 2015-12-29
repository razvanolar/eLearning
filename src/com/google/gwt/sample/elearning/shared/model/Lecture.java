package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/***
 * Created by Horea on 14/11/2015.
 */
public class Lecture implements IsSerializable {
  long id;
  Professor professor;
  String lectureName;
  String enrolmentKey;

  public Lecture() {}

  public Lecture(Professor professor, String lectureName, String enrolmentKey) {
    this.professor = professor;
    this.lectureName = lectureName;
    this.enrolmentKey = enrolmentKey;
  }

  public Lecture(long id, Professor professor, String lectureName, String enrolmentKey) {
    this(professor, lectureName, enrolmentKey);
    this.id = id;
  }

  public Lecture(String lectureName, Professor professor) {
    this.professor = professor;
    this.lectureName = lectureName;
  }

  public void setProfessor(Professor professor) {
    this.professor = professor;
  }

  public void setLectureName(String lectureName) {
    this.lectureName = lectureName;
  }

  public Professor getProfessor() {
    return professor;
  }

  public String getLectureName() {
    return lectureName;
  }

  public String getEnrolmentKey() {
    return enrolmentKey;
  }

  public void setEnrolmentKey(String enrolmentKey) {
    this.enrolmentKey = enrolmentKey;
  }

  public long getId() {
    return id;
  }
}
