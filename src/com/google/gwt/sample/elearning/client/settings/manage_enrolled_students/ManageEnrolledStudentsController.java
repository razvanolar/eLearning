package com.google.gwt.sample.elearning.client.settings.manage_enrolled_students;

import com.google.gwt.sample.elearning.client.settings.ISettingsController;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ManageEnrolledStudentsController implements ISettingsController {

  public interface IManageEnrolledStudentsView {

    Widget asWidget();
  }

  private IManageEnrolledStudentsView view;

  public ManageEnrolledStudentsController(IManageEnrolledStudentsView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {

  }

  @Override
  public void loadResources() {

  }

  @Override
  public String getControllerName() {
    return "ManageEnrolledStudentsController";
  }
}
