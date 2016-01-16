package com.google.gwt.sample.elearning.client.settings.manage_logs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.service.LogService;
import com.google.gwt.sample.elearning.client.service.LogServiceAsync;
import com.google.gwt.sample.elearning.shared.model.LogData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 17.01.2016.
 */
public class ManageLogsController {

  private String currentFile;

  public interface IManageLogsView {
    TextButton getRefresButton();
    ComboBox<String> getLogsCombo();
    Grid<LogData> getGrid();
    Widget asWidget();
  }

  private IManageLogsView view;
  private LogServiceAsync logService = GWT.create(LogService.class);
  private ListStore<LogData> store;
  private Logger log = Logger.getLogger(ManageLogsController.class.getName());

  public ManageLogsController(IManageLogsView view) {
    this.view = view;
  }

  public void bind() {
    store = view.getGrid().getStore();
    addListeners();
    loadCombo();
  }

  private void addListeners() {
    view.getLogsCombo().addSelectionHandler(new SelectionHandler<String>() {
      public void onSelection(SelectionEvent<String> event) {
        currentFile = event.getSelectedItem();
        loadLogs();
      }
    });

    view.getRefresButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        loadCombo();
      }
    });
  }

  private void loadCombo() {
    logService.getAllLogFiles(new AsyncCallback<List<String>>() {
      public void onFailure(Throwable caught) {
        log.warning(caught.getMessage());
      }

      public void onSuccess(List<String> result) {
        view.getLogsCombo().getStore().clear();
        if (result != null && !result.isEmpty()) {
          for (String value : result)
            view.getLogsCombo().getStore().add(value);
          currentFile = view.getLogsCombo().getStore().get(0);
          view.getLogsCombo().setValue(currentFile);
        }
        loadLogs();
      }
    });
  }

  private void loadLogs() {
    if (currentFile == null || currentFile.isEmpty())
      return;

    logService.getLogDataFromFile(currentFile, new AsyncCallback<List<LogData>>() {
      public void onFailure(Throwable caught) {
        log.warning(caught.getMessage());
      }

      public void onSuccess(List<LogData> result) {
        store.clear();
        for (LogData logData : result)
          store.add(logData);
      }
    });
  }
}
