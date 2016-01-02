package com.google.gwt.sample.elearning.client.eLearningUtils.models_properties;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/***
 * Created by Cristi on 11/17/2015.
 */
public interface LectureDataProperties extends PropertyAccess<Lecture> {

  @Editor.Path("id")
  ModelKeyProvider<Lecture> key();

  ValueProvider<Lecture, Long> id();
  ValueProvider<Lecture, String> lectureName();
}
