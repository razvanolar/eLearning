package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

/**
 * Created by Ambrozie Paval on 11/19/2015.
 */
public interface LectureService extends RemoteService {

  void createLecture(Lecture lecture) throws ELearningException;

  List<Lecture> getAllLectures() throws ELearningException;

  List<Lecture> getAllLecturesByProfessor(int idProfessor) throws ELearningException;

  Lecture getLectureById(int id) throws ELearningException;

  void updateLecture(Lecture lecture) throws ELearningException;

  void removeLecture(int id) throws ELearningException;
}
