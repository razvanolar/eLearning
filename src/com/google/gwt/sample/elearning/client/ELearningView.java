package com.google.gwt.sample.elearning.client;

import com.google.gwt.sample.elearning.client.main_views.left_panel.LectureDetailsView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;

/**
 *
 * Created by razvanolar on 30.12.2015.
 */
public class ELearningView {

  private BorderLayoutContainer mainContainer;
  private LectureDetailsView lectureDetailsView;

  public ELearningView() {
    initGUI();
  }

  private void initGUI() {
    lectureDetailsView = new LectureDetailsView();
    mainContainer = new BorderLayoutContainer();
    ContentPanel leftPanel = new ContentPanel();

    leftPanel.setHeaderVisible(false);
    leftPanel.setAnimCollapse(true);
    leftPanel.add(lectureDetailsView.asWidget());

    BorderLayoutContainer.BorderLayoutData leftData = new BorderLayoutContainer.BorderLayoutData(250);
    leftData.setSplit(true);
    leftData.setCollapsible(true);
    leftData.setCollapseMini(true);
    leftData.setMinSize(150);
    leftData.setMaxSize(450);
    leftData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setWestWidget(leftPanel, leftData);
    mainContainer.setCenterWidget(new CenterLayoutContainer(), new MarginData(0, 0, 0, 10));
    mainContainer.setStyleName("mainELearningContainer");
  }

  public Widget asWidget() {
    return mainContainer;
  }
}
