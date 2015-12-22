package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.client.service.*;
import com.google.gwt.sample.elearning.client.settings.ISettingsController;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_files.ManageLecturesFilesController;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_tests.ManageLecturesTestsController;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_videos.ManageLecturesVideosController;
import com.google.gwt.sample.elearning.shared.model.*;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by Cristi on 11/17/2015.
 */
public class ManageLecturesController implements ISettingsController {

  private List<Professor> professorList;
  private List<Lecture> lectureList;
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);
  private UserServiceAsync userServiceAsync = GWT.create(UserService.class);
  private ManageLecturesFilesController lecturesFilesController;
  private ManageLecturesTestsController lecturesTestsController;
  private ManageLecturesVideosController lecturesVideosController;

  public enum LectureGridViewState {
    ADD, EDIT, ADDING, NONE
  }

  public enum LecturesFilesAndTestsState {
    FILES, TESTS, VIDEOS
  }

  public interface IManageLecturesView extends MaskableView{
    TextButton getAddButton();
    TextButton getEditButton();
    TextButton getDeleteButton();
    TextField getLectureNameField();
    ToggleButton getFileToggleButton();
    ToggleButton getTestToggleButton();
    ToggleButton getVideosToggleButton();
    ComboBox<Professor> getProfessorComboBox();
    TextButton getCreateHtmlButton();
    TextButton getEditHtmlButton();
    TextButton getDeleteHtmlButton();
    TextButton getCreateFolderButton();
    TextButton getFileDowloadButton();
    TextButton getFileUploadButton();
    TextButton getCreateTestButton();
    TextButton getEditTestButton();
    TextButton getDeleteTestButton();
    TextButton getPlayVideoButton();
    TextButton getAddVideoLinkButton();
    FormPanel getFileFormPanel();
    FileUpload getFileUpload();
    TreeGrid<FileData> getTreeGrid();
    Grid<LWLectureTestData> getTestsGrid();
    Grid<VideoLinkData> getVideosGrid();
    void setTreeState(ManageLecturesFilesController.LectureTreeViewState state);
    void setTestGridState(ManageLecturesTestsController.LectureTestsViewState state);
    void setVideoGridState(ManageLecturesVideosController.ManageVideosToolBarState state);
    void setCenterWidgetState(LecturesFilesAndTestsState state);
    Grid<Lecture> getGrid();
    void setGridState(LectureGridViewState state);
    void loadLectures(Lecture userData);
    void clearFields();
    void mask();
    void unmask();
    Widget asWidget();
  }

  private IManageLecturesView view;

  public ManageLecturesController(IManageLecturesView view) {
    this.view = view;
    lecturesFilesController = new ManageLecturesFilesController(view, lectureService);
    lecturesTestsController = new ManageLecturesTestsController(view, lectureService);
    lecturesVideosController = new ManageLecturesVideosController(view, lectureService);
  }

  public void bind() {
    lectureList = new ArrayList<Lecture>();
    professorList = new ArrayList<Professor>();
    lecturesFilesController.bind();
    lecturesTestsController.bind();
    lecturesVideosController.bind();
    addListeners();
  }

  @Override
  public void loadResources() {
    ListStore<Lecture> lecturesStore = view.getGrid().getStore();
    if (lecturesStore == null || lecturesStore.getAll().isEmpty())
      populateGrid();
    ListStore<Professor> professorStore = view.getProfessorComboBox().getStore();
    if (professorStore == null || professorStore.getAll().isEmpty())
      populateCombo();
  }

  private void populateGrid() {
    view.mask();
    lectureService.getAllLectures(new ELearningAsyncCallBack<List<Lecture>>(view,log) {
      public void onSuccess(List<Lecture> result) {
        view.getGrid().getStore().clear();
        lectureList.clear();
        lectureList.addAll(result);
        view.getGrid().getStore().addAll(lectureList);
        view.unmask();
      }
    });
  }

  private void populateCombo() {
    view.mask();
    userServiceAsync.getAllUsersByRole(UserRoleTypes.PROFESSOR, new ELearningAsyncCallBack<List<? extends UserData>>(view, log) {
      public void onSuccess(List<? extends UserData> result) {
        view.getProfessorComboBox().getStore().clear();
        Professor all = new Professor(-1, "", "", "All", "", "");
        professorList.clear();
        professorList.add(all);
        for (UserData user : result) {
          professorList.add(new Professor(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
                  user.getLastName(), user.getEmail()));
        }
        view.getProfessorComboBox().getStore().addAll(professorList);
        view.getProfessorComboBox().setValue(all);
        view.unmask();
      }
    });
  }

  private void addListeners() {
    Grid<Lecture> grid = view.getGrid();
    /* lecture grid selection listener */
    grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<Lecture>() {
      public void onSelectionChanged(SelectionChangedEvent<Lecture> event) {
        onGridSelection(event);
      }
    });

    /* lecture tree selection listener */

    KeyUpHandler textFieldsValidator = new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        if (isLectureSelected()) {
          view.setGridState(LectureGridViewState.EDIT);
        } else {
          if (!TextInputValidator.isEmptyString(view.getLectureNameField().getText())
                  && view.getProfessorComboBox().getValue().getId() != -1)
            view.setGridState(LectureGridViewState.ADD);
          else
            view.setGridState(LectureGridViewState.ADDING);
        }
      }
    };

    view.getLectureNameField().addKeyUpHandler(textFieldsValidator);

    view.getProfessorComboBox().addSelectionHandler(new SelectionHandler<Professor>() {
      public void onSelection(SelectionEvent<Professor> event) {
        long selectedProfessordId = event.getSelectedItem().getId();
        onComboProfessorSelection(selectedProfessordId);
      }
    });

    view.getAddButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onAddButtonSelection();
      }
    });

    view.getDeleteButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onRemoveButtonSelection();
      }
    });

    view.getEditButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onEditButtonSelection();
      }
    });

    view.getFileToggleButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
          view.setCenterWidgetState(LecturesFilesAndTestsState.FILES);
          lecturesFilesController.loadFileTree();
        }
      }
    });

    view.getTestToggleButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
          view.setCenterWidgetState(LecturesFilesAndTestsState.TESTS);
          lecturesTestsController.loadTests();
        }
      }
    });

    view.getVideosToggleButton().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
          view.setCenterWidgetState(LecturesFilesAndTestsState.VIDEOS);
          lecturesVideosController.loadVideos();
        }
      }
    });
  }

  Logger log = Logger.getLogger(ManageLecturesController.class.getName());

  private void onAddButtonSelection() {
    log.info("onAdd");
    if (!TextInputValidator.isEmptyString(view.getLectureNameField().getText())) {
      Lecture lecture = new Lecture(view.getProfessorComboBox().getValue(), view.getLectureNameField().getText());
      lectureService.createLecture(lecture, new ELearningAsyncCallBack<Void>(view,log) {
        @Override
        public void onSuccess(Void result) {
          new MessageBox("", "Lecture saved").show();
          populateGrid();
          view.clearFields();
        }
      });
    } else {
      new MessageBox("", "Invalid input").show();
    }
  }

  private void onRemoveButtonSelection() {
    lectureService
            .removeLecture(view.getGrid().getSelectionModel().getSelectedItem().getId(), new ELearningAsyncCallBack<Void>(view,log) {
              @Override
              public void onSuccess(Void result) {
                new MessageBox("", "Lecture removed").show();
                populateGrid();
                view.clearFields();
              }
            });
  }

  private void onEditButtonSelection() {
    Lecture lecture = new Lecture(view.getGrid().getSelectionModel().getSelectedItem().getId(),
            view.getGrid().getSelectionModel().getSelectedItem().getProfessor(), view.getLectureNameField().getText());
    lectureService.updateLecture(lecture, new ELearningAsyncCallBack<Void>(view,log) {
      @Override
      public void onSuccess(Void result) {
        new MessageBox("", "Lecture updated").show();
        populateGrid();
      }
    });
  }

  private void onComboProfessorSelection(long selectedProfessordId) {
    List<Lecture> lectures = new ArrayList<Lecture>();
    if (selectedProfessordId == -1) {
      lectures.addAll(lectureList);
      view.setGridState(LectureGridViewState.ADDING);
    } else {
      if (!TextInputValidator.isEmptyString(view.getLectureNameField().getText()))
        view.setGridState(LectureGridViewState.ADD);
      for (Lecture lecture : lectureList) {
        if (lecture.getProfessor().getId() == selectedProfessordId) {
          lectures.add(lecture);
        }
      }
    }
    view.getGrid().getStore().clear();
    view.getGrid().getStore().addAll(lectures);
  }

  private Lecture getSelectedLecture() {
    if (view.getGrid() != null && view.getGrid().getSelectionModel() != null) {
      return view.getGrid().getSelectionModel().getSelectedItem();
    }
    return null;
  }

  private boolean isLectureSelected() {
    return getSelectedLecture() != null;
  }

  private void onGridSelection(SelectionChangedEvent<Lecture> event) {
    List<Lecture> selection = event.getSelection();
    lecturesFilesController.clearStore();
    lecturesTestsController.clearStore();
    if (selection == null || selection.isEmpty()) {
      view.setGridState(LectureGridViewState.NONE);
      lecturesFilesController.setSelectedLecture(null);
      lecturesTestsController.setSelectedLecture(null);
      lecturesVideosController.setSelectedLecture(null);
    } else {
      view.setGridState(LectureGridViewState.EDIT);
      Lecture lecture = selection.get(0);
      view.loadLectures(lecture);
      lecturesFilesController.setSelectedLecture(lecture);
      lecturesTestsController.setSelectedLecture(lecture);
      lecturesVideosController.setSelectedLecture(lecture);
      if (view.getFileToggleButton().getValue())
        lecturesFilesController.loadFileTree();
      else if (view.getTestToggleButton().getValue())
        lecturesTestsController.loadTests();
      else if (view.getVideosToggleButton().getValue())
        lecturesVideosController.loadVideos();
    }
  }

  @Override
  public String getControllerName() {
    return "ManageLecturesController";
  }
}
