package com.google.gwt.sample.elearning.server.repository;

import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcDAOFactory;
import com.google.gwt.sample.elearning.server.repository.JDBC.LectureDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.UserDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.VideoLinkDAO;

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
}