package com.google.gwt.sample.elearning.client.eLearningUtils.models_properties;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.sample.elearning.shared.model.FileData;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/***
 * Created by razvanolar on 24.11.2015.
 */
public interface FileDataProperties extends PropertyAccess<FileData> {
  @Editor.Path("path")
  ModelKeyProvider<FileData> path();

  ValueProvider<FileData, String> name();
  ValueProvider<FileData, Long> length();
  ValueProvider<FileData, String> lastModified();
}
