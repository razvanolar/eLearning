package com.google.gwt.sample.elearning.server.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
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
      }

//      List<FileItem> items = upload.parseRequest(req);
//      Iterator<FileItem> iterator = items.iterator();
//      while (iterator.hasNext()) {
//        FileItem item = iterator.next();
//        if (item.isFormField()) {
//          System.out.println();
//        } else {
//          byte[] data = item.get();
//          InputStream in = item.getInputStream();
//          byte []buff = new byte[1024];
//          int readSize = 0;
//          while ((readSize = in.read(buff, 0, 1024)) > 0) {
//            String rez = new String(buff, 0, readSize);
//            // TODO: Add string concatenations here
//            System.out.println(rez);
//          }
//          in.close();
//        }
//      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
