package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_tests;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * Created by razvanolar on 29.11.2015.
 */
public class CreateTestController {

  public interface ICreateTestView {

    Widget asWidget();
  }

  private ICreateTestView view;

  public CreateTestController(ICreateTestView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {

  }
}
