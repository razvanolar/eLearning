package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcLectureDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcUserDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.LectureDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.UserDAO;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Valy on 11/22/2015.
 */
public class JdbcLectureDAOTest {

    UserDAO userDAO;
    LectureDAO lectureDAO;

    @Before
    public void getDAOs(){

        userDAO = new JdbcUserDAO();
        lectureDAO = new JdbcLectureDAO();
    }
    @Test
    public void getAllLecturesTest(){

        assert lectureDAO.getAllLectures().size()>0;
    }

    @Test
    public void getLectureByIdTest(){
        Lecture lecture = lectureDAO.getLectureById(1);
        assert lecture.getLectureName().equals("curs1");
    }

    @Test
    public void getLectureByNameTest(){
        Lecture lecture = lectureDAO.getLectureByName("curs1");
        assert lecture.getLectureName().equals("curs1");
    }

    @Test
    public void getLecturesByProfessorTest(){

        assert lectureDAO.getLecturesByProfessor(1).size()>0;
    }

    @Test
    public void insertLectureTest(){
        Professor professor = new Professor( userDAO.getUserById(2));
        Lecture lecture = new Lecture("CursTest", professor);
        lectureDAO.insertLecture(lecture);
    }

    @Test
    public void updateLectureTest(){
        Lecture lecture = lectureDAO.getLectureByName("CursTest");
        lecture.setLectureName("Test");
        lectureDAO.updateLecture(lecture);
        Lecture lecture1 = lectureDAO.getLectureByName("Test");
        assert lecture1.getLectureName().equals("Test");// && lecture.getProfessor().getId()==2;
    }

    @Test
    public void deleteLectureTest(){
        try {
            Lecture lecture = lectureDAO.getLectureByName("Test");
            lectureDAO.deleteLecture(lecture.getId());
        }catch (RepositoryException e){
            e.printStackTrace();
        }
    }

}
