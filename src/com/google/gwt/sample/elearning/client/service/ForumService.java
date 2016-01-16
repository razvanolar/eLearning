package com.google.gwt.sample.elearning.client.service;

import com.google.gwt.sample.elearning.shared.model.ForumData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 * Created by Ambrozie Paval on 1/16/2016.
 */
@RemoteServiceRelativePath("ForumService")
public interface ForumService extends RemoteService{
  List<ForumData> getUserAvailableForums(long userId);
  void addCommentToForum(long userId, long lectureId, String comment) throws Exception;
}
