package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.ProfessorService;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.List;

/**
 * Created by Cristi on 11/22/2015.
 */
public class ProfessorServiceImpl extends RemoteServiceServlet implements ProfessorService {
  @Override
  public List<Professor> getAllProfessors() throws ELearningException {
    throw new ELearningException("Not Implemented");
  }
}
