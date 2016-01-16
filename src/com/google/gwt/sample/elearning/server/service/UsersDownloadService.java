package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcUserDAO;
import com.google.gwt.sample.elearning.server.service.collector.user.UserXMLConverter;
import com.google.gwt.sample.elearning.shared.model.UserData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by razvanolar on 23.12.2015.
 */
public class UsersDownloadService extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String usersParameter = req.getParameter("users");
    String[] usersIDs = usersParameter.split("\\$");

    String usersXML = getUsersXML(usersIDs);

    OutputStream outputStream = resp.getOutputStream();

    resp.setContentType("application/xml");
    resp.setHeader("Content-Disposition:", "attachment;filename=" + "\"users.xml\"");
    resp.setHeader("Connection:", "Close");
    int BUFFER = 1024 * 100;
    resp.setBufferSize(BUFFER);

    byte[] buffer = usersXML.getBytes();
    int sum = buffer.length;
    outputStream.write(buffer, 0, sum);
    outputStream.close();

    resp.setContentLength(sum);
  }

  private String getUsersXML(String[] userIds){
    List<UserData> users = getUsersByIds(userIds);
    UserXMLConverter userXMLConverter = new UserXMLConverter();
    return userXMLConverter.convertToXML(users);
  }

  private List<UserData> getUsersByIds(String[] userIds) {
    List<Integer> userIdsList = getUserIdsByString(userIds);
    List<UserData> userDataList = new ArrayList<>();

    JdbcUserDAO userJDBC = new JdbcUserDAO();
    userDataList.addAll(userIdsList.stream().map(userJDBC::getUserById).collect(Collectors.toList()));

    return userDataList;
  }

  private List<Integer> getUserIdsByString(String[] userIds) {
    List<Integer> users = new ArrayList<>();
    for (String userId : userIds) {
      users.add(Integer.parseInt(userId));
    }
    return users;
  }
}
