package com.google.gwt.sample.elearning.client.settings.manage_logs;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.shared.model.LogData;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 17.01.2016.
 */
public class ManageLogsView implements ManageLogsController.IManageLogsView {

  private BorderLayoutContainer mainContainer;

  private TextButton refresButton;
  private ComboBox<String> logsCombo;
  private Grid<LogData> grid;

  public ManageLogsView() {
    initGUI();
  }

  private void initGUI() {
    ToolBar toolBar = new ToolBar();
    grid = createGrid();
    refresButton = new TextButton("", ELearningController.ICONS.refresh());
    ListStore<String> store = new ListStore<String>(new ModelKeyProvider<String>() {
      public String getKey(String item) {
        return item;
      }
    });
    logsCombo = new ComboBox<String>(store, new LabelProvider<String>() {
      public String getLabel(String item) {
        return item;
      }
    });
    mainContainer = new BorderLayoutContainer();

    logsCombo.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
    logsCombo.setEditable(false);

    toolBar.setHorizontalSpacing(3);
    toolBar.add(refresButton);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(new Label("Log files : "));
    toolBar.add(logsCombo);

    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setCenterWidget(grid);
  }

  private static int key;
  private Grid<LogData> createGrid() {
    ListStore<LogData> store = new ListStore<LogData>(new ModelKeyProvider<LogData>() {
      public String getKey(LogData item) {
        return item.getMessage() + key++;
      }
    });

    List<ColumnConfig<LogData, ?>> columns = new ArrayList<ColumnConfig<LogData, ?>>();
    columns.add(new ColumnConfig<LogData, String>(new ValueProvider<LogData, String>() {
      public String getValue(LogData object) {
        return object.getMessage();
      }

      public void setValue(LogData object, String value) {}

      public String getPath() {
        return null;
      }
    }, 100, "System Log Messages"));

    Grid<LogData> grid = new Grid<LogData>(store, new ColumnModel<LogData>(columns));
    grid.getView().setAutoFill(true);
    return grid;
  }

  public ComboBox<String> getLogsCombo() {
    return logsCombo;
  }

  public Grid<LogData> getGrid() {
    return grid;
  }

  public TextButton getRefresButton() {
    return refresButton;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
