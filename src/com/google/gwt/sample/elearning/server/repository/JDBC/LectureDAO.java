package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.Lecture;

import java.util.List;

/**
 * Created by Valy on 11/22/2015.
 */
public interface LectureDAO {
    List<Lecture> getAllLectures() throws RepositoryException;
    Lecture getLectureById(long id) throws RepositoryException;
    Lecture getLectureByName(String name) throws RepositoryException;
    List<Lecture> getLecturesByProfessor(long id) throws RepositoryException;
    void insertLecture(Lecture lecture) throws RepositoryException;
    void updateLecture(Lecture user) throws RepositoryException;
    void deleteLecture(long id) throws RepositoryException;
}
