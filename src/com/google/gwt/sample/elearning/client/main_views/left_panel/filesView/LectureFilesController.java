package com.google.gwt.sample.elearning.client.main_views.left_panel.filesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.Node;
import com.google.gwt.sample.elearning.shared.Tree;
import com.google.gwt.sample.elearning.shared.model.FileData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.types.FileTypes;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 30.12.2015.
 */
public class LectureFilesController {

  public enum ILectureFilesViewState {
    SELECTED, NONE
  }

  public interface ILectureFilesView extends MaskableView {
    TextButton getDownloadButton();
    TreeGrid<FileData> getGrid();
    void setState(ILectureFilesViewState state);
  }

  private Logger log = Logger.getLogger(LectureFilesController.class.getName());
  private static FileData defaultTreeRoot = new FileData("root", "", FileTypes.FOLDER, -1, null);
  private LectureServiceAsync lectureService;
  private ILectureFilesView view;
  private TreeStore<FileData> treeStore;

  public LectureFilesController(ILectureFilesView view, LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void bind() {
    treeStore = view.getGrid().getTreeStore();
    addListeners();
  }

  private void addListeners() {
    view.getGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<FileData>() {
      public void onSelectionChanged(SelectionChangedEvent<FileData> event) {
        if (event.getSelection() == null || event.getSelection().isEmpty())
          view.setState(ILectureFilesViewState.NONE);
        else {
          if (event.getSelection().get(0).getType() == FileTypes.REGULAR)
            view.setState(ILectureFilesViewState.SELECTED);
          else
            view.setState(ILectureFilesViewState.NONE);
        }
      }
    });

    view.getDownloadButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        FileData selectedFile = getSelectedFile();
        if (selectedFile != null && selectedFile.getType() == FileTypes.REGULAR) {
          downloadFile(selectedFile, ELearningController.getInstance().getCurrentLecture());
        }
      }
    });
  }

  public void loadFiles() {
//    view.mask();
    lectureService.getLectureFilesTree(ELearningController.getInstance().getCurrentUser(),
            ELearningController.getInstance().getCurrentLecture().getId(), new ELearningAsyncCallBack<Tree<FileData>>(view, log) {
              public void onSuccess(Tree<FileData> result) {
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
      view.getGrid().setExpanded(root, true, true);
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

  private void downloadFile(FileData selectedFile, Lecture lecture) {
    String url = GWT.getModuleBaseURL();
    url += "lectureDownloadService?path=" + getFilePathWithoutName(selectedFile) + "&name=" + selectedFile.getName() +
            "&user=" + lecture.getProfessor().getId() + "&lecture=" + lecture.getId();
    Window.open(url, "_self", "");
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

  private FileData getSelectedFile() {
    List<FileData> selectedFiles = view.getGrid().getSelectionModel().getSelectedItems();
    if (selectedFiles == null || selectedFiles.isEmpty())
      return null;
    return selectedFiles.get(0);
  }
}
