package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_videos;

import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.VideoFramePanel;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.ManageLecturesController;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 30.11.2015.
 */
public class ManageLecturesVideosController {

  public enum ManageVideosToolBarState {
    ADD, EDIT, NONE;
  }

  private ManageLecturesController.IManageLecturesView view;
  private LectureServiceAsync lectureService;
  private ListStore<VideoLinkData> store;
  private Lecture currentLecture;
  private Logger log = Logger.getLogger(ManageLecturesVideosController.class.getName());

  public ManageLecturesVideosController(ManageLecturesController.IManageLecturesView view, LectureServiceAsync lectureService) {
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
        onVideosGridSelection(event);
      }
    });

    view.getPlayVideoButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onPlayVideoSelection();
      }
    });

    view.getAddVideoLinkButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onAddVideoLinkSelection();
      }
    });

    view.getEditVideoLinkButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onEditVideoLinkSelection();
      }
    });

    view.getDeleteVideoLinkButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onDeleteVideoLinkSelection();
      }
    });
  }

  private void onVideosGridSelection(SelectionChangedEvent<VideoLinkData> event) {
    List<VideoLinkData> selectedItems = event.getSelection();
    if (selectedItems == null || selectedItems.isEmpty()) {
      if (currentLecture == null)
        view.setVideoGridState(ManageVideosToolBarState.NONE);
      else
        view.setVideoGridState(ManageVideosToolBarState.ADD);
    } else {
      view.setVideoGridState(ManageVideosToolBarState.EDIT);
    }
  }

  private void onPlayVideoSelection() {
    VideoLinkData selectedLink = getSelectedVideoLink();
    if (selectedLink == null)
      return;
    VideoFramePanel frame = new VideoFramePanel(selectedLink);
    MasterWindow window = new MasterWindow();
    window.setContent(frame.asWidget(), selectedLink.getTitle());
    window.setPixelSize(600, 400);
    window.setModal(true);
    window.setCollapsible(true);
    window.show();
  }

  private void onAddVideoLinkSelection() {
    CreateVideoLinkController.ICreateVideoLinkView view = new CreateVideoLinkView();
    CreateVideoLinkController controller = new CreateVideoLinkController(view, lectureService, this);
    controller.bind();
    MasterWindow window = new MasterWindow();
    window.setContent(view.asWidget(), "Add link");
    window.setPixelSize(350, 500);
    window.show();
    controller.setContentWindow(window);
  }

  private void onEditVideoLinkSelection() {
    CreateVideoLinkController.ICreateVideoLinkView view = new CreateVideoLinkView();
    CreateVideoLinkController controller = new CreateVideoLinkController(view, lectureService, this, getSelectedVideoLink());
    controller.bind();
    MasterWindow window = new MasterWindow();
    window.setContent(view.asWidget(), "Add link");
    window.setPixelSize(350, 500);
    window.show();
    controller.setContentWindow(window);
  }

  private void onDeleteVideoLinkSelection() {
    if (getSelectedLecture() == null) {
      (new MessageBox("Error", "Please select a lecture")).show();
      return;
    }

    VideoLinkData videoLinkData = getSelectedVideoLink();
    if (videoLinkData != null) {
      lectureService.deleteVideoData(getSelectedLecture().getId(), videoLinkData, new ELearningAsyncCallBack<Void>(view, log) {
        public void onSuccess(Void result) {
          (new MessageBox("Info", "Link was deleted!")).show();
        }
      });
    } else {
      (new MessageBox("Error", "Please select a link")).show();
    }
  }

  private VideoLinkData getSelectedVideoLink() {
    if (view.getVideosGrid().getSelectionModel() != null) {
      List<VideoLinkData> selectedItems = view.getVideosGrid().getSelectionModel().getSelectedItems();
      if (selectedItems != null && !selectedItems.isEmpty())
        return selectedItems.get(0);
    }
    return null;
  }

  public void loadVideos() {
    if (currentLecture == null)
      return;
    view.mask();
    lectureService.getLectureVideos(currentLecture.getId(), new ELearningAsyncCallBack<List<VideoLinkData>>(view, log) {
      @Override
      public void onSuccess(List<VideoLinkData> result) {
        if (result != null) {
          store.clear();
          store.addAll(result);
        }
        view.unmask();
      }
    });
  }

  public void setSelectedLecture(Lecture lecture) {
    currentLecture = lecture;
    if (currentLecture != null)
      view.setVideoGridState(ManageVideosToolBarState.ADD);
    else
      view.setVideoGridState(ManageVideosToolBarState.NONE);
  }

  public Lecture getSelectedLecture() {
    return currentLecture;
  }
}
