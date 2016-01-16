package com.google.gwt.sample.elearning.client.lecture_views.left_panel.homeworks_view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.models_properties.HomeworkDataProperties;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cristi on 1/16/2016.
 */
public class LectureHomeworksView implements LectureHomeworksController.ILectureHomeworksView{
  private BorderLayoutContainer mainContainer;
  private TextButton resolveHomeworkButton;
  private Grid<HomeworkData> grid;
  private HomeworkDataProperties homeworkDataProperties = GWT.create(HomeworkDataProperties.class);

  public LectureHomeworksView(){
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    resolveHomeworkButton = new TextButton("Resolve Homework", ELearningController.ICONS.solve());
    grid = createGrid();
    ToolBar toolBar = new ToolBar();

    toolBar.add(resolveHomeworkButton);

    mainContainer.setSouthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setCenterWidget(grid);
    setState(LectureHomeworksController.ILectureHomeworksViewState.NONE);
  }

  private Grid<HomeworkData> createGrid() {
    ListStore<HomeworkData> store = new ListStore<HomeworkData>(homeworkDataProperties.key());

    List<ColumnConfig<HomeworkData, ?>> columns = new ArrayList<ColumnConfig<HomeworkData, ?>>();
    columns.add(new ColumnConfig<HomeworkData, String>(homeworkDataProperties.title(), 200, "Name"));
    columns.add(new ColumnConfig<HomeworkData, Integer>(homeworkDataProperties.score(), 100, "Score"));

    ColumnModel<HomeworkData> model = new ColumnModel<HomeworkData>(columns);

    Grid<HomeworkData> gridH = new Grid<HomeworkData>(store, model);
    gridH.getView().setAutoFill(true);

    return gridH;

  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public TextButton getResolveButton() {
    return resolveHomeworkButton;
  }

  @Override
  public void setState(LectureHomeworksController.ILectureHomeworksViewState state) {
    switch (state) {
    case SELECTED:
      resolveHomeworkButton.setEnabled(true);
      break;
    case NONE:
      resolveHomeworkButton.setEnabled(false);
      break;
    }
  }

  @Override
  public Grid<HomeworkData> getGrid() {
    return grid;
  }
}
