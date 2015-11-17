package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
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

    Widget asWidget();
  }

  private IManageLecturesView view;

  public ManageLecturesController(IManageLecturesView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
    addTestData();
  }

  private void addTestData() {
    ListStore<Lecture> listStore = view.getGrid().getStore();
    Professor prof1 = new Professor(0, "user1", "pwd1", "fName1", "lName1", "eMail1");
    Professor prof2 = new Professor(1, "user2", "pwd2", "fName2", "lName2", "eMail2");
    lectureList = new ArrayList<Lecture>();
    lectureList.add(new Lecture(0, prof1, "denumire1"));
    lectureList.add(new Lecture(1, prof1, "denumire2"));
    lectureList.add(new Lecture(2, prof1, "denumire3"));
    lectureList.add(new Lecture(3, prof2, "denumire4"));
    lectureList.add(new Lecture(4, prof2, "denumire5"));

    view.getGrid().getStore().addAll(lectureList);

    professorList = new ArrayList<Professor>();
    Professor all = new Professor(-1, "", "", "All", "", "");
    professorList.add(all);
    professorList.add(prof1);
    professorList.add(prof2);
    ListStore<Professor> profStore = view.getProfessorComboBox().getStore();

    profStore.addAll(professorList);
    view.getProfessorComboBox().setValue(all);
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
          if (!TextInputValidator.isEmptyString(view.getLectureNameField().getText()))
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
  }

  private void onComboProfessorSelection(long selectedProfessordId) {
    List<Lecture> lectures = new ArrayList<Lecture>();
    if (selectedProfessordId == -1) {
      lectures.addAll(lectureList);
    } else {
      for (Lecture lecture : lectureList) {
        if(lecture.getProfessor().getId() == selectedProfessordId){
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
