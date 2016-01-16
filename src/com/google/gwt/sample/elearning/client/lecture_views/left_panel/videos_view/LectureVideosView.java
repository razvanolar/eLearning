package com.google.gwt.sample.elearning.client.lecture_views.left_panel.videos_view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.models_properties.VideoLinkDataProperties;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 30.12.2015.
 */
public class LectureVideosView implements LectureVideosController.ILectureVideosView {

  private VideoLinkDataProperties videoLinkDataProperties = GWT.create(VideoLinkDataProperties.class);
  private BorderLayoutContainer mainContainer;

  private TextButton playVideoButton;
  private Grid<VideoLinkData> videosGrid;

  public LectureVideosView() {
    initGUI();
  }

  private void initGUI() {
    videosGrid = createGrid();
    mainContainer = new BorderLayoutContainer();
    playVideoButton = new TextButton("Play video", ELearningController.ICONS.play());
    ToolBar toolBar = new ToolBar();

    toolBar.add(playVideoButton);

    mainContainer.setSouthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setCenterWidget(videosGrid);
    setState(LectureVideosController.ILectureVideosViewState.NONE);
  }

  @Override
  public void setState(LectureVideosController.ILectureVideosViewState state) {
    switch (state) {
      case SELECTED:
        playVideoButton.setEnabled(true);
        break;
      case NONE:
        playVideoButton.setEnabled(false);
        break;
    }
  }

  private Grid<VideoLinkData> createGrid() {
    ListStore<VideoLinkData> store = new ListStore<VideoLinkData>(videoLinkDataProperties.key());

    List<ColumnConfig<VideoLinkData, ?>> columns = new ArrayList<ColumnConfig<VideoLinkData, ?>>();
    columns.add(new ColumnConfig<VideoLinkData, String>(videoLinkDataProperties.title(), 70, "Title"));
    columns.add(new ColumnConfig<VideoLinkData, String>(videoLinkDataProperties.url(), 150, "URL"));

    ColumnModel<VideoLinkData> columnModel = new ColumnModel<VideoLinkData>(columns);
    Grid<VideoLinkData> grid = new Grid<VideoLinkData>(store, columnModel);
    grid.getView().setAutoFill(true);

    return grid;
  }

  public TextButton getPlayVideoButton() {
    return playVideoButton;
  }

  public Grid<VideoLinkData> getVideosGrid() {
    return videosGrid;
  }

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
