package com.google.gwt.sample.elearning.client.lecture_views.right_panel.usersView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.main_views.right_panel.usersView.UsersPopupController;
import com.google.gwt.sample.elearning.client.main_views.right_panel.usersView.UsersPopupView;
import com.google.gwt.sample.elearning.client.service.UserService;
import com.google.gwt.sample.elearning.client.service.UserServiceAsync;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sencha.gxt.widget.core.client.Popup;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

import java.util.ArrayList;
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
    SingleSelectionModel<UserData> getSelectionModel();
    void mask();
    void unmask();
  }

  private Logger logger = Logger.getLogger(LectureUsersController.class.getName());
  private UserServiceAsync userServiceAsync = GWT.create(UserService.class);
  private ILectureUsersView view;
  private List<UserData> lectureUsers;

  public LectureUsersController(ILectureUsersView view) {
    this.view = view;
  }

  public void bind(){
    addListeners();
  }

  private void addListeners() {
    view.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        UsersPopupView usersPopupView = new UsersPopupView();
        Popup popup = new Popup();
        popup.add(usersPopupView.asWidget());
        popup.setSize("280","80");
        UsersPopupController popupController = new UsersPopupController(usersPopupView, view.getSelectionModel().getSelectedObject());
        popupController.bind();
        int yLocation = 50 + 25 + lectureUsers.indexOf(view.getSelectionModel().getSelectedObject()) * 26;
        int screenWidth = RootPanel.get().getOffsetWidth();
        int mainContainerWidth = ( (BorderLayoutContainer) view.asWidget()).getOffsetWidth();
        popup.showAt(screenWidth - mainContainerWidth - 10 - 280, yLocation);
      }
    });
  }

  public void loadUsers(){
    userServiceAsync.getUsersByLecture(ELearningController.getInstance().getCurrentLecture().getId(), new ELearningAsyncCallBack<List<? extends UserData>>(view, logger) {
      @Override
      public void onSuccess(List<? extends UserData> result) {
        lectureUsers = new ArrayList<UserData>();
        long currentUserId = ELearningController.getInstance().getCurrentUser().getId();
        for(UserData user : result){
          if(user.getId() != currentUserId){
            lectureUsers.add(user);
          }
        }
        view.getCellList().setRowCount(lectureUsers.size(), true);
        view.getCellList().setRowData(0, lectureUsers);
        logger.info("Number of subscribed users to this lecture is: " + lectureUsers.size());
      }
    });
  }
}
