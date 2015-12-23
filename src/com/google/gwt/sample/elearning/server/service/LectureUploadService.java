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
    try {
      long lectureId = Long.parseLong(lecture);
      path = path != null && !path.isEmpty() ? path + "\\" : path;
      String filePath = ServerUtil.getLectureFilesDirectoryPath(lectureId) + path;
      File dirs = new File(filePath);
      if (!dirs.exists() && !dirs.mkdirs())
        throw new Exception("Unable to create missing directories");
      List<FileItem> items = upload.parseRequest(req);
      Iterator<FileItem> iterator = items.iterator();
      while (iterator.hasNext()) {
        FileItem item = iterator.next();
        if (item.isFormField()) {
          System.out.println();
        } else {
          String fieldName = item.getFieldName();
//          String fileName = item.getName();
//          if (fieldName != null)
//            fileName = FilenameUtils.getName(fileName);
//          String contentType = item.getContentType();
//          boolean isInMemory = item.isInMemory();
//          long sizeInBytes = item.getSize();
          byte[] data = item.get();
          FileOutputStream fileOutSt = new FileOutputStream(filePath + item.getName());
          fileOutSt.write(data);
          fileOutSt.close();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {

    }
  }
}
