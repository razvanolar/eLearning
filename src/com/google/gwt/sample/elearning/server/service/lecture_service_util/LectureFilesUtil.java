package com.google.gwt.sample.elearning.server.service.lecture_service_util;

import com.google.gwt.sample.elearning.server.ServerUtil;
import com.google.gwt.sample.elearning.shared.Node;
import com.google.gwt.sample.elearning.shared.Tree;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.FileData;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.FileTypes;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

/***
 * Created by razvanolar on 29.11.2015.
 */
public class LectureFilesUtil {

  public String addLectureHtmlFile(String path, String title, long lectureId, String text) throws ELearningException {
    PrintWriter writer = null;
    try {
      title = title + ".html";
      path = path != null && !path.isEmpty() ? path + "\\" : path;
      path = ServerUtil.getUserLectureDirectoryPath(lectureId) + path;
      File file = new File(path);
      if (!file.exists() && !file.mkdirs())
        throw new Exception("Unable to create missing directories");
      writer = new PrintWriter(path + title, "UTF-8");
      writer.println(ServerUtil.getHtmlDocumentText(title, text));
      return title;
    } catch (Exception e) {
      e.printStackTrace();
      throw new ELearningException(e);
    } finally {
      if (writer != null)
        writer.close();
    }
  }

  public void createFolder(UserData user, String path, String name, long lectureId) throws ELearningException {
    String lectureDirectoryPath = ServerUtil.getUserLectureDirectoryPath(lectureId);
    File file = new File(lectureDirectoryPath + path + "\\" + name);
    if (!file.exists() && !file.mkdirs())
      throw new ELearningException("Folder " + name + " can not be created");
  }

  public Tree<FileData> getLectureFilesTree(UserData user, long lectureId) throws ELearningException {
    String lectureDirectoryPath = ServerUtil.getUserLectureDirectoryPath(lectureId);
    File rootDir = new File(lectureDirectoryPath);

    Tree<FileData> tree = new Tree<FileData>();
    File []filesList = rootDir.listFiles();
    if (filesList == null)
      return tree;

    Queue<Node<FileData>> queue = new LinkedList<Node<FileData>>();
    List<Node<FileData>> roots = createFileNodesFromFiles(filesList);
    queue.addAll(roots);

    while (!queue.isEmpty()) {
      Node<FileData> node = queue.poll();
      if (node.getValue().getType() == FileTypes.FOLDER) {
        File[] files = getFileContents(node.getValue().getPath());
        List<Node<FileData>> children = createFileNodesFromFiles(files);
        node.addChildren(children);
        queue.addAll(children);
      }
    }

    tree.addRoots(roots);
    return tree;
  }

  public String getHtmlFileBodyContent(UserData userData, long lectureId, String path, String title) throws ELearningException {
    String lecturesDirectoryPath = ServerUtil.getUserLectureDirectoryPath(lectureId);
    String filePath = lecturesDirectoryPath + path + title;

    File file = new File(filePath);

    if (!file.exists())
      throw new ELearningException("Specified path does not exists. Path: " + path);
    if (!file.getName().endsWith(".html"))
      throw new ELearningException(file.getName() + " is not a html file. Path: " + path);

    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(file));
      StringBuilder result = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        result.append(line);
      }
      int bodyIndex = result.indexOf("<body>");
      int endBodyIndex = result.indexOf("</body>");
      if (bodyIndex == -1)
        throw new ELearningException("<body> tag can not be found. Html file is corrupted");
      if (endBodyIndex == -1)
        throw new ELearningException("</body> tag can not be found. Html file is corrupted");
      bodyIndex += 6;
      return result.substring(bodyIndex, endBodyIndex);
    } catch (FileNotFoundException ex) {
      throw new ELearningException("Specified path does not exists. Path: " + path, ex);
    } catch (IOException ex) {
      throw new ELearningException("An error occurred while reading the file. Path: " + path, ex);
    }
  }

  public void deleteFile(long lectureId, String path, String title) throws ELearningException {
    String filePath = ServerUtil.getUserLectureDirectoryPath(lectureId) + path + title;
    File file = new File(filePath);
    if (!file.exists())
      throw new ELearningException("Specified file does not exists. Path: " + path + title);

    try {
      if (!file.isDirectory())
        FileUtils.forceDelete(file);
      else
        FileUtils.deleteDirectory(file);
    } catch (IOException e) {
      throw new ELearningException(title + " can not be deleted. Error message: " + e.getMessage());
    }
  }


  private List<Node<FileData>> createFileNodesFromFiles(File[] files) {
    List<Node<FileData>> result = new ArrayList<Node<FileData>>();
    if (files == null)
      return result;
    for (File file : files) {
      FileTypes type = file.isDirectory() ? FileTypes.FOLDER : FileTypes.REGULAR;
      result.add(new Node<FileData>(
              new FileData(file.getName(), file.getAbsolutePath(), type, file.length(), new Date(file.lastModified()))));
    }
    return result;
  }

  private File[] getFileContents(String path) {
    File file = new File(path);
    if (!file.exists())
      return null;
    if (!file.isDirectory())
      return null;

    return file.listFiles();
  }
}
