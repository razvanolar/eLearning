package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_homework;

import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.ManageLecturesController;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by Cristi on 12/23/2015.
 */
public class ManageLecturesHomeworkController {

  private Lecture currentLecture;
  private Logger log = Logger.getLogger(ManageLecturesHomeworkController.class.getName());

  public enum ManageHomeworkToolBarState {
    ADD, EDIT, NONE

  }

  private ManageLecturesController.IManageLecturesView view;
  private LectureServiceAsync lectureService;
  private ListStore<HomeworkData> store;
  public ManageLecturesHomeworkController(ManageLecturesController.IManageLecturesView view,
      LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void bind() {
    store = view.getHomeworkDataGrid().getStore();
    addListeners();
  }

  private void addListeners() {
    view.getHomeworkDataGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<HomeworkData>() {
      @Override
      public void onSelectionChanged(SelectionChangedEvent<HomeworkData> event) {
        onHomeworkGridSelection(event);
      }
    });

    view.getCreateHomeworkButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnCreateHomeworkSelection();
      }
    });

    view.getEditButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnEditHomeworkSelection();
      }
    });

    view.getDeleteButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnDeleteHomeworkSelection();
      }
    });
  }

  private void doOnDeleteHomeworkSelection() {
    if(currentLecture == null){
      new MessageBox("Error", "Please select a lecture").show();
      return;
    }

    HomeworkData homeworkData = view.getHomeworkDataGrid().getSelectionModel().getSelectedItem();
    if(homeworkData == null){
      new MessageBox("Error", "Please select a Homework").show();
      return;
    }
    lectureService.deleteHomeworkData(currentLecture.getId(), homeworkData, new ELearningAsyncCallBack<Void>(view, log) {
      @Override
      public void onSuccess(Void result) {
        new MessageBox("Info", "Homework was deleted!").show();
      }
    });
  }

  private void doOnEditHomeworkSelection() {
    CreateHomeworkController.ICreateHomeworkView createView = new CreateHomeworkView();
    CreateHomeworkController controller = new CreateHomeworkController(createView, lectureService, this, view.getHomeworkDataGrid().getSelectionModel().getSelectedItem());
    controller.bind();
    MasterWindow window = new MasterWindow();
    window.setContent(createView.asWidget(), "Create Test View");
    window.setModal(true);
    window.setMinHeight(335);
    window.setMinWidth(335);
    window.setPixelSize(335, 335);
    window.show();
    controller.setContentWindow(window);
  }

  private void doOnCreateHomeworkSelection() {
    CreateHomeworkController.ICreateHomeworkView view = new CreateHomeworkView();
    CreateHomeworkController controller = new CreateHomeworkController(view, lectureService, this);
    controller.bind();
    MasterWindow window = new MasterWindow();
    window.setContent(view.asWidget(), "Create Test View");
    window.setModal(true);
    window.setMinHeight(335);
    window.setMinWidth(335);
    window.setPixelSize(335, 335);
    window.show();
    controller.setContentWindow(window);
  }

  private void onHomeworkGridSelection(SelectionChangedEvent<HomeworkData> event) {
    List<HomeworkData> selectedItems = event.getSelection();
    if (selectedItems == null || selectedItems.isEmpty()) {
      if (currentLecture == null)
        view.setHomeworkGridState(ManageHomeworkToolBarState.NONE);
      else
        view.setHomeworkGridState(ManageHomeworkToolBarState.ADD);
    } else {
      view.setHomeworkGridState(ManageHomeworkToolBarState.EDIT);
    }
  }

  public void loadHomeworks() {
    if (currentLecture == null)
      return;
    view.mask();
    lectureService.getAllHomeworks(currentLecture.getProfessor(), currentLecture.getId(),
        new ELearningAsyncCallBack<List<HomeworkData>>(view, log) {
          @Override
          public void onSuccess(List<HomeworkData> result) {
            if (result != null) {
              store.clear();
              store.addAll(result);
            }
            view.unmask();
          }
        });
  }

  public void setSelectedLecture(Lecture selectedLecture) {
    currentLecture = selectedLecture;
    if (currentLecture != null)
      view.setHomeworkGridState(ManageHomeworkToolBarState.ADD);
    else
      view.setHomeworkGridState(ManageHomeworkToolBarState.NONE);
  }

  public Lecture getSelectedLecture(){
    return currentLecture;
  }
}
