package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.server.ServerUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by razvanolar on 28.11.2015.
 */
public class LectureUploadService extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    String user = req.getParameter("user");
    String lecture = req.getParameter("lecture");
    String path = req.getParameter("path");
    try {
      long userId = Long.parseLong(user);
      long lectureId = Long.parseLong(lecture);
      String filePath = ServerUtil.getUserLectureDirectoryPath(userId, lectureId) + path + "\\";
      List<FileItem> items = upload.parseRequest(req);
      Iterator<FileItem> iterator = items.iterator();
      while (iterator.hasNext()) {
        FileItem item = iterator.next();
        if (item.isFormField()) {
          String name = item.getFieldName();
          String value = item.getString();
          System.out.println();
        } else {
          String fieldName = item.getFieldName();
          String fileName = item.getName();
          if (fieldName != null)
            fileName = FilenameUtils.getName(fileName);
          String contentType = item.getContentType();
          boolean isInMemory = item.isInMemory();
          long sizeInBytes = item.getSize();
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
