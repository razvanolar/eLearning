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
 * Created by Valy on 11/22/2015.
 */
public class JdbcLectureDAO implements LectureDAO {

    UserDAO userDAO = new JdbcUserDAO();
    public JdbcLectureDAO(){
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
                Professor professor = new Professor( userDAO.getUserById(rs.getLong(3)));
                Lecture lecture = new Lecture(rs.getLong(1), professor,rs.getString(2));
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
                Professor professor = new Professor( userDAO.getUserById(rs.getLong(3)));
                lecture = new Lecture(rs.getLong(1), professor,rs.getString(2));
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
                Professor professor = new Professor( userDAO.getUserById(rs.getLong(3)));
                lecture = new Lecture(rs.getLong(1), professor, rs.getString(2));
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
    public List<Lecture> getLecturesByProfessor(long id) throws RepositoryException {
        ArrayList<Lecture> lectures = new ArrayList<Lecture>();
        Connection con = null;
        try {
            con = JDBCUtil.getNewConnection();
            PreparedStatement pstmt = con.prepareStatement("select * from cursuri where id = ?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Professor professor = new Professor( userDAO.getUserById(rs.getLong(3)));
                Lecture lecture = new Lecture(rs.getLong(1),professor, rs.getString(2));
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
    public void insertLecture(Lecture lecture) throws RepositoryException {
        Connection con = null;
        try {
            con = JDBCUtil.getNewConnection();
            PreparedStatement pstmt = con
                    .prepareStatement("insert into cursuri(denumire, ref_profesor) values(?,?)");
            pstmt.setString(1, lecture.getLectureName());
            pstmt.setLong(2, lecture.getProfessor().getId());
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
            PreparedStatement pstmt = con
                    .prepareStatement("update cursuri set denumire=?, ref_profesor=? where id = ?");
            pstmt.setString(1, lecture.getLectureName());
            pstmt.setLong(2, lecture.getProfessor().getId());
            pstmt.setLong(3, lecture.getId());
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
}
