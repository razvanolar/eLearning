package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.ForumData;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ForumServiceAsync {
  void getUserAvailableForums(UserData user, AsyncCallback<List<ForumData>> async);

  void addCommentToForum(long userId, long lectureId, String comment, AsyncCallback<Void> async);
}
