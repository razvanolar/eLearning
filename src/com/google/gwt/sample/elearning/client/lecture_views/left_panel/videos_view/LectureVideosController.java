package com.google.gwt.sample.elearning.client.lecture_views.left_panel.videos_view;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.eLearningUtils.VideoFramePanel;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class LectureVideosController {

  public enum ILectureVideosViewState {
    SELECTED, NONE
  }

  public interface ILectureVideosView extends MaskableView {
    TextButton getPlayVideoButton();
    Grid<VideoLinkData> getVideosGrid();
    void setState(ILectureVideosViewState state);
    Widget asWidget();
  }

  private ILectureVideosView view;
  private LectureServiceAsync lectureService;
  private ListStore<VideoLinkData> store;
  private Logger log = Logger.getLogger(LectureVideosController.class.getName());

  public LectureVideosController(ILectureVideosView view, LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void bind() {
    store = view.getVideosGrid().getStore();
    addListeners();
  }

  private void addListeners() {
    view.getVideosGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<VideoLinkData>() {
      public void onSelectionChanged(SelectionChangedEvent<VideoLinkData> event) {
        setViewState();
      }
    });

    view.getPlayVideoButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onPlayVideoSelection();
      }
    });
  }

  private void onPlayVideoSelection() {
    VideoLinkData selectedLink = getSelectedVideo();
    if (selectedLink == null)
      return;
    VideoFramePanel frame = new VideoFramePanel(selectedLink);
    MasterWindow window = new MasterWindow();
    window.setContent(frame.asWidget(), selectedLink.getTitle());
    window.setPixelSize(600, 400);
    window.setModal(false);
    window.setCollapsible(true);
    window.show();
  }

  public void loadVideos() {
    lectureService.getLectureVideos(ELearningController.getInstance().getCurrentLecture().getId(), new AsyncCallback<List<VideoLinkData>>() {
      public void onFailure(Throwable caught) {
        log.warning(caught.getMessage());
      }

      public void onSuccess(List<VideoLinkData> result) {
        store.clear();
        if (result != null && !result.isEmpty())
          for (VideoLinkData videoLinkData : result)
            store.add(videoLinkData);
      }
    });
  }

  private void setViewState() {
    if (getSelectedVideo() != null)
      view.setState(ILectureVideosViewState.SELECTED);
    else
      view.setState(ILectureVideosViewState.NONE);
  }

  private VideoLinkData getSelectedVideo() {
    List<VideoLinkData> selectedItems = view.getVideosGrid().getSelectionModel().getSelectedItems();
    if (selectedItems != null && !selectedItems.isEmpty())
      return selectedItems.get(0);
    return null;
  }
}
