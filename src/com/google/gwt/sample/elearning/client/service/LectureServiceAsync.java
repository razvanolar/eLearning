package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.Tree;
import com.google.gwt.sample.elearning.shared.model.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

public interface LectureServiceAsync {
  void createLecture(Lecture lecture, AsyncCallback<Void> async);

  void getAllLectures(AsyncCallback<List<Lecture>> async);

  void getAllLecturesByProfessor(long idProfessor, AsyncCallback<List<Lecture>> async);

  void getLectureById(long id, AsyncCallback<Lecture> async);

  void updateLecture(Lecture lecture, AsyncCallback<Void> async);

  void removeLecture(long id, AsyncCallback<Void> async);

  void addLectureHtmlFile(String path, String title, long lectureId, String text, AsyncCallback<String> async);

  void createFolder(UserData user, String path, String name, long lectureId, AsyncCallback<Void> async);

  void getLectureFilesTree(UserData user, long lectureId, AsyncCallback<Tree<FileData>> async);

  void getHtmlFileBodyContent(UserData userData, long lectureId, String path, String title, AsyncCallback<String> async);

  void deleteFile(long lectureId, String path, String title, AsyncCallback<Void> async);

  void getAllLWTests(UserData user, long lectureId, AsyncCallback<List<LWLectureTestData>> async);

  void getTest(UserData user, long lectureId, String testName, AsyncCallback<LectureTestData> async);

  void getLectureVideos(long lectureId, AsyncCallback<List<VideoLinkData>> async);

  void saveVideoData(long lectureId, VideoLinkData videoLinkData, AsyncCallback<Void> async);

  void updateVideoData(long lectureId, VideoLinkData videoLinkData, AsyncCallback<Void> async);

  void deleteVideoData(long lectureId, VideoLinkData videoLinkData, AsyncCallback<Void> async);

  void createTest(LectureTestData lectureTestData, AsyncCallback<Void> async);

  void getAllHomeworks(UserData user, long lectureId, AsyncCallback<List<HomeworkData>> async);

  void saveHomeworkData(long lectureId, HomeworkData homeworkData, AsyncCallback<Void> async);

  void updateHomeworkData(long lectureId, HomeworkData homeworkData, AsyncCallback<Void> async);
}
