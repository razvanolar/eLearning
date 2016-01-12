package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.server.repository.JDBC.*;
import com.google.gwt.sample.elearning.shared.model.Grade;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Valy on 1/9/2016.
 */
public class JdbcGradeDAOTest {
  GradeDAO gradeDAO = new JdbcGradeDAO();
  HomeworkDAO homeworkDAO = new JdbcHomeworkDAO();
  LectureTestDAO lectureTestDAO = new JdbcLectureTestDAO();

  @Test
  public void addDataTest() {
    Grade grade = new Grade(3, 3, 9);
    //gradeDAO.saveGrade(grade,"HomeworkData");
    LectureTestData lectureTestData = new LectureTestData(5, "test3updated", new Date(), new Date(), 10, 1);
    assert(lectureTestDAO.getLectureTestByLecture(1).size()==2);
  }
}
