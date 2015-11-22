package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 * Created by Cristi on 11/22/2015.
 */
@RemoteServiceRelativePath("ProfessorService")
public interface ProfessorService extends RemoteService {
  List<Professor> getAllProfessors() throws ELearningException;
}
