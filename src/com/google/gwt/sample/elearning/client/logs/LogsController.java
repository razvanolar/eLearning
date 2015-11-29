package com.google.gwt.sample.elearning.client.logs;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by Cristi on 11/28/2015.
 */
public class LogsController {
  public interface ILogsView{
    Grid<LogRecord> getGrid();
    Widget asWidget();
  }
  ILogsView view;
  Logger logger = Logger.getLogger(LogsController.class.getName());
  public LogsController(ILogsView view) {
    this.view = view;
  }

  private void loadLogs(){

  }
}
