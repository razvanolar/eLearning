package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_files.ManageLecturesFilesController;
import com.google.gwt.sample.elearning.shared.model.FileData;
import com.google.gwt.sample.elearning.shared.model.LWLectureTestData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import java.util.logging.Logger;

/***
 * Created by Cristi on 11/17/2015.
 */
public class ManageLecturesView implements ManageLecturesController.IManageLecturesView {
  private static ProfessorProperties professorProperties = GWT.create(ProfessorProperties.class);
  private ManageLecturesViewUtils viewUtils;

  private BorderLayoutContainer mainContainer;
  private TextButton refreshButton;
  private TextButton deleteButton;
  private TextButton addButton;
  private TextButton editButton;
  private TextButton createHtmlFile;
  private TextButton createFolderButton;
  private TextButton deleteFileButton;
  private TextButton editFileButton;
  private TextButton downloadFileButton;
  private TextButton uploadFileButton;
  private TextButton createTestButton;
  private TextButton editTestButton;
  private TextButton deleteTestButton;
  private ComboBox<Professor> professorComboBox;
  private TextField lectureNameField;
  private Grid<Lecture> lectureGridView;
  private Grid<LWLectureTestData> lectureTestDataGrid;
  private TreeGrid<FileData> fileTreeGrid;
  private RadioButton filesRadioButton;
  private RadioButton testsRadioButton;

  private ManageLecturesController.LectureGridViewState state = ManageLecturesController.LectureGridViewState.NONE;
  private static Logger log = Logger.getLogger(ManageLecturesView.class.getName());
  private BorderLayoutContainer filesPanelContainer;
  private BorderLayoutContainer testsPanelContainer;
  private FormPanel fileFormPanel;
  private FileUpload fileUpload;

  public ManageLecturesView() {
    viewUtils = new ManageLecturesViewUtils();
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    ListStore<Professor> professorListStore = new ListStore<Professor>(professorProperties.key());
    professorComboBox = new ComboBox<Professor>(professorListStore, new StringLabelProvider<Professor>());
    professorComboBox.setEditable(false);
    professorComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
    refreshButton = new TextButton("", ELearningController.ICONS.refresh());
    addButton = new TextButton("Add", ELearningController.ICONS.add());
    editButton = new TextButton("Edit", ELearningController.ICONS.edit());
    deleteButton = new TextButton("Delete", ELearningController.ICONS.delete());
    lectureNameField = new TextField();
    createHtmlFile = new TextButton("New file", ELearningController.ICONS.newfile());
    createFolderButton = new TextButton("New folder", ELearningController.ICONS.newfolder());
    deleteFileButton = new TextButton("Delete", ELearningController.ICONS.deletefile());
    editFileButton = new TextButton("Edit file", ELearningController.ICONS.editfile());
    downloadFileButton = new TextButton("Download", ELearningController.ICONS.download());
    uploadFileButton = new TextButton("Upload", ELearningController.ICONS.upload());
    createTestButton = new TextButton("Create Test");
    editTestButton = new TextButton("Edit test");
    deleteTestButton = new TextButton("Delete test");
    lectureGridView = viewUtils.createLecturesGrid();
    fileTreeGrid = viewUtils.createFileTreeGrid();
    lectureTestDataGrid = viewUtils.createTestsGrid();
    filesRadioButton = new RadioButton("Files", "Files");
    testsRadioButton = new RadioButton("Tests", "Tests");
    ToggleGroup radioGroup = new ToggleGroup();
    BorderLayoutContainer gridContainer = new BorderLayoutContainer();
    filesPanelContainer = new BorderLayoutContainer();
    testsPanelContainer = new BorderLayoutContainer();
    fileFormPanel = new FormPanel();
    fileUpload = new FileUpload();
    HorizontalPanel fileUploadContainer = new HorizontalPanel();
    ContentPanel filesGridContentPanel = new ContentPanel();
    ContentPanel testGridContentPanel = new ContentPanel();
    ToolBar toolBar = new ToolBar();
    ToolBar filesToolBar = new ToolBar();
    ToolBar testToolBar = new ToolBar();


    fileFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
    fileFormPanel.setMethod(FormPanel.METHOD_POST);
    fileFormPanel.setWidget(fileUploadContainer);
    fileUpload.setName("fileUpload");
    fileUploadContainer.add(fileUpload);

    toolBar.add(refreshButton);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(new FieldLabel(professorComboBox, "Professor"));
    toolBar.add(new SeparatorToolItem());

    CenterLayoutContainer formContainer = new CenterLayoutContainer();
    VerticalLayoutContainer formPanel = new VerticalLayoutContainer();
    HBoxLayoutContainer buttonsContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
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

    gridContainer.setCenterWidget(lectureGridView);
    BorderLayoutContainer.BorderLayoutData gridLayoutData = new BorderLayoutContainer.BorderLayoutData(80);
    gridLayoutData.setMargins(new Margins(5, 0, 0, 0));
    gridContainer.setSouthWidget(formContainer, gridLayoutData);

    filesRadioButton.setValue(true);
    radioGroup.add(filesRadioButton);
    radioGroup.add(testsRadioButton);

    toolBar.add(new FillToolItem());
    toolBar.add(filesRadioButton);
    toolBar.add(testsRadioButton);
    toolBar.setHorizontalSpacing(5);

    filesToolBar.add(createHtmlFile);
    filesToolBar.add(editFileButton);
    filesToolBar.add(createFolderButton);
    filesToolBar.add(deleteFileButton);
    filesToolBar.add(downloadFileButton);
    filesToolBar.add(uploadFileButton);
    filesToolBar.add(fileFormPanel);
    filesToolBar.setHorizontalSpacing(2);

    testToolBar.add(createTestButton);
    testToolBar.add(editTestButton);
    testToolBar.add(deleteTestButton);

    filesGridContentPanel.setHeaderVisible(false);
    filesGridContentPanel.add(fileTreeGrid);
    filesPanelContainer.setCenterWidget(filesGridContentPanel);
    filesPanelContainer.setSouthWidget(filesToolBar, new BorderLayoutContainer.BorderLayoutData(30));

    testGridContentPanel.setHeaderVisible(false);
    testGridContentPanel.add(lectureTestDataGrid);
    testsPanelContainer.setCenterWidget(testGridContentPanel);
    testsPanelContainer.setSouthWidget(testToolBar, new BorderLayoutContainer.BorderLayoutData(30));

    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(.30);
    layoutData.setSplit(true);
    layoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setWestWidget(gridContainer, layoutData);

    setGridState(state);
    setTreeState(ManageLecturesFilesController.LectureTreeViewState.NONE);
    setFilesAndTestsState(ManageLecturesController.LecturesFilesAndTestsState.FILES);
  }

