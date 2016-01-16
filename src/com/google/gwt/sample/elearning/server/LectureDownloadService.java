package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.server.service.ElearningLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * Created by razvanolar on 29.11.2015.
 */
public class LectureDownloadService extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String lecturePath = req.getParameter("path");
    String fileName = req.getParameter("name");
    String lecture = req.getParameter("lecture");
    ElearningLogger.log("Downloading from lecture:" + lecture + " file name: " + fileName);
    try {
      long lectureId = Long.parseLong(lecture);

      OutputStream outputStream = (OutputStream) resp.getOutputStream();
      FileInputStream inputStream = new FileInputStream(ServerUtil.getLectureFilesDirectoryPath(lectureId) + lecturePath + fileName);

      int bytes, sum=0;
      byte[] buffer = new byte[255];
      resp.setContentType(ServerUtil.getFileContentType(fileName));
      resp.setHeader("Content-Disposition:", "attachment;filename=" + "\"" + fileName + "\"");
      resp.setHeader("Connection:", "Close");
      int BUFFER = 1024 * 100;
      resp.setBufferSize(BUFFER);

      while ((bytes = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytes);
        sum += bytes;
      }
      inputStream.close();
      outputStream.close();

      resp.setContentLength(sum);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
