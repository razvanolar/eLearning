package com.google.gwt.sample.elearning.client.main_views;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.profilebar.ProfileBarController;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 29.12.2015.
 */
public class UserLecturesController {

  public enum UsersLecturesViewState {
    SELECTED, NONE
  }

  public interface IUserLecturesView extends MaskableView {
    Grid<Lecture> getUnenrolledGrid();
    TextField getEnrollmentKeyField();
    TextButton getSendKeyButton();
    void setState(UsersLecturesViewState state);
    Widget asWidget();
  }

  private Logger log = Logger.getLogger(UserLecturesController.class.getName());
  private LectureServiceAsync lectureService;
  private ProfileBarController parentController;
  private IUserLecturesView view;
  private List<Lecture> lectures;

  public UserLecturesController(IUserLecturesView view, LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void setParentController(ProfileBarController parentController) {
    this.parentController = parentController;
  }

  public void bind() {
    loadGrid();
    addListeners();
  }

  private void addListeners() {
    view.getUnenrolledGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<Lecture>() {
      public void onSelectionChanged(SelectionChangedEvent<Lecture> event) {
        onGridSelectionChange();
      }
    });

    view.getSendKeyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onSendKeySelection();
      }
    });
  }

  private void onSendKeySelection() {
    Lecture selectedLecture = getSelectedLecture();
    if (selectedLecture == null)
      return;
    lectureService.registerUserToLecture(ELearningController.getInstance().getCurrentUser().getId(),
            selectedLecture.getId(), view.getEnrollmentKeyField().getText(), new AsyncCallback<Void>() {
              public void onFailure(Throwable caught) {
                (new MessageBox("Error", "Failed to register")).show();
              }

              public void onSuccess(Void result) {
                if (parentController != null) {
                  parentController.hideLectureMenu();
                }
              }
            });
  }

  private void onGridSelectionChange() {
    List<Lecture> selectedItems = view.getUnenrolledGrid().getSelectionModel().getSelectedItems();
    if (selectedItems != null && !selectedItems.isEmpty()) {
      view.setState(UsersLecturesViewState.SELECTED);
    } else {
      view.setState(UsersLecturesViewState.NONE);
    }
  }

  public void setLecturesList(List<Lecture> lectures) {
    this.lectures = lectures;
    loadGrid();
  }

  public void maskView() {
    view.mask();
  }

  public void unmaskView() {
    view.unmask();
  }

  private Lecture getSelectedLecture() {
    List<Lecture> selectedItems = view.getUnenrolledGrid().getSelectionModel().getSelectedItems();
    if (selectedItems == null || selectedItems.isEmpty())
      return null;
    return selectedItems.get(0);
  }

  private void loadGrid() {
    if (lectures != null) {
      log.info(lectures.toString());
      view.getUnenrolledGrid().getStore().clear();
      view.getUnenrolledGrid().getStore().addAll(lectures);
    }
  }
}
