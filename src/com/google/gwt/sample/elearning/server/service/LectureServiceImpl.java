package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.server.repository.DAOFactory;
import com.google.gwt.sample.elearning.server.repository.JDBC.LectureDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.VideoLinkDAO;
import com.google.gwt.sample.elearning.server.service.collector.test.TestXMLConvertor;
import com.google.gwt.sample.elearning.server.service.lecture_service_util.LectureFilesUtil;
import com.google.gwt.sample.elearning.server.service.lecture_service_util.LectureTestsUtil;
import com.google.gwt.sample.elearning.shared.Tree;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.*;

/**
 *
 * Created by Ambrozie Paval on 11/19/2015.
 */
public class LectureServiceImpl extends RemoteServiceServlet implements LectureService {

  private DAOFactory factory = DAOFactory.getInstance();
  private LectureDAO lectureDAO = factory.getLectureDAO();
  private VideoLinkDAO videoLinkDAO = factory.getVideoLinkDAO();
  private LectureFilesUtil filesUtil = new LectureFilesUtil();
  private LectureTestsUtil testsUtil = new LectureTestsUtil();

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
  public String addLectureHtmlFile(String path, String title, long lectureId, String text) throws ELearningException {
    return filesUtil.addLectureHtmlFile(path, title, lectureId, text);
  }

  @Override
  public void createFolder(UserData user, String path, String name, long lectureId) throws ELearningException {
    filesUtil.createFolder(user, path, name, lectureId);
  }

  @Override
  public Tree<FileData> getLectureFilesTree(UserData user, long lectureId) throws ELearningException {
    return filesUtil.getLectureFilesTree(user, lectureId);
  }

  @Override
  public String getHtmlFileBodyContent(UserData userData, long lectureId, String path, String title) throws ELearningException {
    return filesUtil.getHtmlFileBodyContent(userData, lectureId, path, title);
  }

  @Override
  public void deleteFile(long lectureId, String path, String title) throws ELearningException {
    filesUtil.deleteFile(lectureId, path, title);
  }

  @Override
  public List<LWLectureTestData> getAllLWTests(UserData user, long lectureId) throws ELearningException {
    return testsUtil.getAllLWTests(user, lectureId);
  }

  @Override
  public LectureTestData getTest(UserData user, long lectureId, String testName) throws ELearningException {
    return testsUtil.getTest(user, lectureId, testName);
  }

  @Override
  public List<VideoLinkData> getLectureVideos(long lectureId) throws ELearningException {
    return videoLinkDAO.getLectureVideos(lectureId);
  }

  @Override
  public void saveVideoData(long lectureId, VideoLinkData videoLinkData) throws ELearningException {
    videoLinkDAO.saveVideoLinkData(lectureId, videoLinkData);
  }

  @Override
  public void createTest(LectureTestData lectureTestData, long professorId) throws ELearningException {
    // 1. Create .xml file
    TestXMLConvertor testXMLConvertor = new TestXMLConvertor();
    String xmlValue = testXMLConvertor.convertToXML(lectureTestData);
    testsUtil.createTestXMLFile(xmlValue, lectureTestData.getName(), lectureTestData.getCourseId(), professorId);

    // 2. Create .props file
    int totalScore = 0;
    for(QuestionData question : lectureTestData.getQuestions())
      totalScore += question.getScore();
    testsUtil.createTestPropsFile(lectureTestData.getQuestions().size(), totalScore, lectureTestData.getName(),
        lectureTestData.getCourseId(), professorId);
  }

  @Override
  public void updateVideoData(long lectureId, VideoLinkData videoLinkData) throws ELearningException {
    videoLinkDAO.updateVideoLinkData(lectureId, videoLinkData);
  }

  @Override
  public void deleteVideoData(long lectureId, VideoLinkData videoLinkData) throws ELearningException {
    videoLinkDAO.deleteVideoLinkData(lectureId, videoLinkData);
  }
}