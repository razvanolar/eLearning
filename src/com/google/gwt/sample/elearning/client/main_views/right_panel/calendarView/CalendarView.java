package com.google.gwt.sample.elearning.client.main_views.right_panel.calendarView;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;

/**
 *
 * Created by Cristi on 1/10/2016.
 */
public class CalendarView implements CalendarController.ICalendarView{
  private BorderLayoutContainer mainContainer;
  private DatePicker datePicker;
  public CalendarView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();

    datePicker = new DatePicker();
    mainContainer.setCenterWidget(datePicker, new MarginData(0, 0, 0, 0));
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public void mask() {
    mainContainer.mask();
  }

  @Override
  public void unmask() {
    mainContainer.unmask();
  }
}
