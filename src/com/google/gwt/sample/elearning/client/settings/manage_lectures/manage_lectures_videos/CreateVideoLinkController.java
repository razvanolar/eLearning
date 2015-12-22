package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_videos;

import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 01.12.2015.
 */
public class CreateVideoLinkController {

  public interface ICreateVideoLinkView extends MaskableView {

    void testLink(String link);
    TextButton getSaveButton();
    TextButton getTestLinkButton();
    TextField getLinkField();
    TextField getTitleField();
    TextArea getDescriptionTextArea();
    Widget asWidget();
  }
  private ICreateVideoLinkView view;
  private LectureServiceAsync lectureService;
  private final ManageLecturesVideosController parentController;
  private Window window;
  private VideoLinkData videoLinkData;

  private Logger log = Logger.getLogger(CreateVideoLinkController.class.getName());

  public CreateVideoLinkController(ICreateVideoLinkView view, LectureServiceAsync lectureService,
                                   ManageLecturesVideosController parentController) {
    this.view = view;
    this.lectureService = lectureService;
    this.parentController = parentController;
  }

  public CreateVideoLinkController(ICreateVideoLinkView view, LectureServiceAsync lectureService,
                                   ManageLecturesVideosController parentController, VideoLinkData videoLinkData) {
    this(view, lectureService, parentController);
    this.videoLinkData = videoLinkData;
    if (this.videoLinkData != null) {
      view.getTitleField().setText(videoLinkData.getTitle());
      view.getLinkField().setText(videoLinkData.getUrl());
      view.getDescriptionTextArea().setText(videoLinkData.getDescription());
      view.testLink(videoLinkData.getUrl());
    }
  }

  public void bind() {
    addListeners();
  }

  public void setContentWindow(Window window) {
    this.window = window;
  }

  private void addListeners() {
    view.getSaveButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onSaveSelection();
      }
    });

    view.getTestLinkButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onTestLinkSelection();
      }
    });
  }

  private void onSaveSelection() {
    if (view.getTitleField().getText() == null || view.getTitleField().getText().isEmpty()) {
      (new MessageBox("Error", "Please provide a title")).show();
      return;
    }
    if (view.getLinkField().getText() == null || view.getLinkField().getText().isEmpty()) {
      (new MessageBox("Error", "Link field is empty")).show();
      return;
    }
    if (parentController.getSelectedLecture() == null) {
      (new MessageBox("Error", "Please select a lecture")).show();
      return;
    }

    if (videoLinkData == null) {
      videoLinkData = new VideoLinkData(-1, view.getTitleField().getText(), view.getLinkField().getText(),
              view.getDescriptionTextArea().getText(), parentController.getSelectedLecture().getId());
      lectureService.saveVideoData(parentController.getSelectedLecture().getId(),
              videoLinkData, new ELearningAsyncCallBack<Void>(view, log) {
                public void onSuccess(Void result) {
                  if (window != null)
                    window.hide();
                }
              });
    } else {
      videoLinkData.setTitle(view.getTitleField().getText());
      videoLinkData.setUrl(view.getLinkField().getText());
      videoLinkData.setDescription(view.getDescriptionTextArea().getText());
      lectureService.updateVideoData(parentController.getSelectedLecture().getId(),
              videoLinkData, new ELearningAsyncCallBack<Void>(view, log) {
                public void onSuccess(Void result) {
                  if (window != null)
                    window.hide();
                }
              });
    }
  }

  private void onTestLinkSelection() {
    view.testLink(view.getLinkField().getText());
  }
}
