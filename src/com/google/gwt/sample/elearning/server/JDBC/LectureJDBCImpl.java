package com.google.gwt.sample.elearning.server.JDBC;

import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.Lecture;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Horea on 09/11/2015.
 */
public class LectureJDBCImpl {
  Connection dbConnection;

  public LectureJDBCImpl() {
  }

  public void createLecture(LectureJDBCImpl lectureJDBC) {
    throw new ELearningException("Not implemented yet!");
  }

  public List<Lecture> getAllLectures() {
    throw new ELearningException("Not implemented yet!");
  }

  public List<Lecture> getAllLecturesByProfessor(int idProfessor) {
    throw new ELearningException("Not implemented yet!");
  }

  public Lecture getLectureById(int id) {
    throw new ELearningException("Not implemented yet!");
  }

  public void updateLecture(Lecture lecture) {
    throw new ELearningException("Not implemented yet!");
  }

  public void removeLecture(int id) {
    throw new ELearningException("Not implemented yet!");
  }
}
