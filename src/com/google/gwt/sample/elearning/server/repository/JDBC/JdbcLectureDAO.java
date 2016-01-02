package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.JDBC.JDBCUtil;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.sample.elearning.shared.model.UserData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Valy on 11/22/2015.
 */
public class JdbcLectureDAO implements LectureDAO {

  UserDAO userDAO = new JdbcUserDAO();

  public JdbcLectureDAO() {
  }

  @Override
  public List<Lecture> getAllLectures() throws RepositoryException {
    ArrayList<Lecture> lecturelist = new ArrayList<Lecture>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from cursuri");
      while (rs.next()) {
        Professor professor = new Professor(userDAO.getUserById(rs.getLong(3)));
        Lecture lecture = new Lecture(rs.getLong(1), professor, rs.getString(2), rs.getString(4));
        lecturelist.add(lecture);
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return lecturelist;
  }

  @Override
  public Lecture getLectureById(long id) throws RepositoryException {
    Connection con = null;

    Lecture lecture = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from cursuri where id = ?");
      pstmt.setLong(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        Professor professor = new Professor(userDAO.getUserById(rs.getLong(3)));
        lecture = new Lecture(rs.getLong(1), professor, rs.getString(2), rs.getString(4));
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

    return lecture;
  }

  @Override
  public Lecture getLectureByName(String name) throws RepositoryException {
    Connection con = null;

    Lecture lecture = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from cursuri where denumire = ?");
      pstmt.setString(1, name);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        Professor professor = new Professor(userDAO.getUserById(rs.getLong(3)));
        lecture = new Lecture(rs.getLong(1), professor, rs.getString(2), rs.getString(4));
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

    return lecture;
  }

  @Override
  public List<Lecture> getLecturesByUser(long id) throws RepositoryException {
    ArrayList<Lecture> lectures = new ArrayList<Lecture>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from cursuri where id = ?");
      pstmt.setLong(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        Professor professor = new Professor(userDAO.getUserById(rs.getLong(3)));
        Lecture lecture = new Lecture(rs.getLong(1), professor, rs.getString(2), rs.getString(4));
        lectures.add(lecture);
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return lectures;
  }

  @Override
  public List<Lecture> getEnrolledLecturesByUser(long userId) throws RepositoryException {
    List<Lecture> lectures = new ArrayList<Lecture>();
    Connection conn = null;
    try {
      conn = JDBCUtil.getNewConnection();
      String query = "SELECT cs.id, cs.denumire, cs.ref_profesor, cs.enrolment_key" +
              "  FROM cursuri cs INNER JOIN studenti_inscrisi si ON cs.id = si.ref_curs WHERE si.ref_student = ?";
      PreparedStatement statement = conn.prepareStatement(query);
      statement.setLong(1, userId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Professor professor = new Professor(userDAO.getUserById(resultSet.getLong(3)));
        Lecture lecture = new Lecture(resultSet.getLong(1), professor, resultSet.getString(2), resultSet.getString(4));
        lectures.add(lecture);
      }
    } catch (SQLException ex) {
      throw new RepositoryException("Unable to retrieve enrolled lectures for userId: " + userId, ex);
    } finally {
      if (conn != null)
        JDBCUtil.closeConnection(conn);
    }
    return lectures;
  }

  @Override
  public List<Lecture> getUnenrolledLecturesByUser(long userId) throws RepositoryException {
    List<Lecture> lectures = new ArrayList<Lecture>();
    Connection conn = null;
    try {
      conn = JDBCUtil.getNewConnection();
      String query = "SELECT cs.id, cs.denumire, cs.ref_profesor, cs.enrolment_key FROM cursuri cs" +
              " LEFT JOIN" +
              " (" +
              " SELECT t.ref_curs FROM cursuri c INNER JOIN studenti_inscrisi t ON c.id = t.ref_curs" +
              " WHERE t.ref_student = ?" +
              " ) t1 ON cs.id = t1.ref_curs" +
              " WHERE ref_curs IS NULL";
      PreparedStatement statement = conn.prepareStatement(query);
      statement.setLong(1, userId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Professor professor = new Professor(userDAO.getUserById(resultSet.getLong(3)));
        Lecture lecture = new Lecture(resultSet.getLong(1), professor, resultSet.getString(2), resultSet.getString(4));
        lectures.add(lecture);
      }
    } catch (SQLException ex) {
      throw new RepositoryException("Unable to retrieve enrolled lectures for userId: " + userId, ex);
    } finally {
      if (conn != null)
        JDBCUtil.closeConnection(conn);
    }
    return lectures;
  }

  @Override
  public void insertLecture(Lecture lecture) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      String key = lecture.getEnrolmentKey() != null ? lecture.getEnrolmentKey() : "";
      PreparedStatement pstmt = con
              .prepareStatement("insert into cursuri(denumire, ref_profesor, enrolment_key) values (?, ?, ?)");
      pstmt.setString(1, lecture.getLectureName());
      pstmt.setLong(2, lecture.getProfessor().getId());
      pstmt.setString(3, key);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void updateLecture(Lecture lecture) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      String key = lecture.getEnrolmentKey() != null ? lecture.getEnrolmentKey() : "";
      PreparedStatement pstmt = con
              .prepareStatement("update cursuri set denumire=?, ref_profesor=?, enrolment_key=? where id = ?");
      pstmt.setString(1, lecture.getLectureName());
      pstmt.setLong(2, lecture.getProfessor().getId());
      pstmt.setString(3, key);
      pstmt.setLong(4, lecture.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

  }

  @Override
  public void deleteLecture(long id) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("delete from cursuri where id = ?");
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
  public void registerUserToLecture(long userId, long lectureId, String key) throws RepositoryException {
    if (getLectureByIdAndKey(lectureId, key) == null)
      throw new RepositoryException("Wrong enrolment key for the requested lecture");
    Connection conn = null;
    try {
      conn = JDBCUtil.getNewConnection();
      String query = "INSERT INTO studenti_inscrisi (ref_student, ref_curs) VALUES (?, ?)";
      PreparedStatement statement = conn.prepareStatement(query);
      statement.setLong(1, userId);
      statement.setLong(2, lectureId);
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new RepositoryException(ex.getMessage(), ex);
    } finally {
      if (conn != null)
        JDBCUtil.closeConnection(conn);
    }
  }


  private Lecture getLectureByIdAndKey(long lectureId, String key) throws RepositoryException {
    Connection conn = null;
    try {
      key = key == null ? "" : key;
      conn = JDBCUtil.getNewConnection();
      String query = "SELECT * FROM cursuri WHERE id = ? AND enrolment_key = ?";
      PreparedStatement statement = conn.prepareStatement(query);
      statement.setLong(1, lectureId);
      statement.setString(2, key);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return new Lecture(resultSet.getLong(1), null, resultSet.getString(2), resultSet.getString(4));
      }
    } catch (SQLException ex) {
      throw new RepositoryException(ex.getMessage(), ex);
    } finally {
      if (conn != null)
        JDBCUtil.closeConnection(conn);
    }
    return null;
  }
}
