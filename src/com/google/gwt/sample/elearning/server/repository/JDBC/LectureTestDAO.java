package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;

import java.util.List;

/**
 * Created by Valy on 1/12/2016.
 */
public interface LectureTestDAO {
  List<LectureTestData> getAllLectureTests() throws RepositoryException;
  LectureTestData getLectureTestById(long id) throws RepositoryException;
  List<LectureTestData> getLectureTestByLecture(long lectureId) throws RepositoryException;
  void insertLectureTest(LectureTestData lectureTestData) throws RepositoryException;
  void updateLectureTest(LectureTestData lectureTestData) throws RepositoryException;
  void deleteLectureTest(long id) throws RepositoryException;
}
