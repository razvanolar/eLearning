package com.google.gwt.sample.elearning.client.settings.manage_lectures;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 *
 * Created by razvanolar on 30.11.2015.
 */
public interface VideoLinkDataProperties extends PropertyAccess<VideoLinkData> {
  @Editor.Path("id")
  ModelKeyProvider<VideoLinkData> key();

  ValueProvider<VideoLinkData, Long> id();
  ValueProvider<VideoLinkData, String> title();
  ValueProvider<VideoLinkData, String> url();
  ValueProvider<VideoLinkData, String> description();
}
