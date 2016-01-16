package com.google.gwt.sample.elearning.client.settings.manage_enrolled_students;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.client.service.UserService;
import com.google.gwt.sample.elearning.client.service.UserServiceAsync;
import com.google.gwt.sample.elearning.client.settings.ISettingsController;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ManageEnrolledStudentsController implements ISettingsController {

  public enum IManageEnrolledStudentsState {
    DELETE, NONE
  }

  public interface IManageEnrolledStudentsView extends MaskableView {
    TextButton getRefresButton();
    TextButton getRemoveFromLectureButton();
    ComboBox<Lecture> getLectureComboBox();
    ComboBox<Professor> getProfessorComboBox();
    Grid<UserData> getUserGrid();
    void setState(IManageEnrolledStudentsState state);
    Widget asWidget();
  }

  private IManageEnrolledStudentsView view;
  private UserServiceAsync userService = GWT.create(UserService.class);
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);
  private Logger log = Logger.getLogger(ManageEnrolledStudentsController.class.getName());
  private long professorId;
  private long lectureId;

  public ManageEnrolledStudentsController(IManageEnrolledStudentsView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
    loadResources();
  }

  private void addListeners() {
    view.getProfessorComboBox().addSelectionHandler(new SelectionHandler<Professor>() {
      public void onSelection(SelectionEvent<Professor> event) {
        professorId = event.getSelectedItem().getId();
        loadLectures(professorId);
      }
    });

    view.getLectureComboBox().addSelectionHandler(new SelectionHandler<Lecture>() {
      public void onSelection(SelectionEvent<Lecture> event) {
        lectureId = event.getSelectedItem().getId();
        setViewState();
        loadGrid(lectureId);
      }
    });

    view.getRemoveFromLectureButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        doOnRemoveUserFromLecture();
      }
    });

    view.getUserGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<UserData>() {
      public void onSelectionChanged(SelectionChangedEvent<UserData> event) {
        setViewState();
      }
    });
  }

  private void doOnRemoveUserFromLecture() {
    if (lectureId == -1)
      return;
    UserData user = getSelectedUser();
    if (user == null)
      return;
    userService.removeUserFromLecture(lectureId, user.getId(), new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        log.warning(caught.getMessage());
      }

      public void onSuccess(Void result) {
        loadGrid(lectureId);
      }
    });
  }

  @Override
  public void loadResources() {
    loadProfessors();
  }

  private void loadProfessors() {
    if (ELearningController.getInstance().getCurrentUser().getRole() == UserRoleTypes.ADMIN) {
      userService.getAllUsersByRole(UserRoleTypes.PROFESSOR, new ELearningAsyncCallBack<List<? extends UserData>>(view, log) {
        public void onSuccess(List<? extends UserData> result) {
          ListStore<Professor> professorListStore = view.getProfessorComboBox().getStore();
          professorListStore.clear();
          if (result != null && !result.isEmpty()) {
            for (UserData user : result)
              professorListStore.add(new Professor(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
                      user.getLastName(), user.getEmail()));
            professorId = professorListStore.get(0).getId();
            view.getProfessorComboBox().setValue(professorListStore.get(0));
          }
          loadLectures(professorId);
        }
      });
    } else if (ELearningController.getInstance().getCurrentUser().getRole() == UserRoleTypes.PROFESSOR) {
      UserData user = ELearningController.getInstance().getCurrentUser();
      professorId = user.getId();
      view.getProfessorComboBox().getStore().clear();
      view.getProfessorComboBox().setEnabled(false);
      Professor professor = new Professor(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
              user.getLastName(), user.getEmail());
      view.getProfessorComboBox().getStore().add(professor);
      view.getProfessorComboBox().setValue(professor);
      loadLectures(professorId);
    }
  }

  private void loadLectures(long professorId) {
    if (professorId == -1)
      return;
    view.mask();
    lectureService.getAllLecturesByUser(professorId, new ELearningAsyncCallBack<List<Lecture>>(view, log) {
      public void onSuccess(List<Lecture> result) {
        ListStore<Lecture> lectureListStore = view.getLectureComboBox().getStore();
        lectureListStore.clear();
        Lecture all = new Lecture(-1, null, "All", "");
        lectureListStore.add(all);
        if (result != null && !result.isEmpty()) {
          for (Lecture lecture : result)
            lectureListStore.add(lecture);
        }
        view.unmask();
        view.getLectureComboBox().setValue(lectureListStore.get(0));
        loadGrid(-1);
      }
    });
  }

  private void loadGrid(long lectureId) {
    view.setState(IManageEnrolledStudentsState.NONE);
    if (lectureId == -1) {
      userService.getEnrolledStudentsByProfessorId(professorId, new ELearningAsyncCallBack<List<UserData>>(view, log) {
        public void onSuccess(List<UserData> result) {
          ListStore<UserData> store = view.getUserGrid().getStore();
          store.clear();
          if (result != null && !result.isEmpty())
            store.addAll(result);
        }
      });
    } else {
      userService.getUsersByLecture(lectureId, new ELearningAsyncCallBack<List<? extends UserData>>(view, log) {
        public void onSuccess(List<? extends UserData> result) {
          ListStore<UserData> store = view.getUserGrid().getStore();
          store.clear();
          if (result != null && !result.isEmpty())
            store.addAll(result);
        }
      });
    }
  }

  private void setViewState() {
    if (getSelectedUser() != null && lectureId != -1)
      view.setState(IManageEnrolledStudentsState.DELETE);
    else
      view.setState(IManageEnrolledStudentsState.NONE);
  }

  private UserData getSelectedUser() {
    if (view.getUserGrid().getSelectionModel() != null) {
      List<UserData> selectedItems = view.getUserGrid().getSelectionModel().getSelectedItems();
      if (selectedItems != null && !selectedItems.isEmpty())
        return selectedItems.get(0);
    }
    return null;
  }

  @Override
  public String getControllerName() {
    return "ManageEnrolledStudentsController";
  }
}
