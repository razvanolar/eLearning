package com.google.gwt.sample.elearning.client.main_views.left_panel.filesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.models_properties.FileDataProperties;
import com.google.gwt.sample.elearning.shared.model.FileData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 30.12.2015.
 */
public class LectureFilesView implements LectureFilesController.ILectureFilesView {

  private FileDataProperties fileDataProperties = GWT.create(FileDataProperties.class);

  private BorderLayoutContainer mainContainer;
  private TreeGrid<FileData> grid;
  private TextButton downloadButton;

  public LectureFilesView() {
    initGUI();
  }

  private void initGUI() {
    grid = createFilesGrid();
    downloadButton = new TextButton("Download file", ELearningController.ICONS.download());
    ToolBar toolBar = new ToolBar();
    mainContainer = new BorderLayoutContainer();

    toolBar.add(downloadButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 0, 5)));

    mainContainer.setSouthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setCenterWidget(grid);
    setState(LectureFilesController.ILectureFilesViewState.NONE);
  }

  @Override
  public void setState(LectureFilesController.ILectureFilesViewState state) {
    switch (state) {
      case SELECTED:
        downloadButton.setEnabled(true);
        break;
      case NONE:
        downloadButton.setEnabled(false);
        break;
    }
  }

  private TreeGrid<FileData> createFilesGrid() {
    TreeStore<FileData> store = new TreeStore<FileData>(fileDataProperties.path());

    List<ColumnConfig<FileData, ?>> columns = new ArrayList<ColumnConfig<FileData, ?>>();
    ColumnConfig<FileData, String> nameColumn = new ColumnConfig<FileData, String>(fileDataProperties.name(), 200, "Name");
    columns.add(nameColumn);

    TreeGrid<FileData> treeGrid = new TreeGrid<FileData>(store, new ColumnModel<FileData>(columns), nameColumn);
    treeGrid.getTreeView().setAutoFill(true);
    return treeGrid;
  }

  @Override
  public void mask() {
    mainContainer.mask();
  }

  @Override
  public void unmask() {
    mainContainer.unmask();
  }

  public TextButton getDownloadButton() {
    return downloadButton;
  }

  public TreeGrid<FileData> getGrid() {
    return grid;
  }

  public Widget asWidget() {
    return mainContainer;
  }
}
