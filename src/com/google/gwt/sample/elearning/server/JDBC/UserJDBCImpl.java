package com.google.gwt.sample.elearning.server.JDBC;

import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.exception.IncorrectLoginException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/***
 * Created by Horea on 01/11/2015.
 */
public class UserJDBCImpl {
  Connection dbConnection;

  public UserJDBCImpl() throws ELearningException {
  }

  private PreparedStatement prepareGetLoginDataStatement() throws SQLException {
    return dbConnection
            .prepareStatement("SELECT id, userId, parola, nume, prenume, email, rol FROM utilizatori WHERE userId = ? AND parola = ?");
  }

  public UserData getUserData(String userId, String pass) throws IncorrectLoginException, ELearningException {
    ResultSet resultSet;
    PreparedStatement getLoginDataStatement;
    try {
      dbConnection = JDBCUtil.getNewConnection();
      getLoginDataStatement = prepareGetLoginDataStatement();
      getLoginDataStatement.setString(1, userId);
      getLoginDataStatement.setString(2, pass);
      resultSet = getLoginDataStatement.executeQuery();

      if (resultSet.next()) {
        long id = Long.parseLong(resultSet.getString(1));
        String userName = resultSet.getString(2);
        String password = resultSet.getString(3);
        String firstName = resultSet.getString(4);
        String lastName = resultSet.getString(5);
        String email = resultSet.getString(6);

        UserData userData = new UserData(id, userName, password, firstName, lastName, email,
                UserRoleTypes.getRoleById(resultSet.getLong(7)));
        resultSet.close();
        getLoginDataStatement.close();
        return userData;
      } else {
        throw new IncorrectLoginException("User and password combination is invalid.");
      }
    } catch (SQLException e) {
      throw new ELearningException(e);
    } catch (IncorrectLoginException e) {
      throw e;
    } catch (Exception e) {
      throw new ELearningException(e);
    } finally {
      JDBCUtil.closeConnection(dbConnection);
    }

  }

  public UserData getUserById(int id) {
    throw new ELearningException("Not implemented yet");
  }

  public UserData createUser(UserData user) {
    throw new ELearningException("Not implemented yet");
  }

  public UserData updateUser(UserData newUser) {
    throw new ELearningException("Not implemented yet");
  }

  public UserData removeUser(List<Long> id) {
    throw new ELearningException("Not implemented yet");
  }

  public List<? extends UserData> getAllUsersByRole(UserRoleTypes role) {
    throw new ELearningException("Not implemented yet");
  }
}
