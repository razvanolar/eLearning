package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.server.repository.DAOFactory;
import com.google.gwt.sample.elearning.server.repository.JDBC.HomeworkDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.LectureDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.LectureTestDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.VideoLinkDAO;
import com.google.gwt.sample.elearning.server.service.collector.homework.HomeworkXMLConverter;
import com.google.gwt.sample.elearning.server.service.collector.test.TestXMLConvertor;
import com.google.gwt.sample.elearning.server.service.lecture_service_util.LectureFilesUtil;
import com.google.gwt.sample.elearning.server.service.lecture_service_util.LectureHomeworkUtil;
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
  private HomeworkDAO homeworkDAO = factory.getHomeworkDAO();
  private LectureTestDAO testDAO = factory.getLectureTestDAO();
  private LectureFilesUtil filesUtil = new LectureFilesUtil();
  private LectureTestsUtil testsUtil = new LectureTestsUtil();
  private LectureHomeworkUtil homeworkUtil = new LectureHomeworkUtil();

  @Override
  public void createLecture(Lecture lecture) throws ELearningException{
    ElearningLogger.log("Inserting new lecture: " + lecture.getLectureName());
    lectureDAO.insertLecture(lecture);
    ElearningLogger.log("Lecture: " + lecture.getLectureName() + " has been created.");
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
  public List<Lecture> getAllLecturesByStudent(long userId) throws ELearningException {
    return lectureDAO.getAllLecturesByStudent(userId);
  }

  @Override
  public Lecture getLectureById(long id) throws ELearningException{
    Lecture lecture;
    lecture = lectureDAO.getLectureById(id);
    return lecture;
  }

  @Override
  public FilteredLecturesData getLecturesEnrollementsListByUser(long userId) throws ELearningException {
    List<Lecture> enrolledLectures = lectureDAO.getEnrolledLecturesByUser(userId);
    List<Lecture> unenrolledLectures = lectureDAO.getUnenrolledLecturesByUser(userId);
    return new FilteredLecturesData(enrolledLectures, unenrolledLectures);
  }

  @Override
  public void updateLecture(Lecture lecture) throws ELearningException{
    ElearningLogger.log("Updating lecture: " + lecture.getLectureName());
    lectureDAO.updateLecture(lecture);
    ElearningLogger.log("Lecture: " + lecture.getLectureName() + " has been updated.");
  }

  @Override
  public void removeLecture(long id) throws ELearningException{
    ElearningLogger.log("Removing lecture: " + id);
    lectureDAO.deleteLecture(id);
    ElearningLogger.log("Lecture " + id + " has been deleted.");
  }

  @Override
  public void registerUserToLecture(long userId, long lectureId, String enrollmentKey) throws ELearningException {
    ElearningLogger.log("Student: " + userId + " registering to lecture: " + lectureId + " with key: " + enrollmentKey);
    lectureDAO.registerUserToLecture(userId, lectureId, enrollmentKey);
    ElearningLogger.log("Student: " + userId + " has been registered to lecture: " + lectureId);
  }

  @Override
  public String addLectureHtmlFile(String path, String title, long lectureId, String text) throws ELearningException {
    return filesUtil.addLectureHtmlFile(path, title, lectureId, text);
  }

  @Override
  public void createFolder(UserData user, String path, String name, long lectureId) throws ELearningException {
    ElearningLogger.log("Creating folder for user: " + user.getUsername() + " path\\name:" + path + " " + name + " lectureId: " + lectureId);
    filesUtil.createFolder(user, path, name, lectureId);
  }

  @Override
  public Tree<FileData> getLectureFilesTree(UserData user, long lectureId) throws ELearningException {
    ElearningLogger.log("Retrieving files tree for user: " + user.getId() + " lecture id: " + lectureId);
    return filesUtil.getLectureFilesTree(user, lectureId);
  }

  @Override
  public String getHtmlFileBodyContent(UserData userData, long lectureId, String path, String title) throws ELearningException {
    return filesUtil.getHtmlFileBodyContent(userData, lectureId, path, title);
  }

  @Override
  public void deleteFile(long lectureId, String path, String title) throws ELearningException {
    ElearningLogger.log("Deleting file - lectureId: " + lectureId + " path: " + path + " tittle" + title);
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
    ElearningLogger.log("Saving video data for lectureId: " + lectureId + " video: " + videoLinkData.getTitle());
    videoLinkDAO.saveVideoLinkData(lectureId, videoLinkData);
  }

  @Override
  public void createTest(LectureTestData lectureTestData) throws ELearningException {
    ElearningLogger.log("Creating new test - " + lectureTestData.getName() + " lectureId" + lectureTestData.getLectureId());
    testDAO.insertLectureTest(lectureTestData);
    // 1. Create .xml file
    TestXMLConvertor testXMLConvertor = new TestXMLConvertor();
    String xmlValue = testXMLConvertor.convertToXML(lectureTestData);
    testsUtil.createTestXMLFile(xmlValue, lectureTestData.getName(), lectureTestData.getLectureId());

    // 2. Create .props file
    int totalScore = 0;
    for(QuestionData question : lectureTestData.getQuestions())
      totalScore += question.getScore();
    testsUtil.createTestPropsFile(lectureTestData.getQuestions().size(), totalScore, lectureTestData.getName(),
        lectureTestData.getLectureId());
  }

  @Override
  public void updateVideoData(long lectureId, VideoLinkData videoLinkData) throws ELearningException {
    ElearningLogger.log("Updating video data for lectureId: " + lectureId + " video: " + videoLinkData.getTitle());
    videoLinkDAO.updateVideoLinkData(lectureId, videoLinkData);
  }

  @Override
  public void deleteVideoData(long lectureId, VideoLinkData videoLinkData) throws ELearningException {
    ElearningLogger.log("Deleting video data for lectureId: " + lectureId + " video: " + videoLinkData.getTitle());
    videoLinkDAO.deleteVideoLinkData(lectureId, videoLinkData);
  }

  @Override
  public List<HomeworkData> getAllHomeworks(UserData user, long lectureId) throws ELearningException {
    return homeworkUtil.getAllHomeworks(user,lectureId);
  }

  @Override
  public void saveHomeworkData(long lectureId, HomeworkData homeworkData) throws ELearningException {
    ElearningLogger.log("Saving homework data - lectureId: " + lectureId + " homework " + homeworkData.getTitle());
    homeworkDAO.insertHomework(homeworkData);
    HomeworkXMLConverter homeworkXMLConverter = new HomeworkXMLConverter();
    String xmlValue = homeworkXMLConverter.convertToXML(homeworkData);
    homeworkUtil.createHomework(xmlValue, lectureId, homeworkData);
  }

  @Override
  public void updateHomeworkData(long lectureId, HomeworkData homeworkData) throws ELearningException {
    ElearningLogger.log("Updating  homework data - lectureId: " + lectureId + " homework " + homeworkData.getTitle());
    homeworkDAO.updateHomework(homeworkData);
    HomeworkXMLConverter homeworkXMLConverter = new HomeworkXMLConverter();
    String xmlValue = homeworkXMLConverter.convertToXML(homeworkData);
    homeworkUtil.updateHomework(xmlValue,lectureId, homeworkData);
  }

  @Override
  public void deleteHomeworkData(long lectureId, HomeworkData homeworkData) throws ELearningException {
    ElearningLogger.log("Deleting homework data - lectureId: " + lectureId + " homework " + homeworkData.getTitle());
    homeworkDAO.deleteHomework(homeworkData.getId());
    homeworkUtil.deleteHomework(lectureId, homeworkData);
  }

  @Override
  public long resolveTest(LectureTestData testData) throws ELearningException {
    /* save the result in DB */


    /* call LectureTestsUtil to update the files */
    testsUtil.updateTestFiles();

    /* return : test final score */
    return 0;
  }

  @Override
  public void addHomeworkSolution(long lectureid, long professorId, long userId, String homeworkTitle, String solution)
      throws ELearningException {
    homeworkUtil.resolveHomework(lectureid, professorId, userId, homeworkTitle, solution);
  }
}