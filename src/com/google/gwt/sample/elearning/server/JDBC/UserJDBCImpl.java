package com.google.gwt.sample.elearning.server.JDBC;

import com.google.gwt.sample.elearning.shared.ELearningException;
import com.google.gwt.sample.elearning.server.IncorrectLoginException;
import com.google.gwt.sample.elearning.server.LoginData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * Created by Horea on 01/11/2015.
 */
public class UserJDBCImpl {
  Connection dbConnection;

  public UserJDBCImpl() {
    dbConnection = JDBCUtil.getDbConnection();
  }

  private PreparedStatement prepareGetLoginDataStatement() throws SQLException {
    return dbConnection
        .prepareStatement("SELECT userId, parola, rol FROM utilizatori WHERE userId = ? AND parola = ?;");
  }

  public LoginData getLoginData(String userId, String pass) throws IncorrectLoginException, ELearningException {
    ResultSet resultSet;
    PreparedStatement getLoginDataStatement;
    try {
      getLoginDataStatement = prepareGetLoginDataStatement();
      getLoginDataStatement.setString(1, userId);
      getLoginDataStatement.setString(2, pass);
      resultSet = getLoginDataStatement.executeQuery();
      if (resultSet.next()) {
        return new LoginData(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
      } else {
        throw new IncorrectLoginException("User and password combination is invalid.");
      }
    } catch (SQLException e) {
      throw new ELearningException(e);
    } catch (IncorrectLoginException e) {
      throw e;
    } catch (Exception e) {
      throw new ELearningException(e);
    }

  }

}
