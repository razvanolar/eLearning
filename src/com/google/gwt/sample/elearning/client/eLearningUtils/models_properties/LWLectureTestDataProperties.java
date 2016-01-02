package com.google.gwt.sample.elearning.client.eLearningUtils.models_properties;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.sample.elearning.shared.model.LWLectureTestData;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/***
 * Created by razvanolar on 28.11.2015.
 */
public interface LWLectureTestDataProperties extends PropertyAccess<LWLectureTestData> {
  @Editor.Path("name")
  ModelKeyProvider<LWLectureTestData> key();

  ValueProvider<LWLectureTestData, String> name();
  ValueProvider<LWLectureTestData, Integer> questionsNo();
  ValueProvider<LWLectureTestData, Integer> totalScore();
}
