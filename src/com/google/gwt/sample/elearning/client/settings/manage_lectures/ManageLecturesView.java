package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/***
 * Created by Cristi on 11/17/2015.
 */
public class ManageLecturesView implements ManageLecturesController.IManageLecturesView {
  private static LectureDataProperties lectureProperties = GWT.create(LectureDataProperties.class);
  private static ProfessorProperties professorProperties = GWT.create(ProfessorProperties.class);
  private BorderLayoutContainer mainContainer;
  private TextButton deleteButton;
  private TextButton addButton;
  private TextButton editButton;
  private TextButton htmlEditor;
  private ComboBox<Professor> professorComboBox;
  private ListStore<Professor> professorListStore;

  private TextField lectureNameField;
  private Grid<Lecture> lectureGridView;
  private ManageLecturesController.LectureViewState state = ManageLecturesController.LectureViewState.NONE;

  private static Logger log = Logger.getLogger(ManageLecturesView.class.getName());

  public ManageLecturesView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    professorListStore = new ListStore<Professor>(professorProperties.key());
    professorComboBox = new ComboBox<Professor>(professorListStore, new StringLabelProvider<Professor>());
    professorComboBox.setEditable(false);
    professorComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
    addButton = new TextButton("Add", ELearningController.ICONS.add());
    editButton = new TextButton("Edit", ELearningController.ICONS.edit());
    deleteButton = new TextButton("Delete", ELearningController.ICONS.delete());
    lectureNameField = new TextField();
    htmlEditor = new TextButton("Create new file");

    ToolBar toolBar = new ToolBar();
    toolBar.add(new FieldLabel(professorComboBox, "Professor"));
    toolBar.add(new SeparatorToolItem());
    toolBar.add(htmlEditor);
    CenterLayoutContainer formContainer = new CenterLayoutContainer();
    VerticalLayoutContainer formPanel = new VerticalLayoutContainer();
    HBoxLayoutContainer buttonsContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    lectureGridView = createGrid();
    BoxLayoutContainer.BoxLayoutData hBoxLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(5));
    hBoxLayoutData.setFlex(1);
    buttonsContainer.add(addButton, hBoxLayoutData);
    buttonsContainer.add(editButton, hBoxLayoutData);
    buttonsContainer.add(deleteButton, hBoxLayoutData);
    buttonsContainer.setStyleName("buttonsBar");
    VerticalLayoutContainer.VerticalLayoutData verticalLayoutData = new VerticalLayoutContainer.VerticalLayoutData(1,
        -1);
    formPanel.add(new FieldLabel(lectureNameField, "Lecture Name"), verticalLayoutData);
    formPanel.add(buttonsContainer, verticalLayoutData);
    formContainer.add(formPanel);
    formContainer.setStyleName("whiteBackground");
    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(.6);
    layoutData.setSplit(true);
    layoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setWestWidget(lectureGridView, layoutData);
    mainContainer.setCenterWidget(formContainer);
    setState(state);
  }

  private Grid<Lecture> createGrid() {
    IdentityValueProvider<Lecture> identityValueProvider = new IdentityValueProvider<Lecture>("sm");
    CheckBoxSelectionModel<Lecture> selectionModel = new CheckBoxSelectionModel<Lecture>(identityValueProvider);
    selectionModel.setSelectionMode(Style.SelectionMode.SINGLE);

    List<ColumnConfig<Lecture, ?>> columnConfigList = new ArrayList<ColumnConfig<Lecture, ?>>();
    columnConfigList.add(selectionModel.getColumn());
    columnConfigList.add(new ColumnConfig<Lecture, Long>(lectureProperties.id(), 20, "ID"));
    columnConfigList.add(new ColumnConfig<Lecture, String>(lectureProperties.lectureName(), 100, "Lecture Name"));
    ColumnConfig<Lecture, String> profColumn = new ColumnConfig<Lecture, String>(new ValueProvider<Lecture, String>() {
      public String getValue(Lecture object) {
        return (object != null && object.getProfessor() != null) ? object.getProfessor().toString() : "";
      }
      public void setValue(Lecture object, String value) {}
      public String getPath() { return "professor_path"; }
    }, 100, "Professor");
    columnConfigList.add(profColumn);
    ColumnModel<Lecture> columnModel = new ColumnModel<Lecture>(columnConfigList);
    ListStore<Lecture> store = new ListStore<Lecture>(lectureProperties.key());

    GroupingView<Lecture> groupingView = new GroupingView<Lecture>();
    groupingView.setShowGroupedColumn(false);
    groupingView.setForceFit(true);
    groupingView.groupBy(profColumn);

    Grid<Lecture> lectureDataGrid = new Grid<Lecture>(store, columnModel, groupingView);

    lectureDataGrid.setHideHeaders(false);
    lectureDataGrid.getView().setAutoFill(true);
    lectureDataGrid.setSelectionModel(selectionModel);
    return lectureDataGrid;
  }

  @Override
  public void setState(ManageLecturesController.LectureViewState state) {
    switch (state) {
    case ADD:
      addButton.setEnabled(true);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      break;
    case EDIT:
      addButton.setEnabled(false);
      editButton.setEnabled(true);
      deleteButton.setEnabled(true);
      break;
    case ADDING:
      addButton.setEnabled(false);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      break;
    case NONE:
      addButton.setEnabled(false);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      clearFields();
      break;
    }
  }

  public void clearFields() {
    lectureNameField.setText("");
  }

  @Override
  public TextButton getAddButton() {
    return this.addButton;
  }

  @Override
  public TextButton getEditButton() {
    return this.editButton;
  }

  @Override
  public TextButton getDeleteButton() {
    return this.deleteButton;
  }

  @Override
  public TextButton getHtmlEditorButton() {
    return htmlEditor;
  }

  @Override
  public TextField getLectureNameField() {
    return this.lectureNameField;
  }

  @Override
  public ComboBox<Professor> getProfessorComboBox() {
    return this.professorComboBox;
  }

  @Override
  public Grid<Lecture> getGrid() {
    return this.lectureGridView;
  }

  @Override
  public void loadLectures(Lecture lecture) {
    lectureNameField.setText(lecture.getLectureName());
  }

  @Override
  public void mask() {
    mainContainer.mask();
  }

  @Override
  public void unMask() {
    mainContainer.unmask();
  }

  @Override
  public Widget asWidget() {
    return this.mainContainer;
  }
}
