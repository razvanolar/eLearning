package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.server.repository.DAOFactory;
import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcUserDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.LectureDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.UserDAO;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Valy on 11/21/2015.
 */
public class JdbcUserDAOTest {
    UserDAO userDAO;
    @Before
    public void getUserDAO(){
        userDAO = new JdbcUserDAO();
    }
    @Test
    public void getAllUsersTest(){

        assert userDAO.getAllUsers().size()>0;
    }
    @Test
    public void getUserByIdTest(){
        UserData user = userDAO.getUserById(1);
        assert user.getPassword().equals("admin") && user.getPassword().equals("admin");
    }

    @Test
    public void getUserByUsernameTest(){
        UserData user = userDAO.getUserByUsername("admin");
        assert user.getPassword().equals("admin") && user.getId()==1;
    }

    @Test
    public void insertUserTest(){
        UserData user = new UserData("vali","vali","Valentina","Moraru","mvir1515@yahoo.com", UserRoleTypes.USER);
        userDAO.insertUser(user);
        UserData user2 = userDAO.getUserByUsername("vali");
        assert user2.getFirstName().equals(user.getFirstName()) && user2.getLastName().equals(user.getLastName());
    }

    @Test
    public void updateUserTest(){
        UserData user = userDAO.getUserByUsername("vali");
        user.setFirstName("Moraru");
        user.setLastName("Valentina");
        userDAO.updateUser(user);
        UserData user2 = userDAO.getUserById(user.getId());
        assert user2.getFirstName().equals(user.getFirstName()) && user2.getLastName().equals(user.getLastName());
    }

    @Test
    public void deleteUserTest(){
        try {
            UserData user = userDAO.getUserByUsername("vali");
            userDAO.deleteUser(user.getId());
        }catch (RepositoryException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getUserDAOTest(){
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO= factory.getUserDAO();
        LectureDAO lectureDAO= factory.getLectureDAO();
    }
}
