package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_tests;

import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.ManageLecturesController;
import com.google.gwt.sample.elearning.shared.model.LWLectureTestData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 29.11.2015.
 */
public class ManageLecturesTestsController implements ICreateTestListener {

  public enum LectureTestsViewState {
    ADD, TEST_SELECTED, NONE
  }

  private ManageLecturesController.IManageLecturesView view;
  private LectureServiceAsync lectureService;
  private ListStore<LWLectureTestData> gridStore;
  private Lecture currentLecture;
  private Logger log = Logger.getLogger(ManageLecturesTestsController.class.getName());

  public ManageLecturesTestsController(ManageLecturesController.IManageLecturesView view, LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void bind() {
    gridStore = view.getTestsGrid().getStore();
    addListeneres();
  }

  private void addListeneres() {
    view.getTestsGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<LWLectureTestData>() {
      public void onSelectionChanged(SelectionChangedEvent<LWLectureTestData> event) {
        onTestsGridSelection(event);
      }
    });

    view.getCreateTestButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onCreateTestSelection();
      }
    });

    view.getEditTestButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onEditTestSelection();
      }
    });
  }

  private void onTestsGridSelection(SelectionChangedEvent<LWLectureTestData> event) {
    List<LWLectureTestData> selection = event.getSelection();
    if (selection == null || selection.isEmpty()) {
      if (currentLecture == null)
        view.setTestGridState(LectureTestsViewState.NONE);
      else
        view.setTestGridState(LectureTestsViewState.ADD);
    } else {
      view.setTestGridState(LectureTestsViewState.TEST_SELECTED);
    }
  }

  private void onCreateTestSelection() {
    CreateTestController.ICreateTestView view = new CreateTestView();
    CreateTestController createTestController = new CreateTestController(view);
    createTestController.bind();
    MasterWindow window = new MasterWindow();
    window.setContent(view.asWidget(), "Create Test View");
    window.setModal(true);
    window.setPixelSize(600, 350);
    window.show();
  }

  private void onEditTestSelection() {
    LWLectureTestData seletedLWTest = getSelectedLWTest();
    if (currentLecture == null || seletedLWTest == null)
      return;
    lectureService.getTest(currentLecture.getProfessor(), currentLecture.getId(), seletedLWTest.getName(), new ELearningAsyncCallBack<LectureTestData>(view, log) {
      @Override
      public void onSuccess(LectureTestData result) {
        if (result == null)
          return;
        CreateTestController.ICreateTestView view = new CreateTestView();
        CreateTestController createTestController = new CreateTestController(view, result);
        createTestController.bind();
        MasterWindow window = new MasterWindow();
        window.setContent(view.asWidget(), "Create Test View");
        window.setModal(true);
        window.setPixelSize(600, 350);
        window.show();
      }
    });
  }

  public void loadTests() {
    if (currentLecture == null)
      return;
    view.mask();
    lectureService.getAllLWTests(currentLecture.getProfessor(), currentLecture.getId(), new ELearningAsyncCallBack<List<LWLectureTestData>>(view, log) {
      @Override
      public void onSuccess(List<LWLectureTestData> result) {
        if (result != null) {
          gridStore.clear();
          gridStore.addAll(result);
        }
        view.unmask();
      }
    });
  }

  public void setSelectedLecture(Lecture lecture) {
    currentLecture = lecture;
    if (currentLecture != null)
      view.setTestGridState(LectureTestsViewState.ADD);
    else
      view.setTestGridState(LectureTestsViewState.NONE);
  }

  private LWLectureTestData getSelectedLWTest() {
    if (view.getTestsGrid().getSelectionModel() != null) {
      List<LWLectureTestData> selectedItems = view.getTestsGrid().getSelectionModel().getSelectedItems();
      if (selectedItems != null && !selectedItems.isEmpty())
        return selectedItems.get(0);
    }
    return null;
  }

  public void clearStore() {
    gridStore.clear();
  }
}
