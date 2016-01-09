package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.Grade;

import java.util.List;

/**
 * Created by Valy on 12/28/2015.
 */
public interface GradeDAO {
  List<Grade> getAllHomeworkGrades() throws RepositoryException;
  List<Grade> getAllLectureTestGrades() throws RepositoryException;
  List<Grade> getHomeworkGrades(long homeworkId) throws RepositoryException;
  List<Grade> getLectureTestGrades(long lectureId) throws RepositoryException;
  List<Grade> getStudentLecturesTestGrades(List<Long> lectureIds, long studentId) throws RepositoryException;
  List<Grade> getStudentHomeworkGrades(List<Long> lectureIds, long studentId) throws RepositoryException;

  void saveGrade(Grade grade, String className) throws RepositoryException;
  void updateGrade(Grade grade, String className) throws RepositoryException;
  void deleteGrade(Grade grade, String className) throws RepositoryException;
  void deleteStudentGrades(long studentId) throws  RepositoryException;
}
