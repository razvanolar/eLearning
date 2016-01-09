package com.google.gwt.sample.elearning.client.main_views.right_panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.main_views.right_panel.usersView.LectureUsersView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 *
 * Created by Cristi on 1/3/2016.
 */
public class LectureInfoView {
  private BorderLayoutContainer mainContainer;
  private LectureUsersView lectureUsersView;

  public LectureInfoView() {
    initGUI();
  }

  private void initGUI() {
    AccordionLayoutContainer.AccordionLayoutAppearance appearance =
        GWT.<AccordionLayoutContainer.AccordionLayoutAppearance> create(
            AccordionLayoutContainer.AccordionLayoutAppearance.class);
    mainContainer = new BorderLayoutContainer();
    lectureUsersView = new LectureUsersView();
    AccordionLayoutContainer accordionLayoutContainer = new AccordionLayoutContainer();
    ContentPanel lectureUsersPanel = new ContentPanel(appearance);
    lectureUsersPanel.setHeadingText("Users");
    lectureUsersPanel.add(lectureUsersView.asWidget());

    accordionLayoutContainer.add(lectureUsersPanel);
    accordionLayoutContainer.setActiveWidget(lectureUsersPanel);
    mainContainer.setCenterWidget(accordionLayoutContainer);

  }

  public LectureUsersView getLectureUsersView() {
    return lectureUsersView;
  }

  public Widget asWidget() {
    return mainContainer;
  }
}
