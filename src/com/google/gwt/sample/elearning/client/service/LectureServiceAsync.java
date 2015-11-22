package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

public interface LectureServiceAsync {
  void createLecture(Lecture lecture, AsyncCallback<Void> async);

  void getAllLectures(AsyncCallback<List<Lecture>> async);

  void getAllLecturesByProfessor(int idProfessor, AsyncCallback<List<Lecture>> async);

  void getLectureById(int id, AsyncCallback<Lecture> async);

  void updateLecture(Lecture lecture, AsyncCallback<Void> async);

  void removeLecture(int id, AsyncCallback<Void> async);
}