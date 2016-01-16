package com.google.gwt.sample.elearning.client.lecture_views.left_panel.tests_view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.LWLectureTestData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class LectureTestsController {

  public enum ILectureTestsViewState {
    SELECTED, NONE;
  }

  public interface ILectureTestView {
    TextButton getResolveTestButton();
    Grid<LWLectureTestData> getTestsGrid();
    void setState(ILectureTestsViewState state);
    Widget asWidget();
  }

  private ILectureTestView view;
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);
  private ListStore<LWLectureTestData> store;
  private Logger log = Logger.getLogger(LectureTestsController.class.getName());

  public LectureTestsController(ILectureTestView view, LectureServiceAsync lectureService) {
    this.view = view;
//    this.lectureService = lectureService;
  }

  public void bind() {
    store = view.getTestsGrid().getStore();
    addListers();
  }

  private void addListers() {

  }

  public void loadTests() {
    Lecture lecture = ELearningController.getInstance().getCurrentLecture();
    if (lecture == null || lectureService == null) {
      log.info("null service ****");
      return;
    }
    lectureService.getAllLWTests(lecture.getProfessor(), lecture.getId(), new AsyncCallback<List<LWLectureTestData>>() {
      public void onFailure(Throwable caught) {
        log.warning(caught.getMessage());
      }

      public void onSuccess(List<LWLectureTestData> result) {
        store.clear();
        if (result != null && !result.isEmpty())
          for (LWLectureTestData test : result)
            store.add(test);
      }
    });
  }
}

