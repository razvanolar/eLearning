package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.JDBC.JDBCUtil;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valy on 11/21/2015.
 */
public class JdbcUserDAO implements UserDAO {

    public JdbcUserDAO(){
    }

    @Override
    public List<UserData> getAllUsers() throws RepositoryException {
        ArrayList<UserData> userlist = new ArrayList<UserData>();
        Connection con = null;
        try {
            con = JDBCUtil.getNewConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from utilizatori");
            while (rs.next()) {
                UserData user = new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), UserRoleTypes.getRoleById(rs.getLong(7)));
                userlist.add(user);
            }

        } catch (SQLException e) {
            throw new RepositoryException("Eroare la comunicarea cu BD", e);

        } finally {
            if (con != null)
                JDBCUtil.closeConnection(con);
        }
        return userlist;
    }

    @Override
    public UserData getUserById(long id) throws RepositoryException {
        Connection con = null;

        UserData user = null;
        try {
            con = JDBCUtil.getNewConnection();
            PreparedStatement pstmt = con.prepareStatement("select * from utilizatori where id = ?");
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6),UserRoleTypes.getRoleById(rs.getLong(7)));
            }

        } catch (SQLException e) {
            throw new RepositoryException("Eroare la cmunicarea cu BD", e);
        } finally {
            if (con != null)
                JDBCUtil.closeConnection(con);
        }

        return user;
    }


    @Override
    public UserData getUserByUsername(String username) throws RepositoryException {
        Connection con = null;

        UserData user = null;
        try {
            con = JDBCUtil.getNewConnection();
            PreparedStatement pstmt = con.prepareStatement("select * from utilizatori where userId = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new UserData(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6),UserRoleTypes.getRoleById(rs.getLong(7)));
            }

        } catch (SQLException e) {
            throw new RepositoryException("Eroare la cmunicarea cu BD", e);
        } finally {
            if (con != null)
                JDBCUtil.closeConnection(con);
        }

        return user;
    }

    @Override
    public void insertUser(UserData user) throws RepositoryException {
        Connection con = null;
        try {
            con = JDBCUtil.getNewConnection();
            PreparedStatement pstmt = con
                    .prepareStatement("insert into utilizatori(userId, parola, nume, prenume, email, rol) values(?,?,?,?,?,?)");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setString(5, user.getEmail());
            pstmt.setLong(6, user.getRole().getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        } finally {
            if (con != null)
                JDBCUtil.closeConnection(con);
        }

    }

    @Override
    public void updateUser(UserData user) throws RepositoryException {
        Connection con = null;
        try {
            con = JDBCUtil.getNewConnection();
            PreparedStatement pstmt = con
                    .prepareStatement("update utilizatori set parola=?, nume=?, prenume=?, email=?, rol=? where id = ?");
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getEmail());
            pstmt.setLong(5, user.getRole().getId());
            pstmt.setLong(6, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        } finally {
            if (con != null)
                JDBCUtil.closeConnection(con);
        }

    }

    @Override
    public void deleteUser(long id) throws RepositoryException {
        Connection con = null;

        try {
            con = JDBCUtil.getNewConnection();
            PreparedStatement pstmt = con.prepareStatement("delete from utilizatori where id = ?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        } finally {
            if (con != null)
                JDBCUtil.closeConnection(con);
        }

    }

}