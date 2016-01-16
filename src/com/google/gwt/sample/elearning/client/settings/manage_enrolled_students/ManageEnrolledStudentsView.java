package com.google.gwt.sample.elearning.client.settings.manage_enrolled_students;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.models_properties.LectureDataProperties;
import com.google.gwt.sample.elearning.client.eLearningUtils.models_properties.ProfessorProperties;
import com.google.gwt.sample.elearning.client.eLearningUtils.models_properties.UserDataProperties;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ManageEnrolledStudentsView implements ManageEnrolledStudentsController.IManageEnrolledStudentsView {

  private LectureDataProperties lectureDataProperties = GWT.create(LectureDataProperties.class);
  private UserDataProperties userProperties = GWT.create(UserDataProperties.class);
  private ProfessorProperties professorProperties = GWT.create(ProfessorProperties.class);
  private BorderLayoutContainer mainContainer;

  private TextButton refresButton;
  private TextButton removeFromLectureButton;
  private ComboBox<Lecture> lectureComboBox;
  private ComboBox<Professor> professorComboBox;
  private Grid<UserData> userGrid;

  public ManageEnrolledStudentsView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    refresButton = new TextButton("", ELearningController.ICONS.refresh());
    removeFromLectureButton = new TextButton("Remove form lecture", ELearningController.ICONS.delete());
    ListStore<Lecture> lectureListStore = new ListStore<Lecture>(lectureDataProperties.key());
    lectureComboBox = new ComboBox<Lecture>(lectureListStore, new LabelProvider<Lecture>() {
      public String getLabel(Lecture item) {
        return item.getLectureName();
      }
    });
    ListStore<Professor> professorListStore = new ListStore<Professor>(professorProperties.key());
    professorComboBox = new ComboBox<Professor>(professorListStore, new LabelProvider<Professor>() {
      public String getLabel(Professor item) {
        return item.getUsername();
      }
    });
    userGrid = createGrid();
    ToolBar toolBar = new ToolBar();

    lectureComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
    lectureComboBox.setEditable(false);
    lectureComboBox.setAllowBlank(false);
    professorComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
    professorComboBox.setEditable(false);
    professorComboBox.setAllowBlank(false);

    toolBar.setHorizontalSpacing(5);
    toolBar.add(refresButton);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(new Label("Professor: "));
    toolBar.add(professorComboBox);
    toolBar.add(new Label("Lecture: "));
    toolBar.add(lectureComboBox);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(removeFromLectureButton);

    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setCenterWidget(userGrid);
  }

  private Grid<UserData> createGrid() {
    IdentityValueProvider<UserData> identityValueProvider = new IdentityValueProvider<UserData>("sm");
    CheckBoxSelectionModel<UserData> selectionModel = new CheckBoxSelectionModel<UserData>(identityValueProvider);
    selectionModel.setSelectionMode(Style.SelectionMode.MULTI);

    List<ColumnConfig<UserData, ?>> columnConfigList = new ArrayList<ColumnConfig<UserData, ?>>();

    columnConfigList.add(selectionModel.getColumn());
    columnConfigList.add(new ColumnConfig<UserData, Long>(userProperties.id(), 20, "ID"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.username(), 100, "Username"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.firstName(), 100, "First Name"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.lastName(), 100, "Last Name"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.email(), 100, "Email"));
//    columnConfigList.add(new ColumnConfig<UserData, UserRoleTypes>(userProperties.role(), 100, "Role"));

    ColumnModel<UserData> columnModel = new ColumnModel<UserData>(columnConfigList);
    ListStore<UserData> store = new ListStore<UserData>(userProperties.key());

    Grid<UserData> userDataGrid = new Grid<UserData>(store, columnModel);

    userDataGrid.setLoadMask(true);

    userDataGrid.setHideHeaders(false);
    userDataGrid.getView().setAutoFill(true);
    userDataGrid.setSelectionModel(selectionModel);

    return userDataGrid;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
