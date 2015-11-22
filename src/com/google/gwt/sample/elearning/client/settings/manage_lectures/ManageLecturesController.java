package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.client.service.ProfessorService;
import com.google.gwt.sample.elearning.client.service.ProfessorServiceAsync;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristi on 11/17/2015.
 */
public class ManageLecturesController {

  private List<Professor> professorList;
  private List<Lecture> lectureList;
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);
  private ProfessorServiceAsync professorService = GWT.create(ProfessorService.class);

  public enum LectureViewState {
    ADD, EDIT, ADDING, NONE
  }

  public interface IManageLecturesView {
    TextButton getAddButton();

    TextButton getEditButton();

    TextButton getDeleteButton();

    TextField getLectureNameField();

    ComboBox<Professor> getProfessorComboBox();

    Grid<Lecture> getGrid();

    void setState(LectureViewState state);

    void loadLectures(Lecture userData);

    void clearFields();

    void mask();

    void unMask();

    Widget asWidget();
  }

  private IManageLecturesView view;

  public ManageLecturesController(IManageLecturesView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
    populateGrid();
    populateCombo();
  }

  private void populateGrid(){
    view.mask();
    lectureService.getAllLectures(new AsyncCallback<List<Lecture>>() {
      @Override
      public void onFailure(Throwable caught) {
        view.unMask();
        new MessageBox("", "Could not get Lectures").show();
      }

      @Override
      public void onSuccess(List<Lecture> result) {
        view.getGrid().getStore().clear();
        lectureList.clear();
        lectureList.addAll(result);
        view.getGrid().getStore().addAll(lectureList);
        view.unMask();
      }
    });
  }

  private void populateCombo(){
    view.mask();
    professorService.getAllProfessors(new AsyncCallback<List<Professor>>() {
      @Override
      public void onFailure(Throwable caught) {
        view.unMask();
        new MessageBox("", "Cold not get Professors").show();
      }

      @Override
      public void onSuccess(List<Professor> result) {
        view.getProfessorComboBox().getStore().clear();
        Professor all = new Professor(-1, "", "", "All", "", "");
        professorList.clear();
        professorList.add(all);
        professorList.addAll(result);
        view.getProfessorComboBox().getStore().addAll(professorList);
        view.unMask();
      }
    });
  }

  private void addListeners() {
    Grid<Lecture> grid = view.getGrid();
    grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<Lecture>() {
      public void onSelectionChanged(SelectionChangedEvent<Lecture> event) {
        doGridSelectionEvent(event);
      }
    });
    KeyUpHandler textFieldsValidator = new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        if (isLectureSelected()) {
          view.setState(LectureViewState.EDIT);
        } else {
          if (!TextInputValidator.isEmptyString(view.getLectureNameField().getText())
              && view.getProfessorComboBox().getValue().getId() != -1)
            view.setState(LectureViewState.ADD);
          else
            view.setState(LectureViewState.ADDING);
        }
      }
    };
    view.getLectureNameField().addKeyUpHandler(textFieldsValidator);

    view.getProfessorComboBox().addSelectionHandler(new SelectionHandler<Professor>() {
      @Override
      public void onSelection(SelectionEvent<Professor> event) {
        long selectedProfessordId = event.getSelectedItem().getId();
        onComboProfessorSelection(selectedProfessordId);
      }
    });

    view.getAddButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onAddButtonSelection();
        populateGrid();
      }
    });

    view.getDeleteButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onRemoveButtonSelection();
        populateGrid();
      }
    });

    view.getEditButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onEditButtonSelection();
        populateGrid();
      }
    });
  }

  private void onAddButtonSelection() {
    if (!TextInputValidator.isEmptyString(view.getLectureNameField().getText())) {
      Lecture lecture = new Lecture(view.getProfessorComboBox().getValue(), view.getLectureNameField().getText());
      lectureService.createLecture(lecture, new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable caught) {
          new MessageBox("", "Could not save Lecture").show();
        }

        @Override
        public void onSuccess(Void result) {
          new MessageBox("", "Lecture saved").show();
        }
      });
    } else {
      new MessageBox("", "Invalid input").show();
    }
  }

  private void onRemoveButtonSelection() {
    lectureService
        .removeLecture(view.getGrid().getSelectionModel().getSelectedItem().getId(), new AsyncCallback<Void>() {
          @Override
          public void onFailure(Throwable caught) {
            new MessageBox("", "Could not remove Lecture").show();
          }

          @Override
          public void onSuccess(Void result) {
            new MessageBox("", "Lecture removed").show();
          }
        });
  }

  private void onEditButtonSelection() {
    Lecture lecture = new Lecture(view.getGrid().getSelectionModel().getSelectedItem().getId(), view.getGrid().getSelectionModel().getSelectedItem().getProfessor(), view.getLectureNameField().getText());
    lectureService.updateLecture(lecture, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        new MessageBox("", "Could not update Lecture").show();
      }

      @Override
      public void onSuccess(Void result) {
        new MessageBox("", "Lecture updated").show();
      }
    });
  }

  private void onComboProfessorSelection(long selectedProfessordId) {
    List<Lecture> lectures = new ArrayList<Lecture>();
    if (selectedProfessordId == -1) {
      lectures.addAll(lectureList);
      view.setState(LectureViewState.ADDING);
    } else {
      if (!TextInputValidator.isEmptyString(view.getLectureNameField().getText()))
        view.setState(LectureViewState.ADD);
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

  private void doGridSelectionEvent(SelectionChangedEvent<Lecture> event) {
    List<Lecture> selection = event.getSelection();
    if (selection == null || selection.isEmpty())
      view.setState(LectureViewState.NONE);
    else {
      view.setState(LectureViewState.EDIT);
      view.loadLectures(selection.get(0));
    }

  }
}
