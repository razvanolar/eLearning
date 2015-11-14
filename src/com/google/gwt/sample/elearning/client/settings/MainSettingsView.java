package com.google.gwt.sample.elearning.client.settings;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.theme.blue.client.tabs.BlueTabPanelBottomAppearance;
import com.sencha.gxt.widget.core.client.TabPanel;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class MainSettingsView implements MainSettingsController.IMainSettingsView {

  private TabPanel tabPanel;

  public MainSettingsView() {
    initGUI();
  }

  private void initGUI() {
    tabPanel = new TabPanel(GWT.<TabPanel.TabPanelAppearance> create(TabPanel.TabPanelBottomAppearance.class));
    tabPanel.setBorders(false);
//    tabPanel = new TabPanel();
  }

  @Override
  public void addTab(Widget tabContent, String title) {
    tabPanel.add(tabContent, title);
  }

  @Override
  public Widget asWidget() {
    return tabPanel;
  }
}
