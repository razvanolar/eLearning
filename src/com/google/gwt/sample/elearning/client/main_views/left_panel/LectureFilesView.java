package com.google.gwt.sample.elearning.client.main_views.left_panel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 *
 * Created by razvanolar on 30.12.2015.
 */
public class LectureFilesView {

  private BorderLayoutContainer mainContainer;
  
  public LectureFilesView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
  }

  public Widget asWidget() {
    return mainContainer;
  }
}
