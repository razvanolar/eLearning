package com.google.gwt.sample.elearning.client.icons;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/***
 * Created by razvanolar on 14.11.2015.
 */
public interface Icons extends ClientBundle {

  @ClientBundle.Source("logo.png")
  ImageResource logo();

  @ClientBundle.Source("profile.png")
  ImageResource profile();

  @ClientBundle.Source("logout.png")
  ImageResource logout();

  @Source("forum.png")
  ImageResource forum();

  @Source("chat.png")
  ImageResource chat();

  @Source("settings.png")
  ImageResource settings();

  @Source("add.gif")
  ImageResource add();

  @Source("delete.gif")
  ImageResource delete();

  @Source("edit.png")
  ImageResource edit();

  @Source("save.png")
  ImageResource save();

  @Source("download.png")
  ImageResource download();

  @Source("newfile.png")
  ImageResource newfile();

  @Source("newfolder.png")
  ImageResource newfolder();

  @Source("refresh.png")
  ImageResource refresh();

  @Source("deletefile.png")
  ImageResource deletefile();

  @Source("editfile.png")
  ImageResource editfile();

  @Source("upload.png")
  ImageResource upload();

  @Source("succes.png")
  ImageResource succes();

  @Source("fail.png")
  ImageResource fail();

  @Source("apply.png")
  ImageResource apply();

  @Source("arrowleft.png")
  ImageResource arrowleft();

  @Source("arrowright.png")
  ImageResource arrowright();

  @Source("addquestion.png")
  ImageResource addquestion();

  @Source("deletequestion.png")
  ImageResource deletequestion();

  @Source("play.png")
  ImageResource play();

  @Source("lectures.png")
  ImageResource lectures();

  @Source("send.png")
  ImageResource send();

  @Source("mail.png")
  ImageResource mail();

  @Source("solve.png")
  ImageResource solve();
}
