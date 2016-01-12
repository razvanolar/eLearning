package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.JDBC.JDBCUtil;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valy on 1/12/2016.
 */
public class JdbcHomeworkDAO implements HomeworkDAO {
  @Override
  public List<HomeworkData> getAllHomeworks() throws RepositoryException {
    ArrayList<HomeworkData> list = new ArrayList<HomeworkData>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from teme");
      list = getHomeworkList(rs);

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return list;
  }

  @Override
  public HomeworkData getHomeworkById(long id) throws RepositoryException {
    Connection con = null;

    HomeworkData homeworkData = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from teme where id = ?");
      pstmt.setLong(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        homeworkData = new HomeworkData(rs.getLong(1),rs.getString(2),rs.getDate(3),rs.getDate(4), rs.getLong(5),rs.getInt(6));
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }

    return homeworkData;
  }

  @Override
  public List<HomeworkData> getHomeworkByLecture(long lectureId) throws RepositoryException {
    ArrayList<HomeworkData> list = new ArrayList<HomeworkData>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from teme where ref_curs = ?");
      pstmt.setLong(1, lectureId);
      ResultSet rs = pstmt.executeQuery();
      list = getHomeworkList(rs);

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return list;
  }

  @Override
  public void insertHomework(HomeworkData homeworkData) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
              .prepareStatement("insert into teme(denumire,timp_start,timp_sfarsit,ref_curs,punctaj) values (?,?,?,?,?)");
      pstmt.setString(1, homeworkData.getTitle());
      pstmt.setDate(2, new Date(homeworkData.getBeginDate().getTime()));
      pstmt.setDate(3, new Date(homeworkData.getBeginDate().getTime()));
      pstmt.setLong(4,homeworkData.getCourseId());
      pstmt.setInt(5,homeworkData.getScore());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException("Tema nu a putut fi adaugata!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void updateHomework(HomeworkData homeworkData) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
              .prepareStatement("update teme set denumire=?,timp_start=?,timp_sfarsit=?,ref_curs=?,punctaj=? where id =?");

      pstmt.setString(1, homeworkData.getTitle());
      pstmt.setDate(2, new Date(homeworkData.getBeginDate().getTime()));
      pstmt.setDate(3, new Date(homeworkData.getBeginDate().getTime()));
      pstmt.setLong(4,homeworkData.getCourseId());
      pstmt.setInt(5,homeworkData.getScore());
      pstmt.setLong(6,homeworkData.getId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException("Tema nu a putut fi modificata!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void deleteHomework(long id) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
              .prepareStatement("delete from teme where id =?");

      pstmt.setLong(1,id);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException("Tema nu a putut fi stearsa!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  private ArrayList<HomeworkData> getHomeworkList(ResultSet rs) throws SQLException {
    ArrayList<HomeworkData> list = new ArrayList<HomeworkData>();
    while (rs.next()) {
      HomeworkData homeworkData = new HomeworkData(rs.getLong(1),rs.getString(2),rs.getDate(3),rs.getDate(4), rs.getLong(5),rs.getInt(6));
      list.add(homeworkData);
    }
    return list;
  }
}
