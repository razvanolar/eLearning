package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_files;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;

/**
 * Created by razvanolar on 28.11.2015.
 */
public class UploadFileView {

  private FormPanel formPanel;
  private FileUpload fileUpload;
  private HBoxLayoutContainer panel;

  public UploadFileView() {
    initGUI();
  }

  private void initGUI() {
    formPanel = new FormPanel();
    fileUpload = new FileUpload();
    HorizontalPanel panel = new HorizontalPanel();

    formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
    formPanel.setMethod(FormPanel.METHOD_POST);
    formPanel.setWidget(panel);
    formPanel.setAction(GWT.getModuleBaseURL() + "licenseTypesUploadService");

    panel.add(fileUpload);
  }

  public Widget asWidget() {
    return panel;
  }
}
