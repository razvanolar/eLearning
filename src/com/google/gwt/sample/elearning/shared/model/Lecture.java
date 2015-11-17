package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Horea on 14/11/2015.
 */
public class Lecture implements IsSerializable {
  Professor professor;
  String lectureName;

  public Lecture() {
  }

  public Lecture(Professor profesor, String denumire) {
    this.professor = profesor;
    this.lectureName = denumire;
  }

  public Professor getProfessor() {
    return professor;
  }

  public String getLectureName() {
    return lectureName;
  }
}