  @Override
  public void setGridState(ManageLecturesController.LectureGridViewState state) {
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
      createHtmlFile.setEnabled(false);
      createFolderButton.setEnabled(false);
      break;
    case NONE:
      addButton.setEnabled(false);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      createHtmlFile.setEnabled(false);
      clearFields();
      break;
    }
  }

  @Override
  public void setTreeState(ManageLecturesFilesController.LectureTreeViewState state) {
    switch (state) {
    case FOLDER_SELECTED:
      createHtmlFile.setEnabled(true);
      createFolderButton.setEnabled(true);
      deleteFileButton.setEnabled(true);
      editFileButton.setEnabled(false);
      downloadFileButton.setEnabled(false);
      uploadFileButton.setEnabled(true);
      fileUpload.setEnabled(true);
      break;
    case FILE_SELECTED:
      createHtmlFile.setEnabled(false);
      createFolderButton.setEnabled(false);
      deleteFileButton.setEnabled(true);
      editFileButton.setEnabled(true);
      downloadFileButton.setEnabled(true);
      uploadFileButton.setEnabled(false);
      fileUpload.setEnabled(false);
      fileUpload.getElement().setPropertyString("value", "");
      break;
    case NONE:
      createHtmlFile.setEnabled(false);
      createFolderButton.setEnabled(false);
      deleteFileButton.setEnabled(false);
      editFileButton.setEnabled(false);
      downloadFileButton.setEnabled(false);
      uploadFileButton.setEnabled(false);
      fileUpload.setEnabled(false);
      fileUpload.getElement().setPropertyString("value", "");
      break;
    }
  }

  public void setFilesAndTestsState(ManageLecturesController.LecturesFilesAndTestsState state) {
    if (state == ManageLecturesController.LecturesFilesAndTestsState.FILES) {
      mainContainer.setCenterWidget(filesPanelContainer);
    } else if (state == ManageLecturesController.LecturesFilesAndTestsState.TESTS) {
      mainContainer.setCenterWidget(testsPanelContainer);
    }
    mainContainer.forceLayout();
  }


  public void clearFields() {
    lectureNameField.setText("");
  }

  public TextButton getAddButton() {
    return this.addButton;
  }

  public TextButton getEditButton() {
    return this.editButton;
  }

  public TextButton getDeleteButton() {
    return this.deleteButton;
  }

  public TextButton getCreateHtmlButton() {
    return createHtmlFile;
  }

  public TextButton getEditHtmlButton() {
    return editFileButton;
  }

  public TextButton getDeleteHtmlButton() {
    return deleteFileButton;
  }

  public TextButton getCreateFolderButton() {
    return createFolderButton;
  }

  public TextButton getFileDowloadButton() {
    return downloadFileButton;
  }

  public TextButton getFileUploadButton() {
    return uploadFileButton;
  }

  public FormPanel getFileFormPanel() {
    return fileFormPanel;
  }

  public FileUpload getFileUpload() {
    return fileUpload;
  }

  public TextField getLectureNameField() {
    return this.lectureNameField;
  }

  public RadioButton getFilesRadioButton() {
    return filesRadioButton;
  }

  public RadioButton getTestsRadioButton() {
    return testsRadioButton;
  }

  public ComboBox<Professor> getProfessorComboBox() {
    return this.professorComboBox;
  }

  public Grid<Lecture> getGrid() {
    return this.lectureGridView;
  }

  public TreeGrid<FileData> getTreeGrid() {
    return fileTreeGrid;
  }

  public void loadLectures(Lecture lecture) {
    lectureNameField.setText(lecture.getLectureName());
  }

  public void mask() {
    mainContainer.mask();
  }

  public void unMask() {
    mainContainer.unmask();
  }

  public Widget asWidget() {
    return this.mainContainer;
  }
}
