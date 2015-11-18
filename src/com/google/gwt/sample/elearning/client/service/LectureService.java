package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

/**
 * Created by Ambrozie Paval on 11/19/2015.
 */
public interface LectureService extends RemoteService {

  void createLecture(Lecture lecture);

  List<Lecture> getAllLectures();

  List<Lecture> getAllLecturesByProfessor(int idProfessor);

  Lecture getLectureById(int id);

  void updateLecture(Lecture lecture);

  void removeLecture(int id);
}
