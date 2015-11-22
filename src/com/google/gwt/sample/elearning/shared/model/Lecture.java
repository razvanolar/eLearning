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

  public Lecture(Professor professor, String lectureName) {
    this.professor = professor;
    this.lectureName = lectureName;
  }

  public Lecture(long id, Professor professor, String lectureName) {
    this.id = id;
    this.professor = professor;
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
