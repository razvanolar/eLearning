package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.Tree;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;
import java.util.Map;

/***
 * Created by Ambrozie Paval on 11/19/2015.
 */
@RemoteServiceRelativePath("LectureService")
public interface LectureService extends RemoteService {

  void createLecture(Lecture lecture) throws ELearningException;

  List<Lecture> getAllLectures() throws ELearningException;

  List<Lecture> getAllLecturesByProfessor(long idProfessor) throws ELearningException;

  List<Lecture> getAllLecturesByStudent(long userId) throws ELearningException;

  Lecture getLectureById(long id) throws ELearningException;

  FilteredLecturesData getLecturesEnrollementsListByUser(long userId) throws ELearningException;

  void updateLecture(Lecture lecture) throws ELearningException;

  void removeLecture(long id) throws ELearningException;

  void registerUserToLecture(long userId, long lectureId, String enrollmentKey) throws ELearningException;

  String addLectureHtmlFile(String path, String title, long lectureId, String text) throws ELearningException;

  void createFolder(UserData user, String path, String name, long lectureId) throws ELearningException;

  Tree<FileData> getLectureFilesTree(UserData user, long lectureId) throws ELearningException;

  String getHtmlFileBodyContent(UserData userData, long lectureId, String path, String title) throws ELearningException;

  void deleteFile(long lectureId, String path, String title) throws ELearningException;

  List<LWLectureTestData> getAllLWTests(UserData user, long lectureId) throws ELearningException;

  LectureTestData getTest(UserData user, long lectureId, String testName) throws ELearningException;

  List<VideoLinkData> getLectureVideos(long lectureId) throws ELearningException;

  void saveVideoData(long lectureId, VideoLinkData videoLinkData) throws ELearningException;

  void updateVideoData(long lectureId, VideoLinkData videoLinkData) throws ELearningException;

  void deleteVideoData(long lectureId, VideoLinkData videoLinkData) throws ELearningException;

  void createTest(LectureTestData lectureTestData) throws ELearningException;

  List<HomeworkData> getAllHomeworks(UserData user, long lectureId) throws ELearningException;

  void saveHomeworkData(long lectureId, HomeworkData homeworkData) throws ELearningException;

  void updateHomeworkData(long lectureId, HomeworkData homeworkData) throws ELearningException;

  void deleteHomeworkData(long lectureId, HomeworkData homeworkData) throws ELearningException;

  long resolveTest(LectureTestData testData) throws ELearningException;
}
