package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.JDBC.JDBCUtil;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.Grade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valy on 1/8/2016.
 */
public class JdbcGradeDAO implements GradeDAO {
  @Override
  public List<Grade> getAllHomeworkGrades() throws RepositoryException {
    ArrayList<Grade> gradeslist = new ArrayList<Grade>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from note_teme");
      gradeslist = getGradesList(rs);
    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return gradeslist;
  }

  @Override
  public List<Grade> getAllLectureTestGrades() throws RepositoryException {
    ArrayList<Grade> gradeslist = new ArrayList<Grade>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from note_teste");
      gradeslist = getGradesList(rs);
    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return gradeslist;
  }

  @Override
  public List<Grade> getHomeworkGrades(long homeworkId) throws RepositoryException {
    ArrayList<Grade> gradeslist = new ArrayList<Grade>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from note_teme where ref_tema = ?");
      pstmt.setLong(1, homeworkId);
      ResultSet rs = pstmt.executeQuery();
      gradeslist = getGradesList(rs);
    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return gradeslist;
  }

  @Override
  public List<Grade> getLectureTestGrades(long lectureId) throws RepositoryException {
    ArrayList<Grade> gradeslist = new ArrayList<Grade>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from note_teste where ref_test = ?");
      pstmt.setLong(1, lectureId);
      ResultSet rs = pstmt.executeQuery();
      gradeslist = getGradesList(rs);
    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return gradeslist;
  }

  @Override
  public List<Grade> getStudentLecturesTestGrades(List<Long> lectureDataIds, long studentId) throws RepositoryException {
    /**
     * returns a list with grades for all LectureData from lectureDataIds
     */
    ArrayList<Grade> gradeslist = new ArrayList<Grade>();
    Connection con = null;
    String instmt="";
    for(int i=0;i<lectureDataIds.size()-1;i++)
      instmt +="?,";
    instmt+="?";
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from note_teste where ref_student=? and ref_test in ("+instmt+")");
      pstmt.setLong(1, studentId);
      for(int i=0;i<lectureDataIds.size();i++)
        pstmt.setLong(i+2,lectureDataIds.get(i));
      ResultSet rs = pstmt.executeQuery();
      gradeslist = getGradesList(rs);
    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return gradeslist;
  }

  @Override
  public List<Grade> getStudentHomeworkGrades(List<Long> homeworkIds, long studentId) throws RepositoryException {
    /**
     * returns a list with grades for all homeworkIds
     */
    ArrayList<Grade> gradeslist = new ArrayList<Grade>();
    Connection con = null;
    String instmt="";
    for(int i=0;i<homeworkIds.size()-1;i++)
      instmt +="?,";
    instmt+="?";
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from note_teme where ref_student=? and ref_tema in ("+instmt+")");
      pstmt.setLong(1, studentId);
      for(int i=0;i<homeworkIds.size();i++)
        pstmt.setLong(i+2,homeworkIds.get(i));
      ResultSet rs = pstmt.executeQuery();
      gradeslist = getGradesList(rs);
    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return gradeslist;
  }

  @Override
  public void saveGrade(Grade grade, String className) throws RepositoryException {
    /**
     * className = HomeworkData or LectureData dependin on which type of note must be saved
     */
    Connection con = null;
    PreparedStatement pstmt;
    try {
      con = JDBCUtil.getNewConnection();
      if(className.equals("HomeworkData")){
        pstmt = con
                .prepareStatement("insert into note_teme (ref_tema, ref_student, nota) values (?, ?, ?)");
      }
      else{
        pstmt = con
                .prepareStatement("insert into note_teste (ref_test, ref_student, nota) values (?, ?, ?)");
      }
      pstmt.setLong(1, grade.getTestId());
      pstmt.setLong(2,grade.getStudentId());
      pstmt.setInt(3, grade.getGrade());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException("Nota nu a putut fi adaugata!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void updateGrade(Grade grade, String className) throws RepositoryException {
    /**
     * className = HomeworkData or LectureData dependin on which type of note must be updated
     */
    Connection con = null;
    PreparedStatement pstmt;
    try {
      con = JDBCUtil.getNewConnection();
      if(className.equals("HomeworkData")){
        pstmt = con
                .prepareStatement("update note_teme set nota =?");
      }
      else{
        pstmt = con
                .prepareStatement("update note_teste set nota =?");
      }
      pstmt.setInt(1, grade.getGrade());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException("Nota nu a putut fi modificata!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void deleteGrade(Grade grade, String className) throws RepositoryException {
    /**
     * className = HomeworkData or LectureData dependin on which type of note must be deleted
     */
    Connection con = null;
    PreparedStatement pstmt;
    try {
      con = JDBCUtil.getNewConnection();
      if(className.equals("HomeworkData")){
        pstmt = con
                .prepareStatement("delete from note_teme where id =?");
      }
      else{
        pstmt = con
                .prepareStatement("delete from note_teste where id =?");
      }
      pstmt.setLong(1, grade.getId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException("Nota nu a putut fi stearsa!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void deleteStudentGrades(long studentId) throws RepositoryException {
    Connection con = null;
    PreparedStatement pstmt;
    try {
      con = JDBCUtil.getNewConnection();
        pstmt = con.prepareStatement("delete from note_teme where ref_student =?");
        pstmt.setLong(1, studentId);
        pstmt.executeUpdate();
        pstmt = con.prepareStatement("delete from note_teste where ref_student =?");
      pstmt.setLong(1, studentId);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException("Notele studentului nu au putut fi sterse!", e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  private ArrayList<Grade> getGradesList(ResultSet rs) throws SQLException {
    ArrayList<Grade> gradeslist = new ArrayList<Grade>();
    while (rs.next()) {
      Grade grade = new Grade (rs.getLong(1),rs.getLong(2),rs.getLong(3),rs.getInt(4));
      gradeslist.add(grade);
    }
    return gradeslist;
  }
}
