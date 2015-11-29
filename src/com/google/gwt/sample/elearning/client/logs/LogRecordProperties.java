package com.google.gwt.sample.elearning.client.logs;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by Cristi on 11/28/2015.
 */
public interface LogRecordProperties extends PropertyAccess<LogRecord> {
  @Editor.Path("millis")
  ModelKeyProvider<LogRecord> key();
  ValueProvider<LogRecord, String> message();
  ValueProvider<LogRecord, String> loggerName();
  ValueProvider<LogRecord, Long> millis();
  ValueProvider<LogRecord, Level> level();

}
