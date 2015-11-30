package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.Tree;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/***
 * Created by Ambrozie Paval on 11/19/2015.
 */
@RemoteServiceRelativePath("LectureService")
public interface LectureService extends RemoteService {

  void createLecture(Lecture lecture) throws ELearningException;

  List<Lecture> getAllLectures() throws ELearningException;

  List<Lecture> getAllLecturesByProfessor(long idProfessor) throws ELearningException;

  Lecture getLectureById(long id) throws ELearningException;

  void updateLecture(Lecture lecture) throws ELearningException;

  void removeLecture(long id) throws ELearningException;

  String addLectureHtmlFile(UserData user, String path, String title, long lectureId, String text) throws ELearningException;

  void createFolder(UserData user, String path, String name, long lectureId) throws ELearningException;

  Tree<FileData> getLectureFilesTree(UserData user, long lectureId) throws ELearningException;

  String getHtmlFileBodyContent(UserData userData, long lectureId, String path, String title) throws ELearningException;

  void deleteFile(UserData user, long lectureId, String path, String title) throws ELearningException;

  List<LWLectureTestData> getAllLWTests(UserData user, long lectureId) throws ELearningException;

  LectureTestData getTest(UserData user, long lectureId, String testName) throws ELearningException;
}
