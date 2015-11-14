package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.client.services.LoginService;
import com.google.gwt.sample.elearning.server.JDBC.UserJDBCImpl;
import com.google.gwt.sample.elearning.shared.ELearningException;
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
  public String loginServer(String user, String pwd) throws ELearningException {
    UserJDBCImpl userJDBC = new UserJDBCImpl();
    userJDBC.getLoginData(user,pwd);
    storeUserInSession(user);
    return "sessionID" + sessionId++;
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
