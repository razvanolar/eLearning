package com.google.gwt.sample.elearning.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.services.LoginService;
import com.google.gwt.sample.elearning.client.services.LoginServiceAsync;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.menu.DateMenu;
import com.sencha.gxt.widget.core.client.menu.HeaderMenuItem;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;

public class ELearningApp implements EntryPoint {

  private VerticalLayoutContainer mainContainer;
  private HorizontalLayoutContainer logoPanel;

  private static LoginServiceAsync loginService = GWT.create(LoginService.class);

  /*Entry point method*/
  public void onModuleLoad() {
    Viewport viewport = new Viewport();
    RootPanel.get("eLearning").add(viewport);

    CenterLayoutContainer centerPanel = new CenterLayoutContainer();

    BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
    BoxLayoutContainer.BoxLayoutData flex2 = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
    flex.setFlex(1);
    flex2.setFlex(3);
    HBoxLayoutContainer northPanel = new HBoxLayoutContainer();
    HBoxLayoutContainer buttonsPanel = new HBoxLayoutContainer();
    northPanel.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    northPanel.setStyleName("headerPanel");
    buttonsPanel.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    buttonsPanel.setPack(BoxLayoutContainer.BoxLayoutPack.END);
//    buttonsPanel.setStyleName("headerButtonsPanel");
    buttonsPanel.setHeight(40);
    buttonsPanel.setPadding(new Padding(0, 10, 0, 0));

    northPanel.add(new Image(ELearningController.ICONS.logo()), flex);
    northPanel.add(buttonsPanel, flex2);

    BorderLayoutContainer mainELearningContainer = new BorderLayoutContainer();
    mainELearningContainer.setNorthWidget(northPanel, new BorderLayoutContainer.BorderLayoutData(50));
    mainELearningContainer.setCenterWidget(centerPanel);

    mainELearningContainer.setStyleName("mainELearningContainer");
    viewport.add(mainELearningContainer);
    ELearningController.getInstance().setViews(mainELearningContainer, buttonsPanel);
    ELearningController.getInstance().run();
  }

