package com.google.gwt.sample.elearning.client.eLearningUtils;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.logging.Logger;

/***
 * Created by razvanolar on 22.11.2015.
 */
public class HtmlEditorView implements HtmlEditorController.IHtmlEditorView {

  private HtmlEditor editor;
  private BorderLayoutContainer mainContainer;
  private Logger log = Logger.getLogger(HtmlEditorView.class.getName());

  public HtmlEditorView() {
    initGUI();
  }

  private void initGUI() {
    editor = new HtmlEditor();
    mainContainer = new BorderLayoutContainer();
    ToolBar toolBar = new ToolBar();
    TextButton btn = new TextButton("Apply");

    toolBar.add(btn);
    btn.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        log.info("--- VALUE ---");
        log.info(editor.getValue());
        log.info("--- MESSAGES ---");
        log.info(editor.getMessages().toString());
      }
    });

    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(25));
    mainContainer.setCenterWidget(editor);
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}
