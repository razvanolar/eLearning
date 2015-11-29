package com.google.gwt.sample.elearning.client.logs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by Cristi on 11/28/2015.
 */
public class LogGridHandler extends Handler implements IsWidget{
  private LogRecordProperties properties = GWT.create(LogRecordProperties.class);
  private Grid<LogRecord> grid;
  private ListStore<LogRecord> store;
  private static LogGridHandler instance;

  public static LogGridHandler getInstance(){
    if(instance == null)
      instance = new LogGridHandler();
    return instance;
  }

  private LogGridHandler(){
    createGrid();
  }

  private void createGrid() {
    store = new ListStore<LogRecord>(properties.key());
    List<ColumnConfig<LogRecord, ?>> columnConfigList = new ArrayList<ColumnConfig<LogRecord, ?>>();
    columnConfigList.add(new ColumnConfig<LogRecord, Date>(new LongToDateValueProvider<LogRecord>(properties.millis()), 70, "Timestamp"));
    columnConfigList.add(new ColumnConfig<LogRecord, Level>(properties.level(), 30, "Level"));
    ColumnConfig<LogRecord, String> messageColumn = new ColumnConfig<LogRecord, String>(properties.message(), 200, "Message");
    columnConfigList.add(messageColumn);
    columnConfigList.add(new ColumnConfig<LogRecord, String>(properties.loggerName(), 70, "Logger"));
    ColumnModel<LogRecord> columnModel = new ColumnModel<LogRecord>(columnConfigList);
    grid = new Grid<LogRecord>(store, columnModel);
    grid.getView().setForceFit(true);
    grid.getView().setAutoExpandColumn(messageColumn);
    Comparator<LogRecord> comparator = new Comparator<LogRecord>() {
      @Override
      public int compare(LogRecord o1, LogRecord o2) {
        return (int)(o1.getMillis() - o2.getMillis());
      }
    };
    store.addSortInfo(new Store.StoreSortInfo<LogRecord>(comparator, SortDir.DESC));
  }

  public Grid<LogRecord> getGrid() {
    return grid;
  }

  @Override
  public void publish(LogRecord record) {
    store.add(0,record);
  }

  @Override
  public void flush() {

  }

  @Override
  public void close() {

  }

  @Override
  public Widget asWidget() {
    return grid;
  }
}
