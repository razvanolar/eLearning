package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

public interface ProfessorServiceAsync {
  void getAllProfessors(AsyncCallback<List<Professor>> async);
}
