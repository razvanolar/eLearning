package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.server.ServerUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/***
 * Created by razvanolar on 23.11.2015.
 */
public class LectureDownloadService extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String fileName = req.getParameter("fileName");
    try {
      BufferedReader reader = new BufferedReader(new FileReader(ServerUtil.LECTURES_PATH + fileName));
      String line;
      StringBuilder rez = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        rez.append(line);
      }

      int BUFFER = 1024 * 100;
      String content = rez.toString();
      resp.setContentType("text/html");
      resp.setHeader("Content-Disposition:", "attachment;filename=" + "\"" + fileName + "\"");
      resp.setHeader("Connection:", "Close");
      resp.setBufferSize(BUFFER);
      resp.setContentLength(content.length());

      ServletOutputStream outputStream = resp.getOutputStream();
      outputStream.print(content);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }
}
