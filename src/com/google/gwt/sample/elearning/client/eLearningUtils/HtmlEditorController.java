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
    TextField getTitleField();
    String getEditorValue();
    void setEditorValue(String value);
    void setSaveStatus(SaveStatus status);
    Widget asWidget();
  }

  public enum SaveStatus {
    SAVED, FAILED
  }

  private IHtmlEditorView view;
  private IHtmlListener htmlListener;
  private String path;
  private Logger log = Logger.getLogger(HtmlEditorController.class.getName());

  public HtmlEditorController(IHtmlEditorView view, String path, IHtmlListener htmlListener) {
    this.view = view;
    this.htmlListener = htmlListener;
    this.path = path;
  }

  public HtmlEditorController(IHtmlEditorView view, String path, IHtmlListener htmlListener, String value, String title) {
    this(view, path, htmlListener);
    view.setEditorValue(value);
    view.getTitleField().setText(title);
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
  }

  private void onSaveSelect() {
    String title = view.getTitleField().getText();
    title = title == null || title.isEmpty() ? "Untitled" : title.replace(".html", "");
    String text = view.getEditorValue();
    htmlListener.createHtmlFile(path, title, text);
  }

  public void setSaveState(SaveStatus status) {
    view.setSaveStatus(status);
  }
}
