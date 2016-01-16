package com.google.gwt.sample.elearning.client.lecture_views.left_panel.homeworks_view;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.homeworks_view.resolve_homework.ResolveHomeworkController;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.homeworks_view.resolve_homework.ResolveHomeworkView;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
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
 * Created by Cristi on 1/16/2016.
 */
public class LectureHomeworksController {
  public enum ILectureHomeworksViewState {
    SELECTED, NONE
  }
  public interface ILectureHomeworksView {
    Widget asWidget();
    TextButton getResolveButton();
    void setState(ILectureHomeworksViewState state);
    Grid<HomeworkData> getGrid();

  }

  ILectureHomeworksView view;
  Logger logger = Logger.getLogger(LectureHomeworksController.class.getName());
  private ListStore<HomeworkData> store;
  private LectureServiceAsync lectureService;


  public LectureHomeworksController(ILectureHomeworksView view, LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void bind(){
    store = view.getGrid().getStore();
    addListeners();
  }

  private void addListeners() {
    view.getGrid().getSelectionModel().addSelectionChangedHandler(
        new SelectionChangedEvent.SelectionChangedHandler<HomeworkData>() {
          @Override
          public void onSelectionChanged(SelectionChangedEvent<HomeworkData> event) {
            setViewState();
          }
        });
    view.getResolveButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnResolve();
      }
    });
  }

  private void doOnResolve() {
    HomeworkData homework = getSelectedHomework();
    ResolveHomeworkController.IResolveHomeworkView resolveHomeworkView = new ResolveHomeworkView();
    ResolveHomeworkController resolveHomeworkController = new ResolveHomeworkController(resolveHomeworkView, homework, lectureService);
    resolveHomeworkController.bind();
    MasterWindow window = new MasterWindow();
    window.setContent(resolveHomeworkView.asWidget(), homework.getTitle());
    window.setModal(true);
    window.setMinHeight(285);
    window.setMinWidth(270);
    window.setPixelSize(270, 285);
    window.setResizable(true);
    window.show();
    resolveHomeworkController.setContentWindow(window);
  }

  private void setViewState() {
    if (getSelectedHomework() != null)
      view.setState(ILectureHomeworksViewState.SELECTED);
    else
      view.setState(ILectureHomeworksViewState.NONE);
  }

  private HomeworkData getSelectedHomework() {
    List<HomeworkData> selectedItems = view.getGrid().getSelectionModel().getSelectedItems();
    if (selectedItems != null && !selectedItems.isEmpty())
      return selectedItems.get(0);
    return null;
  }

  public void loadHomeworks() {
    lectureService.getAllHomeworks(ELearningController.getInstance().getCurrentUser(),
        ELearningController.getInstance().getCurrentLecture().getId(), new AsyncCallback<List<HomeworkData>>() {
          @Override
          public void onFailure(Throwable caught) {
            logger.warning(caught.getMessage());
          }

          @Override
          public void onSuccess(List<HomeworkData> result) {
            store.clear();
            if (result != null && !result.isEmpty()){
              store.addAll(result);
            }
          }
        });
  }
}
