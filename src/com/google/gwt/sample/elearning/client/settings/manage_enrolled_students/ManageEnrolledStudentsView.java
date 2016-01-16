package com.google.gwt.sample.elearning.client.settings.manage_enrolled_students;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ManageEnrolledStudentsView implements ManageEnrolledStudentsController.IManageEnrolledStudentsView {

  private BorderLayoutContainer mainContainer;

  public ManageEnrolledStudentsView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
