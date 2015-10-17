package com.google.gwt.sample.elearning.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.Portlet;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.PortalLayoutContainer;

/**
 * Created by razvanolar on 12.10.2015.
 */
public class WidgetUtil {


  public static Widget getAccordionLayout() {
    AccordionLayoutContainer panel = new AccordionLayoutContainer();
    panel.addStyleName("defaultBg");

    ContentPanel doc = new ContentPanel();
    doc.setHeadingHtml("Documents");
    doc.getHeader().addStyleDependentName("cp");

    ContentPanel news = new ContentPanel();
    news.setHeadingHtml("News");

    ContentPanel others = new ContentPanel();
    others.setHeadingHtml("Others");

    panel.add(doc);
    panel.add(news);
    panel.add(others);

    return panel;
  }

  public static Widget getEastPanel() {
    PortalLayoutContainer panel = new PortalLayoutContainer(1);
    panel.addStyleName("portlet");

    Portlet p1 = new Portlet();
    p1.setHeadingHtml("Something");
    p1.setCollapsible(true);
    p1.getHeader().addTool(new ToolButton(ToolButton.GEAR));
    p1.add(new HTML(
        "Using GWT, developers can develop and debug Ajax applications in the Java language using the Java development tools of their choice. When the application is deployed, the GWT cross-compiler translates the Java application to standalone JavaScript files that are optionally obfuscated and deeply optimized. When needed, JavaScript can also be embedded directly into Java code, using Java comments."));

    Portlet p2 = new Portlet();
    p2.setHeadingHtml("Something else");
    p2.setCollapsible(true);
    p2.add(new DatePicker());

    panel.setColumnWidth(0, 1);
    panel.add(p1, 0);
    panel.add(p2, 0);

    return panel;
  }
}
