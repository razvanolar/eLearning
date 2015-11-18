package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.LoginService;
import com.google.gwt.sample.elearning.server.JDBC.UserJDBCImpl;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.exception.IncorrectLoginException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/***
 * Created by razvanolar on 19.10.2015.
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

  private static final long serialVersionUID = 4456105400553118785L;
  private static long sessionId = 0;
  private static final String USER = "user";

  @Override
  public UserData loginServer(String user, String pwd) throws ELearningException, IncorrectLoginException {
    UserJDBCImpl userJDBC = new UserJDBCImpl();
    UserData userData = userJDBC.getUserData(user, pwd);
    userData.setSessionId("sessionID" + sessionId++);
    storeUserInSession(user);
    return userData;
  }

  @Override
  public String loginFromSessionServer() {
    return getUserAlreadyFromSession();
  }

  @Override
  public void logout() {
    deleteUserFromSession();
  }

  private void storeUserInSession(String user) {
    HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
    HttpSession httpSession = httpServletRequest.getSession(true);
    httpSession.setAttribute(USER, user);
  }

  private String getUserAlreadyFromSession() {
    String user = null;
    HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
    HttpSession httpSession = httpServletRequest.getSession();
    Object object = httpSession.getAttribute(USER);
    if(object != null && object instanceof String)
      user = (String) object;
    return user;
  }

  private void deleteUserFromSession() {
    HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
    HttpSession session = httpServletRequest.getSession();
    session.removeAttribute(USER);
  }
}
