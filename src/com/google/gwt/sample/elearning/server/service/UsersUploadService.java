package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.server.repository.JDBC.JdbcUserDAO;
import com.google.gwt.sample.elearning.server.service.collector.user.UserXMLHandler;
import com.google.gwt.sample.elearning.shared.model.UserData;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * Created by razvanolar on 23.12.2015.
 */
public class UsersUploadService extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload();
    try {
      FileItemIterator iter = upload.getItemIterator(req);
      if(iter != null && iter.hasNext()) {
        FileItemStream fileStream = iter.next();
        InputStream in = fileStream.openStream();
        BufferedInputStream bis = new BufferedInputStream(in, 32768);
        byte[] buffer = new byte[32768];

        int bytes;
        String rez = "";
        while ((bytes = bis.read(buffer, 0, 32768)) >= 0) {
          String s = new String(buffer, 0, bytes);
          rez += s;
        }
        System.out.println(rez);

        UserXMLHandler userXMLHandler = new UserXMLHandler(rez);
        List<UserData> users = userXMLHandler.parse();
        JdbcUserDAO jdbcUserDAO = new JdbcUserDAO();
        for(UserData userData : users){
          jdbcUserDAO.insertUser(userData);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
