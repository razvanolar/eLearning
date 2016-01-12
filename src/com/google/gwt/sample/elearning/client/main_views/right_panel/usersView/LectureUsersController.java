package com.google.gwt.sample.elearning.client.main_views.right_panel.usersView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.service.UserService;
import com.google.gwt.sample.elearning.client.service.UserServiceAsync;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RowCountChangeEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by Cristi on 1/3/2016.
 */
public class LectureUsersController {
  public interface ILectureUsersView extends MaskableView{
    CellList<UserData> getCellList();
    Widget asWidget();
    void mask();
    void unmask();
  }

  private Logger logger = Logger.getLogger(LectureUsersController.class.getName());
  private UserServiceAsync userServiceAsync = GWT.create(UserService.class);
  private ILectureUsersView view;

  public LectureUsersController(ILectureUsersView view) {
    this.view = view;
  }

  public void bind(){
    addListeners();
  }

  private void addListeners() {
    view.getCellList().addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
      @Override
      public void onRowCountChange(RowCountChangeEvent event) {
        view.getCellList().setVisibleRange(new Range(0, event.getNewRowCount()));
      }
    });
  }

  public void loadUsers(){
    userServiceAsync.getUsersByLecture(ELearningController.getInstance().getCurrentLecture().getId(), new ELearningAsyncCallBack<List<? extends UserData>>(view, logger) {
      @Override
      public void onSuccess(List<? extends UserData> result) {
        view.getCellList().setRowCount(result.size(), true);
        view.getCellList().setRowData(0, result);
        logger.info(result.size() + " result");
      }
    });
  }
}
