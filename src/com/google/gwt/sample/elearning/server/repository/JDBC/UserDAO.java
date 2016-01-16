package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.UserData;

import java.util.List;

/***
 * Created by Valy on 11/21/2015.
 */
public interface UserDAO {

    List<UserData> getAllUsers() throws RepositoryException;
    UserData getUserById(long id) throws RepositoryException;
    UserData getUserByUsername(String username) throws RepositoryException;
    UserData getUserByEmail(String email) throws RepositoryException;
    void insertUser(UserData user) throws RepositoryException;
    void updateUser(UserData user) throws RepositoryException;
    void deleteUser(long id) throws RepositoryException;
    List<UserData> getAllUsersByLecture(long id);
    List<UserData> getAllUsersByProfessor(long id);
}
