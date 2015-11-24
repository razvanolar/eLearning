package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.DAOFactory;
import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcUserDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.UserDAO;

/***
 * Created by Valy on 11/21/2015.
 */
public class JdbcDAOFactory extends DAOFactory {

  @Override
  public UserDAO getUserDAO() {
    return new JdbcUserDAO();
  }

  @Override
  public LectureDAO getLectureDAO() {
    return new JdbcLectureDAO();
  }
}
