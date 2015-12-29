package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.EmailService;
import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcUserDAO;
import com.google.gwt.sample.elearning.server.repository.JDBC.UserDAO;
import com.google.gwt.sample.elearning.server.service.email_service_util.EmailUtil;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 *
 * Created by Cristi on 12/29/2015.
 */
public class EmailServiceImpl extends RemoteServiceServlet implements EmailService {

  @Override
  public void sendForgetEmailPassword(String email) throws ELearningException {
    EmailUtil emailUtil = new EmailUtil();

    UserDAO userDAO = new JdbcUserDAO();
    UserData userData;
    try {
      userData = userDAO.getUserByEmail(email);
    } catch (Exception ex){
      throw new ELearningException("The given email was not found in the database.");
    }
    if(userData == null)
      throw new ELearningException("The given email was not found in the database.");

    String from = "systemelearning42";
    String password = "Test123##";
    String subject = "User and Password recovery mail.";
    String body = "Your account details are: \n\tUser: " + userData.getUsername() + "\n\tPassword: "+ userData.getPassword()+  "\n\n\nELearning Support";
    String[] to = {email};
    emailUtil.sendFromGMail(from, password, to, subject, body);
  }
}
