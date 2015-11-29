package com.google.gwt.sample.elearning.client.logs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by Cristi on 11/28/2015.
 */
public class LogsView implements LogsController.ILogsView{
  private BorderLayoutContainer mainContainer;
  private Grid<LogRecord> grid;

  public LogsView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    LogGridHandler logGridHandler = LogGridHandler.getInstance();
    grid = logGridHandler.getGrid();
    mainContainer.setCenterWidget(grid);
  }


  @Override
  public Grid<LogRecord> getGrid() {
    return grid;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
