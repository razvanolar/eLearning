package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.server.JDBC.LectureJDBCImpl;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.List;

/**
 *
 * Created by Ambrozie Paval on 11/19/2015.
 */
public class LectureServiceImpl extends RemoteServiceServlet implements LectureService {

  @Override
  public void createLecture(Lecture lecture) throws ELearningException{
    LectureJDBCImpl lectureJDBC = new LectureJDBCImpl();
    lectureJDBC.createLecture(lectureJDBC);
  }

  @Override
  public List<Lecture> getAllLectures() throws ELearningException {
    List<Lecture> lectures;
    LectureJDBCImpl lectureJDBC = new LectureJDBCImpl();
    lectures = lectureJDBC.getAllLectures();
    return lectures;
  }

  @Override
  public List<Lecture> getAllLecturesByProfessor(long idProfessor) throws ELearningException {
    List<Lecture> lectures;
    LectureJDBCImpl lectureJDBC = new LectureJDBCImpl();
    lectures = lectureJDBC.getAllLecturesByProfessor(idProfessor);
    return lectures;
  }

  @Override
  public Lecture getLectureById(long id) throws ELearningException{
    Lecture lecture;
    LectureJDBCImpl lectureJDBC = new LectureJDBCImpl();
    lecture = lectureJDBC.getLectureById(id);
    return lecture;
  }

  @Override
  public void updateLecture(Lecture lecture) throws ELearningException{
    LectureJDBCImpl lectureJDBC = new LectureJDBCImpl();
    lectureJDBC.updateLecture(lecture);
  }

  @Override
  public void removeLecture(long id) throws ELearningException{
    LectureJDBCImpl lectureJDBC;
    lectureJDBC = new LectureJDBCImpl();
    lectureJDBC.removeLecture(id);
  }
}
