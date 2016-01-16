package com.google.gwt.sample.elearning.client.lecture_views.left_panel.tests_view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.LWLectureTestData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;
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
public class LectureTestsController implements IResolveTestListener {

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
    view.getTestsGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<LWLectureTestData>() {
      public void onSelectionChanged(SelectionChangedEvent<LWLectureTestData> event) {
        setViewState();
      }
    });

    view.getResolveTestButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onResolveTestSelection();
      }
    });
  }

  private void onResolveTestSelection() {
    LWLectureTestData test = getSelectedTest();
    if (test == null)
      return;
    Lecture lecture = ELearningController.getInstance().getCurrentLecture();
    lectureService.getTest(lecture.getProfessor(), lecture.getId(), test.getName(), new AsyncCallback<LectureTestData>() {
      public void onFailure(Throwable caught) {
        log.warning(caught.getMessage());
      }

      public void onSuccess(LectureTestData result) {
        ResolveTestController.IResolveTestView testView = new ResolveTestView();
        ResolveTestController resolveTestController = new ResolveTestController(testView, result, LectureTestsController.this);
        resolveTestController.bind();

        MasterWindow window = new MasterWindow();
        window.setContent(testView.asWidget(), result.getName());
        window.setPixelSize(400, 250);
        window.show();
      }
    });
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

  private void setViewState() {
    if (getSelectedTest() != null)
      view.setState(ILectureTestsViewState.SELECTED);
    else
      view.setState(ILectureTestsViewState.NONE);
  }

  private LWLectureTestData getSelectedTest() {
    List<LWLectureTestData> selectedItems = view.getTestsGrid().getSelectionModel().getSelectedItems();
    if (selectedItems != null && !selectedItems.isEmpty())
      return selectedItems.get(0);
    return null;
  }

  @Override
  public void resolveTest(long userId,LectureTestData testData, final ResolveTestController.IResolveTestView testView) {
    testView.mask();
    lectureService.resolveTest(userId,testData, new ELearningAsyncCallBack<Long>(testView, log) {
      public void onSuccess(Long result) {
        testView.displayScore(result);
        testView.unmask();
      }
    });
  }
}

