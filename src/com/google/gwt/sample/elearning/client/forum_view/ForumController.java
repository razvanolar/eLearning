package com.google.gwt.sample.elearning.client.forum_view;

import com.google.gwt.sample.elearning.client.login.ForgotPasswordController;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ForumController {

  public interface IForumView {

    Widget asWidget();
  }

  private IForumView view;

  public ForumController(IForumView view) {
    this.view = view;
  }

  public void bind() {
    addListers();
    loadResources();
  }

  private void addListers() {

  }

  public void loadResources() {

  }
}
