package com.google.gwt.sample.elearning.client.eLearningUtils;

import com.google.gwt.user.client.ui.Widget;

/***
 * Created by razvanolar on 22.11.2015.
 */
public class HtmlEditorController {

  public interface IHtmlEditorView {

    Widget asWidget();
  }

  private IHtmlEditorView view;

  public HtmlEditorController(IHtmlEditorView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {

  }
}
