package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 *
 * Created by Cristi on 12/23/2015.
 */
public interface HomeworkDataProperties extends PropertyAccess<HomeworkData> {
  @Editor.Path("id")
  ModelKeyProvider<HomeworkData> key();

  ValueProvider<HomeworkData, Long> id();
  ValueProvider<HomeworkData, String> title();
  ValueProvider<HomeworkData, Integer> score();
}
