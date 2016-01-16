package com.google.gwt.sample.elearning.client.lecture_views.left_panel.tests_view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.models_properties.LWLectureTestDataProperties;
import com.google.gwt.sample.elearning.shared.model.LWLectureTestData;
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

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class LectureTestsView implements LectureTestsController.ILectureTestView {

  private LWLectureTestDataProperties testDataProperties = GWT.create(LWLectureTestDataProperties.class);
  private BorderLayoutContainer mainContainer;

  private TextButton resolveTestButton;
  private Grid<LWLectureTestData> testsGrid;

  public LectureTestsView() {
    initGUI();
  }

  private void initGUI() {
    resolveTestButton = new TextButton("Resolve test", ELearningController.ICONS.solve());
    testsGrid = createGrid();
    mainContainer = new BorderLayoutContainer();
    ToolBar toolBar = new ToolBar();

    toolBar.add(resolveTestButton);

    mainContainer.setSouthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setCenterWidget(testsGrid);
  }

  @Override
  public void setState(LectureTestsController.ILectureTestsViewState state) {

  }

  private Grid<LWLectureTestData> createGrid() {
    ListStore<LWLectureTestData> store = new ListStore<LWLectureTestData>(testDataProperties.key());

    List<ColumnConfig<LWLectureTestData, ?>> columns = new ArrayList<ColumnConfig<LWLectureTestData, ?>>();
    columns.add(new ColumnConfig<LWLectureTestData, String>(testDataProperties.name(), 150, "Name"));
    columns.add(new ColumnConfig<LWLectureTestData, Integer>(testDataProperties.questionsNo(), 100, "Questions No."));
    columns.add(new ColumnConfig<LWLectureTestData, Integer>(testDataProperties.totalScore(), 100, "Total score"));

    ColumnModel<LWLectureTestData> model = new ColumnModel<LWLectureTestData>(columns);

    Grid<LWLectureTestData> grid = new Grid<LWLectureTestData>(store, model);
    grid.getView().setAutoFill(true);

    return grid;
  }

  public TextButton getResolveTestButton() {
    return resolveTestButton;
  }

  public Grid<LWLectureTestData> getTestsGrid() {
    return testsGrid;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
