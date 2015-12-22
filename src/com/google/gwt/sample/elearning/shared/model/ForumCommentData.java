package com.google.gwt.sample.elearning.shared.model;

/**
 * Created by Ambrozie Paval on 12/22/2015.
 */
public class ForumCommentData {
  long id;
  long userId;
  String commentText;

  public ForumCommentData() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getCommentText() {
    return commentText;
  }

  public void setCommentText(String commentText) {
    this.commentText = commentText;
  }
}
