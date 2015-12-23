package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.shared.model.*;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by razvanolar on 27.11.2015.
 */
public class ManageLecturesViewUtils {
  private static LectureDataProperties lectureProperties = GWT.create(LectureDataProperties.class);
  private static FileDataProperties fileDataProperties = GWT.create(FileDataProperties.class);
  private static LWLectureTestDataProperties testDataProperties = GWT.create(LWLectureTestDataProperties.class);
  private static VideoLinkDataProperties videoLinkDataProperties = GWT.create(VideoLinkDataProperties.class);
  private static HomeworkDataProperties homeworkDataProperties = GWT.create(HomeworkDataProperties.class);

  public Grid<Lecture> createLecturesGrid() {
    IdentityValueProvider<Lecture> identityValueProvider = new IdentityValueProvider<Lecture>("sm");
    CheckBoxSelectionModel<Lecture> selectionModel = new CheckBoxSelectionModel<Lecture>(identityValueProvider);
    selectionModel.setSelectionMode(Style.SelectionMode.SINGLE);

    List<ColumnConfig<Lecture, ?>> columnConfigList = new ArrayList<ColumnConfig<Lecture, ?>>();
    columnConfigList.add(selectionModel.getColumn());
    columnConfigList.add(new ColumnConfig<Lecture, Long>(lectureProperties.id(), 20, "ID"));
    columnConfigList.add(new ColumnConfig<Lecture, String>(lectureProperties.lectureName(), 100, "Lecture Name"));
    ColumnConfig<Lecture, String> profColumn = new ColumnConfig<Lecture, String>(new ValueProvider<Lecture, String>() {
      public String getValue(Lecture object) {
        return (object != null && object.getProfessor() != null) ? object.getProfessor().toString() : "";
      }
      public void setValue(Lecture object, String value) {}
      public String getPath() { return "professor_path"; }
    }, 100, "Professor");
    columnConfigList.add(profColumn);
    ColumnModel<Lecture> columnModel = new ColumnModel<Lecture>(columnConfigList);
    ListStore<Lecture> store = new ListStore<Lecture>(lectureProperties.key());

    GroupingView<Lecture> groupingView = new GroupingView<Lecture>();
    groupingView.setShowGroupedColumn(false);
    groupingView.setForceFit(true);
    groupingView.groupBy(profColumn);

    Grid<Lecture> lectureDataGrid = new Grid<Lecture>(store, columnModel, groupingView);

    lectureDataGrid.setHideHeaders(false);
    lectureDataGrid.getView().setAutoFill(true);
    lectureDataGrid.setSelectionModel(selectionModel);
    return lectureDataGrid;
  }

  public TreeGrid<FileData> createFileTreeGrid() {
    TreeStore<FileData> store = new TreeStore<FileData>(fileDataProperties.path());

    List<ColumnConfig<FileData, ?>> columnConfigList = new ArrayList<ColumnConfig<FileData, ?>>();
    ColumnConfig<FileData, String> primaryColumn = new ColumnConfig<FileData, String>(fileDataProperties.name(), 100, "Name");
    columnConfigList.add(primaryColumn);
    columnConfigList.add(new ColumnConfig<FileData, String>(fileDataProperties.lastModified(), 100, "Last Modified"));
    columnConfigList.add(new ColumnConfig<FileData, Long>(fileDataProperties.length(), 30, "Size"));

    ColumnModel<FileData> columnModel = new ColumnModel<FileData>(columnConfigList);

    TreeGrid<FileData> treeGrid = new TreeGrid<FileData>(store, columnModel, primaryColumn);
    treeGrid.setAutoExpand(true);
    treeGrid.getTreeView().setAutoFill(true);
    return treeGrid;
  }

  public Grid<LWLectureTestData> createTestsGrid() {
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

  public Grid<VideoLinkData> createVideosGrid() {
    IdentityValueProvider<VideoLinkData> identityValueProvider = new IdentityValueProvider<VideoLinkData>("sm");
    CheckBoxSelectionModel<VideoLinkData> checkBoxSelectionModel = new CheckBoxSelectionModel<VideoLinkData>(identityValueProvider);
    checkBoxSelectionModel.setSelectionMode(Style.SelectionMode.SINGLE);

    ListStore<VideoLinkData> store = new ListStore<VideoLinkData>(videoLinkDataProperties.key());

    List<ColumnConfig<VideoLinkData, ?>> columns = new ArrayList<ColumnConfig<VideoLinkData, ?>>();
    columns.add(checkBoxSelectionModel.getColumn());
    columns.add(new ColumnConfig<VideoLinkData, Long>(videoLinkDataProperties.id(), 50, "ID"));
    columns.add(new ColumnConfig<VideoLinkData, String>(videoLinkDataProperties.title(), 70, "Title"));
    columns.add(new ColumnConfig<VideoLinkData, String>(videoLinkDataProperties.url(), 150, "URL"));
    columns.add(new ColumnConfig<VideoLinkData, String>(videoLinkDataProperties.description(), 150, "Description"));

    ColumnModel<VideoLinkData> columnModel = new ColumnModel<VideoLinkData>(columns);
    Grid<VideoLinkData> grid = new Grid<VideoLinkData>(store, columnModel);
    grid.getView().setAutoFill(true);
    grid.setSelectionModel(checkBoxSelectionModel);

    return grid;
  }

  public Grid<HomeworkData> createHomeworkGrid() {
    IdentityValueProvider<HomeworkData> identityValueProvider = new IdentityValueProvider<HomeworkData>("sm");
    CheckBoxSelectionModel<HomeworkData> checkBoxSelectionModel = new CheckBoxSelectionModel<HomeworkData>(identityValueProvider);
    checkBoxSelectionModel.setSelectionMode(Style.SelectionMode.SINGLE);

    ListStore<HomeworkData> store = new ListStore<HomeworkData>(homeworkDataProperties.key());

    List<ColumnConfig<HomeworkData, ?>> columns = new ArrayList<ColumnConfig<HomeworkData, ?>>();
    columns.add(checkBoxSelectionModel.getColumn());
    columns.add(new ColumnConfig<HomeworkData, Long>(homeworkDataProperties.id(), 50, "ID"));
    columns.add(new ColumnConfig<HomeworkData, String>(homeworkDataProperties.title(), 200, "Title"));
    columns.add(new ColumnConfig<HomeworkData, Integer>(homeworkDataProperties.score(), 100, "Score"));

    ColumnModel<HomeworkData> columnModel = new ColumnModel<HomeworkData>(columns);
    Grid<HomeworkData> grid = new Grid<HomeworkData>(store,columnModel);
    grid.getView().setAutoFill(true);
    grid.setSelectionModel(checkBoxSelectionModel);

    return grid;
  }
}
