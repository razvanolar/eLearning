package com.google.gwt.sample.elearning.client.eLearningUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.logging.Logger;

/***
 * Created by razvanolar on 22.11.2015.
 */
public class HtmlEditorController {

  public interface IHtmlEditorView {
    TextButton getSaveButton();
    TextButton getDownloadButton();
    TextField getTitleField();
    String getEditorValue();
    Widget asWidget();
  }

  private IHtmlEditorView view;
  private LectureServiceAsync lectureService;
  private String fileName;
  private Logger log = Logger.getLogger(HtmlEditorController.class.getName());

  public HtmlEditorController(IHtmlEditorView view, LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {
    view.getSaveButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onSaveSelect();
      }
    });

    view.getDownloadButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onDownloadSelect();
      }
    });
  }

  private void onSaveSelect() {
    String title = view.getTitleField().getText();
    title = title == null || title.isEmpty() ? "Untitled.html" : title.replace(".html", "");
    String text = view.getEditorValue();
    final String finalTitle = title;
    lectureService.addLectureFile(title, text, new AsyncCallback<String>() {
      public void onFailure(Throwable caught) {
        log.severe(caught.getMessage());
      }

      public void onSuccess(String result) {
        (new MessageBox("Info", finalTitle + " was saved!")).show();
        fileName = result;
      }
    });
  }

  private void onDownloadSelect() {
    if (fileName == null)
      return;
    String url = GWT.getModuleBaseURL() + "lectureDownloadService?fileName=" + fileName;
    Window.open(url, "", "");
  }
}
