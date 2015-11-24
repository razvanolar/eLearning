package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.HtmlEditorController;
import com.google.gwt.sample.elearning.client.eLearningUtils.HtmlEditorView;
import com.google.gwt.sample.elearning.client.eLearningUtils.IHtmlListener;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.client.service.*;
import com.google.gwt.sample.elearning.client.settings.ISettingsController;
import com.google.gwt.sample.elearning.shared.Node;
import com.google.gwt.sample.elearning.shared.model.FileData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.FileTypes;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.box.PromptMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/***
 * Created by Cristi on 11/17/2015.
 */
public class ManageLecturesController implements ISettingsController, IHtmlListener {

  private List<Professor> professorList;
  private List<Lecture> lectureList;
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);
  private UserServiceAsync userServiceAsync = GWT.create(UserService.class);

  public enum LectureGridViewState {
    ADD, EDIT, ADDING, NONE
  }

  public enum LectureTreeViewState {
    FOLDER_SELECTED, FILE_SELECTED, NONE
  }

  public interface IManageLecturesView {
    TextButton getAddButton();
    TextButton getEditButton();
    TextButton getDeleteButton();
    TextButton getCreateHtmlButton();
    TextButton getCreateFolderButton();
    TextField getLectureNameField();
    ComboBox<Professor> getProfessorComboBox();
    Grid<Lecture> getGrid();
    TreeGrid<FileData> getTreeGrid();
    void setGridState(LectureGridViewState state);
    void setTreeState(LectureTreeViewState state);
    void loadLectures(Lecture userData);
    void clearFields();
    void mask();
    void unMask();
    Widget asWidget();
  }

  private IManageLecturesView view;
  private TreeStore<FileData> treeStore;
  private static FileData defaultTreeRoot = new FileData("root", "", FileTypes.FOLDER, -1, null);

  public ManageLecturesController(IManageLecturesView view) {
    this.view = view;
    treeStore = view.getTreeGrid().getTreeStore();
  }

  public void bind() {
    lectureList = new ArrayList<Lecture>();
    professorList = new ArrayList<Professor>();
    addListeners();
    populateGrid();
    populateCombo();
  }

  @Override
  public void loadResources() {
    ListStore<Lecture> lecturesStore = view.getGrid().getStore();
    if(lecturesStore == null || lecturesStore.getAll().isEmpty())
      populateGrid();
    ListStore<Professor> professorStore = view.getProfessorComboBox().getStore();
    if(professorStore == null || professorStore.getAll().isEmpty())
      populateCombo();
  }

  private void populateGrid() {
    view.mask();
    lectureService.getAllLectures(new AsyncCallback<List<Lecture>>() {
      public void onFailure(Throwable caught) {
        view.unMask();
        new MessageBox("", "Could not get Lectures").show();
      }

      public void onSuccess(List<Lecture> result) {
        view.getGrid().getStore().clear();
        lectureList.clear();
        lectureList.addAll(result);
        view.getGrid().getStore().addAll(lectureList);
        view.unMask();
      }
    });
  }
  private void populateCombo() {
    view.mask();
    userServiceAsync.getAllUsersByRole(UserRoleTypes.PROFESSOR, new AsyncCallback<List<? extends UserData>>() {
      public void onFailure(Throwable caught) {
        view.unMask();
        new MessageBox("", "Cold not get Professors").show();
      }

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
        view.unMask();
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
    view.getTreeGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<FileData>() {
      public void onSelectionChanged(SelectionChangedEvent<FileData> event) {
        onTreeSelection(event);
      }
    });

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
      @Override
      public void onSelection(SelectionEvent<Professor> event) {
        long selectedProfessordId = event.getSelectedItem().getId();
        onComboProfessorSelection(selectedProfessordId);
      }
    });

    view.getCreateHtmlButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onHtmlEditorSelection();
      }
    });

    view.getAddButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
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
      @Override
      public void onSelect(SelectEvent event) {
        onEditButtonSelection();
      }
    });

    view.getCreateFolderButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onCreateFolderSelect();
      }
    });
  }

  private void onHtmlEditorSelection() {
    HtmlEditorController.IHtmlEditorView editorView = new HtmlEditorView();
    HtmlEditorController controller = new HtmlEditorController(editorView, this);
    controller.bind();
    MasterWindow window = new MasterWindow();
    window.setContent(editorView.asWidget(), "Html Editor");
    window.setModal(true);
    window.setPixelSize(500, 350);
    window.show();
  }

  Logger log = Logger.getLogger(ManageLecturesController.class.getName());

  private void onAddButtonSelection() {
    log.info("onAdd");
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
        .removeLecture(view.getGrid().getSelectionModel().getSelectedItem().getId(), new AsyncCallback<Void>() {
          @Override
          public void onFailure(Throwable caught) {
            new MessageBox("", "Could not remove Lecture").show();
          }

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
    lectureService.updateLecture(lecture, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        new MessageBox("", "Could not update Lecture").show();
      }

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

  private void onCreateFolderSelect() {
    final PromptMessageBox messageBox = new PromptMessageBox("Name", "Enter the folder name:");
    messageBox.addHideHandler(new HideEvent.HideHandler() {
      public void onHide(HideEvent event) {
        String fileName = messageBox.getValue();
        if (fileName != null && !fileName.isEmpty()) {
          createNewFolder(fileName);
        }
      }
    });
    messageBox.show();
  }

  @Override
  public void createHtmlFile(String title, String text) {
    Lecture selectedLecture = getSelectedLecture();
    FileData selectedFile = getSelectedFile();
    if (selectedLecture == null || selectedFile == null)
      return;
    lectureService
        .addLectureHtmlFile(selectedLecture.getProfessor(), selectedFile.getPath(), title, selectedLecture.getId(),
            text, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            (new MessageBox("Error", caught.getMessage())).show();
          }

          public void onSuccess(String result) {
            (new MessageBox("Info", "File was created")).show();
          }
        });
  }

  private void createNewFolder(final String name) {
    Lecture selectedLecture = getSelectedLecture();
    if (selectedLecture == null)
      return;
    FileData selectedFile = getSelectedFile();
    if (selectedFile == null)
      return;

    String path = getFilePath(selectedFile);
    if (path == null)
      return;

    lectureService
        .createFolder(selectedLecture.getProfessor(), path, name, selectedLecture.getId(), new AsyncCallback<Void>() {
          public void onFailure(Throwable caught) {
            (new MessageBox("Error", caught.getMessage())).show();
          }

          public void onSuccess(Void result) {
            (new MessageBox("Info", "Folder " + name + " was created")).show();
            loadFileTree();
          }
        });

  }

  private void loadFileTree() {
    Lecture selectedLecture = getSelectedLecture();
    if (selectedLecture == null)
      return;
    view.mask();
    lectureService.getLectureFilesTree(selectedLecture.getProfessor(), selectedLecture.getId(),
        new AsyncCallback<com.google.gwt.sample.elearning.shared.Tree<FileData>>() {
          public void onFailure(Throwable caught) {
            view.unMask();
            (new MessageBox("Error", caught.getMessage())).show();
          }

          public void onSuccess(com.google.gwt.sample.elearning.shared.Tree<FileData> result) {
            loadTreeStore(result);
            view.unMask();
          }
        });
  }

  private void loadTreeStore(com.google.gwt.sample.elearning.shared.Tree<FileData> tree) {
    treeStore.clear();
    treeStore.add(defaultTreeRoot);
    List<Node<FileData>> roots = tree.getRoots();
    for (Node<FileData> node : roots) {
      FileData root = node.getValue();
      treeStore.add(defaultTreeRoot, root);
      addStoreChildrenHierarchy(root, node.getChildren());
    }

    for (FileData root : treeStore.getRootItems())
      view.getTreeGrid().setExpanded(root, true, true);
  }

  private void addStoreChildrenHierarchy(FileData parent, List<Node<FileData>> children) {
    if (children == null || children.isEmpty())
      return;
    for (Node<FileData> node : children) {
      FileData n = node.getValue();
      treeStore.add(parent, n);
      addStoreChildrenHierarchy(n, node.getChildren());
    }
  }

  private String getFilePath(FileData fileData) {
    List<FileData> roots = treeStore.getRootItems();
    /* if it is the root item  */
    if (roots.contains(fileData))
      return "";
    /* if it's a regular file, we can not add any child for it */
    if (fileData.getType() == null || fileData.getType() == FileTypes.REGULAR)
      return null;

    String path = fileData.getName();
    FileData parent = treeStore.getParent(fileData);
    while (parent != null && !(roots.contains(parent))) {
      path = parent.getName() + "\\" + path;
    }

    return path;
  }

  private Lecture getSelectedLecture() {
    if (view.getGrid() != null && view.getGrid().getSelectionModel() != null) {
      return view.getGrid().getSelectionModel().getSelectedItem();
    }
    return null;
  }

  private FileData getSelectedFile() {
    if (view.getTreeGrid() != null && view.getTreeGrid().getSelectionModel() != null)
      return view.getTreeGrid().getSelectionModel().getSelectedItem();
    return null;
  }

  private boolean isLectureSelected() {
    return getSelectedLecture() != null;
  }

  private void onGridSelection(SelectionChangedEvent<Lecture> event) {
    List<Lecture> selection = event.getSelection();
    if (selection == null || selection.isEmpty()) {
      view.setGridState(LectureGridViewState.NONE);
      treeStore.clear();
    } else {
      view.setGridState(LectureGridViewState.EDIT);
      view.loadLectures(selection.get(0));
      loadFileTree();
    }
  }

  private void onTreeSelection(SelectionChangedEvent<FileData> event) {
    List<FileData> selection = event.getSelection();
    if (selection == null || selection.isEmpty()) {
      view.setTreeState(LectureTreeViewState.NONE);
    } else {
      FileData selectedFile = selection.get(0);
      if (selectedFile.getType() == FileTypes.FOLDER)
        view.setTreeState(LectureTreeViewState.FOLDER_SELECTED);
      else
        view.setTreeState(LectureTreeViewState.FILE_SELECTED);
    }
  }
}
