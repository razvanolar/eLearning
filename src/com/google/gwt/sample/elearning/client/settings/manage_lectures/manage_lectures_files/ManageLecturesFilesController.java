package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_files;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.HtmlEditorController;
import com.google.gwt.sample.elearning.client.eLearningUtils.HtmlEditorView;
import com.google.gwt.sample.elearning.client.eLearningUtils.IHtmlListener;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.ManageLecturesController;
import com.google.gwt.sample.elearning.shared.Node;
import com.google.gwt.sample.elearning.shared.model.FileData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.types.FileExtensionTypes;
import com.google.gwt.sample.elearning.shared.types.FileTypes;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.box.PromptMessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 28.11.2015.
 */
public class ManageLecturesFilesController implements IHtmlListener {

  public enum LectureTreeViewState {
    FOLDER_SELECTED, FILE_SELECTED, NONE
  }

  private ManageLecturesController.IManageLecturesView view;
  private TreeStore<FileData> treeStore;
  private HtmlEditorController htmlEditorContr;
  private LectureServiceAsync lectureService;

  private Lecture currentLecture;
  private static FileData defaultTreeRoot = new FileData("root", "", FileTypes.FOLDER, -1, null);

  private static Logger log = Logger.getLogger(ManageLecturesFilesController.class.getName());

