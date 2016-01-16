package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.JDBC.JDBCUtil;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Valy on 11/21/2015.
 */
public class JdbcUserDAO implements UserDAO {

  public JdbcUserDAO() {
  }

  @Override
  public List<UserData> getAllUsers() throws RepositoryException {
    ArrayList<UserData> userlist = new ArrayList<UserData>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from utilizatori");
      while (rs.next()) {
        UserData user = new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), UserRoleTypes.getRoleById(rs.getLong(7)));
        userlist.add(user);
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return userlist;
  }

  @Override
  public List<UserData> getAllUsersByLecture(long id) {
    ArrayList<UserData> userlist = new ArrayList<UserData>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select ut.* from utilizatori ut INNER JOIN studenti_inscrisi si on ut.id = si.ref_student WHERE si.ref_curs = ?");
      pstmt.setLong(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        UserData user = new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), UserRoleTypes.getRoleById(rs.getLong(7)));
        userlist.add(user);
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return userlist;
  }

  @Override
  public List<UserData> getAllUsersByProfessor(long id) {
    ArrayList<UserData> userlist = new ArrayList<UserData>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select DISTINCT ut.* from utilizatori ut INNER JOIN studenti_inscrisi si on ut.id = si.ref_student WHERE si.ref_curs in (SELECT cu.id from cursuri cu where ref_profesor = ?)");
      pstmt.setLong(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        UserData user = new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                rs.getString(6), UserRoleTypes.getRoleById(rs.getLong(7)));
        userlist.add(user);
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return userlist;
  }

  @Override
  public UserData getUserById(long id) throws RepositoryException {
    Connection con = null;

    UserData user = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from utilizatori where id = ?");
      pstmt.setLong(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        user = new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), UserRoleTypes.getRoleById(rs.getLong(7)));
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la cmunicarea cu BD", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

    return user;
  }

  @Override
  public UserData getUserByUsername(String username) throws RepositoryException {
    Connection con = null;

    UserData user = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from utilizatori where userId = ?");
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        user = new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), UserRoleTypes.getRoleById(rs.getLong(7)));
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la cmunicarea cu BD", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

    return user;
  }

  @Override
  public UserData getUserByEmail(String email) throws RepositoryException {
    Connection con;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement preparedStatement = con.prepareStatement("select * from utilizatori WHERE email = ?");
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();
      while(rs.next()){
        return new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), UserRoleTypes.getRoleById(rs.getLong(7)));
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public void insertUser(UserData user) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
          .prepareStatement("insert into utilizatori(userId, parola, nume, prenume, email, rol) values(?,?,?,?,?,?)");
      pstmt.setString(1, user.getUsername());
      pstmt.setString(2, user.getPassword());
      pstmt.setString(3, user.getFirstName());
      pstmt.setString(4, user.getLastName());
      pstmt.setString(5, user.getEmail());
      pstmt.setLong(6, user.getRole().getId());

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

  }

  @Override
  public void updateUser(UserData user) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
          .prepareStatement("update utilizatori set parola=?, nume=?, prenume=?, email=?, rol=? where id = ?");
      pstmt.setString(1, user.getPassword());
      pstmt.setString(2, user.getFirstName());
      pstmt.setString(3, user.getLastName());
      pstmt.setString(4, user.getEmail());
      pstmt.setLong(5, user.getRole().getId());
      pstmt.setLong(6, user.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

  }

  @Override
  public void deleteUser(long id) throws RepositoryException {
    Connection con = null;

    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("delete from utilizatori where id = ?");
      pstmt.setLong(1, id);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

  }

  @Override
  public void removeUserFromLecture(long lectureId, long userId) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("delete from studenti_inscrisi where ref_curs = ? and ref_student = ?");
      pstmt.setLong(1, lectureId);
      pstmt.setLong(2, userId);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void changePassword(long id, String password) throws RepositoryException {
    Connection con = null;
    try{
      con = JDBCUtil.getNewConnection();
      PreparedStatement preparedStatement = con.prepareStatement("update utilizatori set parola = ? where id = ?");
      preparedStatement.setString(1, password);
      preparedStatement.setLong(2,id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }
}