package com.google.gwt.sample.elearning.client.main_views.right_panel.calendarView;

import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * Created by Cristi on 1/10/2016.
 */
public class CalendarController {
  public interface ICalendarView extends MaskableView {
    Widget asWidget();
    void mask();
    void unmask();
  }
  ICalendarView view;

  public CalendarController(ICalendarView view) {
    this.view = view;
  }
}
