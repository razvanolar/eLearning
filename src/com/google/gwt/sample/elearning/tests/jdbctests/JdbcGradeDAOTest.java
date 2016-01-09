package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.server.repository.JDBC.GradeDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcGradeDAO;
import com.google.gwt.sample.elearning.shared.model.Grade;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Valy on 1/9/2016.
 */
public class JdbcGradeDAOTest {
  GradeDAO gradeDAO = new JdbcGradeDAO();

@Test
public void addDataTest(){
  Grade grade = new Grade(3,3,9);
  gradeDAO.saveGrade(grade,"HomeworkData");
}
}
