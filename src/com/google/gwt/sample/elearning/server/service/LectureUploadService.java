package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.server.ServerUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by razvanolar on 28.11.2015.
 */
public class LectureUploadService extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    String lecture = req.getParameter("lecture");
    String path = req.getParameter("path");
    ElearningLogger.log("Uploading lecture " + lecture + " with path: " + path);
    try {
      long lectureId = Long.parseLong(lecture);
      path = path != null && !path.isEmpty() ? path + "\\" : path;
      String fileDirPath = ServerUtil.getLectureFilesDirectoryPath(lectureId) + path;
      String fileProjPath = ServerUtil.getLecturesFileProjectPath(lectureId) + path;
      File dirs = new File(fileDirPath);
      File dirsProj = new File(fileProjPath);
      if (!dirs.exists() && !dirs.mkdirs())
        throw new Exception("Unable to create missing directories");
      if (!dirsProj.exists() && !dirsProj.mkdirs())
        throw new Exception("Unable to create missing directories");
      List<FileItem> items = upload.parseRequest(req);
      Iterator<FileItem> iterator = items.iterator();
      while (iterator.hasNext()) {
        FileItem item = iterator.next();
        if (item.isFormField()) {
          System.out.println();
        } else {
          byte[] data = item.get();
          FileOutputStream fileOutStDir = new FileOutputStream(fileDirPath + item.getName());
          FileOutputStream fileOutStProj = new FileOutputStream(fileProjPath + item.getName());
          fileOutStDir.write(data);
          fileOutStProj.write(data);
          fileOutStDir.close();
          fileOutStProj.close();

          if (item.getName().endsWith(".xml")) {
            saveAdditionalXmlFile(fileDirPath, item.getName(), data);
            saveAdditionalXmlFile(fileProjPath, item.getName(), data);
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {

    }
  }

  private void saveAdditionalXmlFile(String dirPath, String fileName, byte[] data) {
    try {
      String text = new String(data);
      text = text.replace("<", "&lt;");
      text = text.replace(">", "&gt;");

      fileName += "_";
      PrintWriter writer = new PrintWriter(new File(dirPath + fileName));
      writer.write(text);
      writer.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
