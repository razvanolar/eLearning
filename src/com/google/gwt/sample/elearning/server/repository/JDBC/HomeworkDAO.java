package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;

import java.util.List;

/**
 * Created by Valy on 1/11/2016.
 */
public interface HomeworkDAO {
  List<HomeworkData> getAllHomeworks() throws RepositoryException;
  HomeworkData getHomeworkById(long id) throws RepositoryException;
  List<HomeworkData> getHomeworkByLecture(long lectureId) throws RepositoryException;
  void insertHomework(HomeworkData homeworkData) throws RepositoryException;
  void updateHomework(HomeworkData homeworkData) throws RepositoryException;
  void deleteHomework(long id) throws RepositoryException;


}
