package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.JDBC.JDBCUtil;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Valy on 1/12/2016.
 */

public class JdbcLectureTestDAO implements LectureTestDAO {
  @Override
  public List<LectureTestData> getAllLectureTests() throws RepositoryException {
    ArrayList<LectureTestData> list = new ArrayList<LectureTestData>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from teste");
      list = getLectureTestList(rs);

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return list;
  }

  @Override
  public LectureTestData getLectureTestById(long id) throws RepositoryException {
    Connection con = null;
    LectureTestData lectureTestData = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from teste where id = ?");
      pstmt.setLong(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        lectureTestData = new LectureTestData(rs.getLong(1),rs.getString(2),rs.getDate(3),rs.getDate(4), rs.getDate(4).getTime()-rs.getDate(3).getTime(),rs.getLong(5));
      }
    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return lectureTestData;
  }

  @Override
  public List<LectureTestData> getLectureTestByLecture(long lectureId) throws RepositoryException {
    ArrayList<LectureTestData> list = new ArrayList<LectureTestData>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from teste where ref_curs = ?");
      pstmt.setLong(1, lectureId);
      ResultSet rs = pstmt.executeQuery();
      list = getLectureTestList(rs);

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return list;
  }

  @Override
  public void insertLectureTest(LectureTestData lectureTestData) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
              .prepareStatement("insert into teste(denumire,timp_start,timp_sfarsit,ref_curs) values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, lectureTestData.getName());
      pstmt.setDate(2, new java.sql.Date(lectureTestData.getBeginDate().getTime()));
      pstmt.setDate(3, new java.sql.Date(lectureTestData.getBeginDate().getTime()));
      pstmt.setLong(4,lectureTestData.getLectureId());
      pstmt.executeUpdate();

      ResultSet generatedKeys = pstmt.getGeneratedKeys();
      if (generatedKeys.next()) {
        lectureTestData.setId(generatedKeys.getLong(1));
      }

    } catch (SQLException e) {
      throw new RepositoryException("Testul nu a putut fi adaugat!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void updateLectureTest(LectureTestData lectureTestData) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
              .prepareStatement("update teste set denumire=?,timp_start=?,timp_sfarsit=?,ref_curs=? where id =?");
      pstmt.setString(1, lectureTestData.getName());
      pstmt.setDate(2, new java.sql.Date(lectureTestData.getBeginDate().getTime()));
      pstmt.setDate(3, new java.sql.Date(lectureTestData.getBeginDate().getTime()));
      pstmt.setLong(4,lectureTestData.getLectureId());
      pstmt.setLong(5,lectureTestData.getId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException("Testul nu a putut fi modificat!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void deleteLectureTest(long id) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
              .prepareStatement("delete from teste where id =?");

      pstmt.setLong(1,id);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException("Testul nu a putut fi sters!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  private ArrayList<LectureTestData> getLectureTestList(ResultSet rs) throws SQLException {
    ArrayList<LectureTestData> list = new ArrayList<LectureTestData>();
    while (rs.next()) {
      LectureTestData lectureTestData = new LectureTestData(rs.getLong(1),rs.getString(2),rs.getDate(3),rs.getDate(4), rs.getDate(4).getTime()-rs.getDate(3).getTime(),rs.getLong(5));
      list.add(lectureTestData);
    }
    return list;
  }
}
