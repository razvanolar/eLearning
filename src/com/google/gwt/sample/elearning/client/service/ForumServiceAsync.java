package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.ForumData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

public interface ForumServiceAsync {
  void getUserAvailableForums(long userId, AsyncCallback<List<ForumData>> async);

  void addCommentToForum(long userId, long lectureId, String comment, AsyncCallback<Void> async);
}
