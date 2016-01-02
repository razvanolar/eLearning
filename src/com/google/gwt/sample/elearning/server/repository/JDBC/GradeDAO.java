package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.Grade;

import java.util.List;

/**
 * Created by Valy on 12/28/2015.
 */
public interface GradeDAO {
  List<Grade> getTestGrades() throws RepositoryException;
  List<Grade> getHomeworkGrades() throws RepositoryException;
  List<Grade> getLectureGrades(long lectureId) throws RepositoryException;
  void saveGrade(Grade grade) throws RepositoryException;
  void updateGrade(Grade grade) throws RepositoryException;
  void deleteGrade(Grade grade) throws RepositoryException;
  void deleteStudentGrades(long studentId) throws  RepositoryException;

}
