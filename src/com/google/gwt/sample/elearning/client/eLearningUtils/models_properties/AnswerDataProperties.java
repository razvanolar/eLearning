package com.google.gwt.sample.elearning.client.eLearningUtils.models_properties;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.sample.elearning.shared.model.AnswerData;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/***
 * Created by razvanolar on 29.11.2015.
 */
public interface AnswerDataProperties extends PropertyAccess<AnswerData> {

  @Editor.Path("value")
  ModelKeyProvider<AnswerData> key();

  ValueProvider<AnswerData, String> value();
}
