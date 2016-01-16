package com.google.gwt.sample.elearning.client.lecture_views.left_panel;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 *
 * Created by razvanolar on 30.12.2015.
 */
public class LectureVideosView {

  private BorderLayoutContainer mainContainer;

  public LectureVideosView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
  }

  public Widget asWidget() {
    return mainContainer;
  }

}
