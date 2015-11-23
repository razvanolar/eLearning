package com.google.gwt.sample.elearning.client.eLearningUtils;

import com.google.gwt.sample.elearning.client.ELearningApp;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.logging.Logger;

/***
 * Created by razvanolar on 22.11.2015.
 */
public class HtmlEditorView implements HtmlEditorController.IHtmlEditorView {

  private HtmlEditor editor;
  private BorderLayoutContainer mainContainer;
  private TextField titleField;
  private TextButton saveButton;
  private TextButton downloadButton;

  private Logger log = Logger.getLogger(HtmlEditorView.class.getName());

  public HtmlEditorView() {
    initGUI();
  }

  private void initGUI() {
    editor = new HtmlEditor();
    mainContainer = new BorderLayoutContainer();
    titleField = new TextField();
    saveButton = new TextButton("", ELearningController.ICONS.save());
    downloadButton = new TextButton("", ELearningController.ICONS.download());
    ToolBar toolBar = new ToolBar();

    toolBar.add(saveButton);
    toolBar.add(downloadButton);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(new Label("Title : "));
    toolBar.add(titleField);

    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(25));
    mainContainer.setCenterWidget(editor);
  }

  @Override
  public TextField getTitleField() {
    return titleField;
  }

  @Override
  public TextButton getSaveButton() {
    return saveButton;
  }

  @Override
  public TextButton getDownloadButton() {
    return downloadButton;
  }

  @Override
  public String getEditorValue() {
    return editor.getValue();
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
