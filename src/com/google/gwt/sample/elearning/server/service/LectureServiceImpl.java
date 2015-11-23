package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.server.ServerUtil;
import com.google.gwt.sample.elearning.server.repository.DAOFactory;
import com.google.gwt.sample.elearning.server.repository.JDBC.LectureDAO;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 * Created by Ambrozie Paval on 11/19/2015.
 */
public class LectureServiceImpl extends RemoteServiceServlet implements LectureService {

  private DAOFactory factory = DAOFactory.getInstance();
  private LectureDAO lectureDAO = factory.getLectureDAO();

  @Override
  public void createLecture(Lecture lecture) throws ELearningException{
    lectureDAO.insertLecture(lecture);
  }

  @Override
  public List<Lecture> getAllLectures() throws ELearningException {
    List<Lecture> lectures;
    lectures = lectureDAO.getAllLectures();
    return lectures;
  }

  @Override
  public List<Lecture> getAllLecturesByProfessor(long idProfessor) throws ELearningException {
    List<Lecture> lectures;
    lectures = lectureDAO.getLecturesByProfessor(idProfessor);
    return lectures;
  }

  @Override
  public Lecture getLectureById(long id) throws ELearningException{
    Lecture lecture;
    lecture = lectureDAO.getLectureById(id);
    return lecture;
  }

  @Override
  public void updateLecture(Lecture lecture) throws ELearningException{
    lectureDAO.updateLecture(lecture);
  }

  @Override
  public void removeLecture(long id) throws ELearningException{
    lectureDAO.deleteLecture(id);
  }

  @Override
  public String addLectureFile(String title, String text) throws ELearningException {
    PrintWriter writer = null;
    try {
      title = title + ".html";
      String path = ServerUtil.LECTURES_PATH + title;
      writer = new PrintWriter(path, "UTF-8");
      writer.println(ServerUtil.getHtmlDocumentText(title, text));
      return title;
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
      throw new ELearningException(e);
    } finally {
      if (writer != null)
        writer.close();
    }
  }
}