package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Horea on 14/11/2015.
 */
public class Lecture implements IsSerializable {
  long id;
  Professor professor;
  String lectureName;

  public Lecture() {
  }

  public Lecture(long id, String lectureName,  Professor professor) {
    this.id = id;
    this.professor = professor;
    this.lectureName = lectureName;
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

  public long getId() {
    return id;
  }
}
