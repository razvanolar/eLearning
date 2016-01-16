package com.google.gwt.sample.elearning.client.main_views.right_panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.main_views.right_panel.calendarView.CalendarView;
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
  private CalendarView calendarView;

  public LectureInfoView() {
    initGUI();
  }

  private void initGUI() {
    AccordionLayoutContainer.AccordionLayoutAppearance appearance =
        GWT.<AccordionLayoutContainer.AccordionLayoutAppearance> create(
            AccordionLayoutContainer.AccordionLayoutAppearance.class);
    mainContainer = new BorderLayoutContainer();
    lectureUsersView = new LectureUsersView();
    calendarView = new CalendarView();
    AccordionLayoutContainer accordionLayoutContainer = new AccordionLayoutContainer();
    ContentPanel lectureUsersPanel = new ContentPanel(appearance);
    lectureUsersPanel.setHeadingText("Users");
    lectureUsersPanel.add(lectureUsersView.asWidget());

    ContentPanel calendarPanel = new ContentPanel(appearance);
    calendarPanel.setHeadingText("Calendar");
    calendarPanel.add(calendarView.asWidget());

    accordionLayoutContainer.add(lectureUsersPanel);
    accordionLayoutContainer.add(calendarPanel);
    accordionLayoutContainer.setActiveWidget(lectureUsersPanel);
    mainContainer.setCenterWidget(accordionLayoutContainer);

  }

  public LectureUsersView getLectureUsersView() {
    return lectureUsersView;
  }

  public CalendarView getCalendarView() {
    return calendarView;
  }

  public Widget asWidget() {
    return mainContainer;
  }
}