  private void displayLoginWindow() {
    Viewport viewport = new Viewport();
    mainContainer = new VerticalLayoutContainer();

    final TextField textField = new TextField();
    TextButton btn = new TextButton("Login");

    mainContainer.add(textField, new VerticalLayoutContainer.VerticalLayoutData(-1, -1));
    mainContainer.add(btn, new VerticalLayoutContainer.VerticalLayoutData(-1, -1));

    btn.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        String user = textField.getValue();
        if(user != null) {
          loginService.loginServer(user, "pwd", new AsyncCallback<UserData>() {
            @Override
            public void onFailure(Throwable caught) {
              Window.alert("Access denied!");
            }

            @Override
            public void onSuccess(UserData result) {
              RootPanel.get("stockList").clear();
              displayWindow();

              final long DURATION = 1000 * 60;// * 60 * 24 * 1;
              Date expires = new Date(System.currentTimeMillis() + DURATION);
              Cookies.setCookie("sid", result.getSessionId(), expires, null, "/", false);
            }
          });
        }
      }
    });

    viewport.add(mainContainer);
    RootPanel.get("stockList").add(viewport);
  }

  private void displayWindow() {
    Viewport viewport = new Viewport();
    mainContainer = new VerticalLayoutContainer();
    mainContainer.addStyleName("mainContainer");

    mainContainer.add(getMenu(), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
    mainContainer.add(createLogoPanel(), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

    BorderLayoutContainer border = new BorderLayoutContainer();

    AccordionLayoutContainer accordionLayoutContainer = new AccordionLayoutContainer();
    accordionLayoutContainer.add(new ContentPanel());
    accordionLayoutContainer.add(new ContentPanel());
    accordionLayoutContainer.add(new ContentPanel());

    BorderLayoutContainer.BorderLayoutData borderWestData = new BorderLayoutContainer.BorderLayoutData(250);
    borderWestData.setSplit(true);
    borderWestData.setCollapsible(true);
    borderWestData.setCollapseMini(true);
    border.setWestWidget(WidgetUtil.getAccordionLayout(), borderWestData);

    BorderLayoutContainer.BorderLayoutData borderEastData = new BorderLayoutContainer.BorderLayoutData(200);
    borderEastData.setSplit(true);
    border.setEastWidget(WidgetUtil.getEastPanel(), borderEastData);

    ContentPanel center = new ContentPanel();
    center.setHeaderVisible(false);
    center.addStyleName("defaultBg");
    border.setCenterWidget(center);

    mainContainer.add(border, new VerticalLayoutContainer.VerticalLayoutData(1, 1));

    viewport.add(mainContainer);
    RootPanel.get("stockList").add(viewport);
  }

  private Widget createLogoPanel() {
    logoPanel = new HorizontalLayoutContainer();
    logoPanel.setHeight(40);
    logoPanel.addStyleName("logo");

    HorizontalLayoutContainer left = new HorizontalLayoutContainer();
    HorizontalLayoutContainer right = new HorizontalLayoutContainer();

    right.add(new Button("dadas"));

    right.addStyleName("logoRight");
    left.setHeight(40);
    right.setHeight(40);

    logoPanel.add(left, new HorizontalLayoutContainer.HorizontalLayoutData(.5, -1));
    logoPanel.add(right, new HorizontalLayoutContainer.HorizontalLayoutData(.5, -1));

    return logoPanel;
  }

  public Widget getMenu() {
    SelectionHandler<Item> handler = new SelectionHandler<Item>() {
      @Override
      public void onSelection(SelectionEvent<Item> event) {
        if (event.getSelectedItem() instanceof MenuItem) {
          MenuItem item = (MenuItem) event.getSelectedItem();
          Info.display("Action", "You selected the " + item.getText());
        }
      }
    };

    Menu subMenuItem2 = new Menu();
    subMenuItem2.addSelectionHandler(handler);
    subMenuItem2.add(new MenuItem("readme.txt"));
    subMenuItem2.add(new MenuItem("helloworld.txt"));

    MenuItem subMenuNew = new MenuItem("New");
    MenuItem subMenuFile = new MenuItem("Open File");
    subMenuFile.setSubMenu(subMenuItem2);

    Menu menuFile = new Menu();
    menuFile.addSelectionHandler(handler);
    menuFile.add(subMenuNew);
    menuFile.add(subMenuFile);

    Menu subMenuEdit = new Menu();
    subMenuEdit.addSelectionHandler(handler);
    subMenuEdit.add(new MenuItem("Cut"));
    subMenuEdit.add(new MenuItem("Copy"));

    MenuBarItem menuBarEdit = new MenuBarItem("Edit", subMenuEdit);

    Menu subMenuItem3 = new Menu();
    subMenuItem3.addSelectionHandler(handler);
    subMenuItem3.add(new MenuItem("Search"));
    subMenuItem3.add(new MenuItem("File"));
    subMenuItem3.add(new MenuItem("Java"));

    MenuBarItem menuBarItem3 = new MenuBarItem("Search", subMenuItem3);

    CheckMenuItem checkMenuItem1 = new CheckMenuItem("I Like Cats");
    CheckMenuItem checkMenuItem2 = new CheckMenuItem("I Like Dogs");
    checkMenuItem1.setChecked(true);

    CheckMenuItem menuItemBlue = new CheckMenuItem("Blue Theme");
    CheckMenuItem menuItemGray = new CheckMenuItem("Gray Theme");
    CheckMenuItem menuItemNeptune = new CheckMenuItem("Neptune Theme");

    menuItemBlue.setChecked(true);
    menuItemBlue.setGroup("radios");
    menuItemGray.setGroup("radios");
    menuItemNeptune.setGroup("radios");

    Menu radioMenu = new Menu();
    radioMenu.addSelectionHandler(handler);
    radioMenu.add(new HeaderMenuItem("Built-in GXT Themes"));
    radioMenu.add(menuItemBlue);
    radioMenu.add(menuItemGray);
    radioMenu.add(menuItemNeptune);

    MenuItem menuItemRadios = new MenuItem("Radio Options");
    menuItemRadios.setSubMenu(radioMenu);

    final DateMenu subdateMenu = new DateMenu();
    subdateMenu.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(ValueChangeEvent<Date> event) {
        Date d = event.getValue();
        DateTimeFormat f = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
        Info.display("Value Changed", "You selected " + f.format(d));
        subdateMenu.hide(true);
      }
    });

    MenuItem menuItemDate = new MenuItem("Choose a Date");
    menuItemDate.setSubMenu(subdateMenu);

    Menu subMenu4 = new Menu();
    subMenu4.addSelectionHandler(handler);
    subMenu4.add(checkMenuItem1);
    subMenu4.add(checkMenuItem2);
    subMenu4.add(new SeparatorMenuItem());
    subMenu4.add(menuItemRadios);
    subMenu4.add(menuItemDate);

    MenuBarItem menuBarItem4 = new MenuBarItem("Foo", subMenu4);

    MenuBar menuBar = new MenuBar();
    menuBar.addStyleName(ThemeStyles.get().style().borderBottom());
    menuBar.add(new MenuBarItem("File", menuFile));
    menuBar.add(menuBarEdit);
    menuBar.add(menuBarItem3);
    menuBar.add(menuBarItem4);

    return menuBar;
  }
}
