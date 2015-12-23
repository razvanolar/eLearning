package com.google.gwt.sample.elearning.server.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * Created by razvanolar on 23.12.2015.
 */
public class UsersDownloadService extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String usersParameter = req.getParameter("users");
    String []usersIDs = usersParameter.split("\\$");
    System.out.println(Arrays.toString(usersIDs));
  }
}
