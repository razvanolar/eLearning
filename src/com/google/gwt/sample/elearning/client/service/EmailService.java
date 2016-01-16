package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * Created by Cristi on 12/29/2015.
 */
@RemoteServiceRelativePath("EmailService")
public interface EmailService extends RemoteService {
  void sendForgetEmailPassword(String email) throws ELearningException;
  void sendCustomEmail(String to, String from, String fromPwd, String subject, String body) throws ELearningException;

}
