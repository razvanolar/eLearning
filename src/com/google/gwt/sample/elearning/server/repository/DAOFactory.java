package com.google.gwt.sample.elearning.server.repository;

import com.google.gwt.sample.elearning.server.repository.JDBC.*;
import com.google.gwt.sample.elearning.shared.model.Grade;

/***
 * Created by Valy on 11/21/2015.
 */
public abstract class DAOFactory {
    public static DAOFactory getInstance() {
        return new JdbcDAOFactory();
    }

    public abstract UserDAO getUserDAO();

    public abstract LectureDAO getLectureDAO();

    public abstract VideoLinkDAO getVideoLinkDAO();

    public abstract HomeworkDAO getHomeworkDAO();

    public abstract LectureTestDAO getLectureTestDAO();

    public abstract GradeDAO getGradeDAO();
}