  public ManageLecturesFilesController(ManageLecturesController.IManageLecturesView view, LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void bind() {
    treeStore = view.getTreeGrid().getTreeStore();
    addListeners();
  }

  private void addListeners() {
    view.getCreateHtmlButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onHtmlCreateSelection();
      }
    });

    view.getEditHtmlButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onHtmlEditSelection();
      }
    });

    view.getCreateFolderButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onCreateFolderSelect();
      }
    });

    view.getDeleteHtmlButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onDeleteFileSelection();
      }
    });

    view.getFileDowloadButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onDownloadFileSelection();
      }
    });

    view.getFileUploadButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        FileData selectedFile = getSelectedFile();
        if (selectedFile == null || selectedFile.getType() != FileTypes.FOLDER || currentLecture == null)
          return;
        view.getFileFormPanel().setAction(GWT.getModuleBaseURL() + "lectureUploadService?user=" +
              currentLecture.getProfessor().getId() + "&lecture=" + currentLecture.getId() +
              "&path=" + getFilePathWithName(selectedFile));
        view.getFileFormPanel().submit();
      }
    });

    view.getFileFormPanel().addSubmitHandler(new FormPanel.SubmitHandler() {
      @Override
      public void onSubmit(FormPanel.SubmitEvent event) {
        String fileName = view.getFileUpload().getFilename();
        log.info("fileName: " + fileName);
        if (fileName == null || fileName.isEmpty()) {
          (new MessageBox("Warning", "Please select a file!")).show();
          event.cancel();
        } else if (FileExtensionTypes.getFileExtensionByValue(getFileExtentsion(fileName)) != null) {
          log.info("Uploading file");
        } else {
          (new MessageBox("Warning", "Only html or pdf files can be uploaded!")).show();
          event.cancel();
        }
      }
    });

    view.getFileFormPanel().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      @Override
      public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        log.info("File was uploaded");
        loadFileTree();
      }
    });

    view.getTreeGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<FileData>() {
      public void onSelectionChanged(SelectionChangedEvent<FileData> event) {
        onTreeSelection(event);
      }
    });
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

  private void onHtmlCreateSelection() {
    FileData selectedFile = getSelectedFile();
    if (selectedFile == null || selectedFile.getType() != FileTypes.FOLDER)
      return;
    String path = getFilePath(selectedFile, true);
    HtmlEditorController.IHtmlEditorView editorView = new HtmlEditorView();
    htmlEditorContr = new HtmlEditorController(editorView, path, this);
    htmlEditorContr.bind();
    MasterWindow window = new MasterWindow();
    window.setContent(editorView.asWidget(), "Html Editor");
    window.setModal(true);
    window.setPixelSize(600, 350);
    window.show();
  }

  private void onHtmlEditSelection() {
    final FileData selectedFile = getSelectedFile();
    if (selectedFile == null || !selectedFile.getName().endsWith(".html"))
      return;
    final String path = getFilePathWithoutName(selectedFile);

    lectureService.getHtmlFileBodyContent(currentLecture.getProfessor(), currentLecture.getId(), path, selectedFile.getName(), new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        (new MessageBox("Error", caught.getMessage())).show();
      }

      public void onSuccess(String result) {
        HtmlEditorController.IHtmlEditorView htmlEditorView = new HtmlEditorView();
        htmlEditorContr = new HtmlEditorController(htmlEditorView, path, ManageLecturesFilesController.this, result, selectedFile.getName());
        htmlEditorContr.bind();
        MasterWindow window = new MasterWindow();
        window.setModal(true);
        window.setContent(htmlEditorView.asWidget(), "Html Editor");
        window.setPixelSize(600, 350);
        window.show();
      }
    });
  }

  private void onDeleteFileSelection() {
    FileData selectedFile = getSelectedFile();
    if (selectedFile == null || currentLecture == null)
      return;
    lectureService.deleteFile(currentLecture.getId(),
            getFilePathWithoutName(selectedFile), selectedFile.getName(), new AsyncCallback<Void>() {
              public void onFailure(Throwable caught) {
                (new MessageBox("Error", caught.getMessage())).show();
              }

              public void onSuccess(Void result) {
                (new MessageBox("Info", "File was deleted successfully!")).show();
                loadFileTree();
              }
            });
  }

  private void onDownloadFileSelection() {
    FileData selectedFile = getSelectedFile();
    if (selectedFile == null || currentLecture == null)
      return;
    if (selectedFile.getType() == FileTypes.REGULAR)
      downloadFile(selectedFile, currentLecture);
  }

  private void downloadFile(FileData selectedFile, Lecture lecture) {
    String url = GWT.getModuleBaseURL();
    url += "lectureDownloadService?path=" + getFilePathWithoutName(selectedFile) + "&name=" + selectedFile.getName() +
            "&user=" + lecture.getProfessor().getId() + "&lecture=" + lecture.getId();
    Window.open(url, "_self", "");
  }

  @Override
  public void createHtmlFile(String path, String title, String text) {
    if (currentLecture == null)
      return;
    lectureService.addLectureHtmlFile(path, title, currentLecture.getId(), text, new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        htmlEditorContr.setSaveState(HtmlEditorController.SaveStatus.FAILED);
      }

      public void onSuccess(String result) {
        htmlEditorContr.setSaveState(HtmlEditorController.SaveStatus.SAVED);
      }
    });
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

  private void createNewFolder(final String name) {
    if (currentLecture == null)
      return;
    FileData selectedFile = getSelectedFile();
    if (selectedFile == null)
      return;

    String path = getFilePath(selectedFile, true);
    if (path == null)
      return;

    lectureService.createFolder(currentLecture.getProfessor(), path, name, currentLecture.getId(), new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        (new MessageBox("Error", caught.getMessage())).show();
      }

      public void onSuccess(Void result) {
        (new MessageBox("Info", "Folder " + name + " was created")).show();
        loadFileTree();
      }
    });
  }

  public void loadFileTree() {
    if (currentLecture == null)
      return;
    view.mask();
    lectureService.getLectureFilesTree(currentLecture.getProfessor(), currentLecture.getId(), new AsyncCallback<com.google.gwt.sample.elearning.shared.Tree<FileData>>() {
      public void onFailure(Throwable caught) {
        view.unmask();
        (new MessageBox("Error", caught.getMessage())).show();
      }

      public void onSuccess(com.google.gwt.sample.elearning.shared.Tree<FileData> result) {
        log.info("onSuccess");
        loadTreeStore(result);
        view.unmask();
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

  private String getFilePath(FileData fileData, boolean isCreateAction) {
    List<FileData> roots = treeStore.getRootItems();
    /* if it is the root item  */
    if (roots.contains(fileData))
      return "";
    /* if it's a regular file, we can not add any child for it */
    if (isCreateAction && (fileData.getType() == null || fileData.getType() == FileTypes.REGULAR))
      return null;

    String path = fileData.getName();
    FileData parent = treeStore.getParent(fileData);
    while (parent != null && !(roots.contains(parent))) {
      log.info("---path = " + path);
      path = parent.getName() + "\\" + path;
      parent = treeStore.getParent(parent);
    }

    return path;
  }

  private String getFilePathWithoutName(FileData fileData) {
    List<FileData> roots = treeStore.getRootItems();
    if (roots.contains(fileData))
      return "";

    String path = "";
    FileData parent = treeStore.getParent(fileData);
    while (parent != null && !(roots.contains(parent))) {
      path = parent.getName() + "\\" + path;
      parent = treeStore.getParent(parent);
    }
    return path;
  }

  private String getFilePathWithName(FileData fileData) {
    List<FileData> roots = treeStore.getRootItems();
    if (roots.contains(fileData))
      return "";

    String path = fileData.getName();
    FileData parent = treeStore.getParent(fileData);
    while (parent != null && !(roots.contains(parent))) {
      path = parent.getName() + "\\" + path;
      parent = treeStore.getParent(parent);
    }
    return path;
  }

  private FileData getSelectedFile() {
    if (view.getTreeGrid() != null && view.getTreeGrid().getSelectionModel() != null)
      return view.getTreeGrid().getSelectionModel().getSelectedItem();
    return null;
  }

  private String getFileExtentsion(String fileName) {
    StringBuilder builder = new StringBuilder(fileName);
    int index = builder.lastIndexOf(".");
    if (index == -1)
      return "";
    return builder.substring(index);
  }

  public void setSelectedLecture(Lecture lecture) {
    currentLecture = lecture;
  }

  public void clearStore() {
    treeStore.clear();
  }
}
