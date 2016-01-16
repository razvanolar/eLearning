package com.google.gwt.sample.elearning.client.settings.manage_enrolled_students;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ManageEnrolledStudentsController implements ISettingsController {

  public interface IManageEnrolledStudentsView extends MaskableView {
    TextButton getRefresButton();
    TextButton getRemoveFromLectureButton();
    ComboBox<Lecture> getLectureComboBox();
    ComboBox<Professor> getProfessorComboBox();
    Grid<UserData> getUserGrid();
    Widget asWidget();
  }

  private IManageEnrolledStudentsView view;
  private UserServiceAsync userService = GWT.create(UserService.class);
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);
  private Logger log = Logger.getLogger(ManageEnrolledStudentsController.class.getName());

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

      }
    });
  }

  @Override
  public void loadResources() {
    loadProfessors();
    loadLectures();
  }

  private void loadProfessors() {
    userService.getAllUsersByRole(UserRoleTypes.PROFESSOR, new ELearningAsyncCallBack<List<? extends UserData>>(view, log) {
      public void onSuccess(List<? extends UserData> result) {
        ListStore<Professor> professorListStore = view.getProfessorComboBox().getStore();
        professorListStore.clear();
        if (result != null && !result.isEmpty()) {
          for (UserData user : result)
            professorListStore.add(new Professor(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
                    user.getLastName(), user.getEmail()));
          view.getProfessorComboBox().setValue(professorListStore.get(0));
        }
      }
    });
  }

  private void loadLectures() {
    Professor professor = getSelectedProffesor();
    if (professor == null)
      return;
    lectureService.getAllLecturesByUser(professor.getId(), new ELearningAsyncCallBack<List<Lecture>>(view, log) {
      public void onSuccess(List<Lecture> result) {
        ListStore<Lecture> lectureListStore = view.getLectureComboBox().getStore();
        lectureListStore.clear();
        Lecture all = new Lecture(-1, getSelectedProffesor(), "All", "");
        lectureListStore.add(all);
        if (result != null && !result.isEmpty()) {
          lectureListStore.addAll(result);
          view.getLectureComboBox().setValue(lectureListStore.get(0));
        }
      }
    });
  }

  private void loadGrid() {
    Lecture lecture = getSelectedLecture();
    if (lecture == null)
      return;
    
  }

  private Lecture getSelectedLecture() {
    return view.getLectureComboBox().getValue();
  }

  private Professor getSelectedProffesor() {
    return view.getProfessorComboBox().getValue();
  }

  @Override
  public String getControllerName() {
    return "ManageEnrolledStudentsController";
  }
}
