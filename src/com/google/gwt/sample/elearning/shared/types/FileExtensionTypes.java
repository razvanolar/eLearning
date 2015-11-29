package com.google.gwt.sample.elearning.shared.types;

/**
 * Created by razvanolar on 29.11.2015.
 */
public enum FileExtensionTypes {
  HTML(".html"), PDF(".pdf"), PNG(".png"), XML(".xml");

  private String value;

  FileExtensionTypes(String value) {
    this.value = value;
  }

  public static FileExtensionTypes getFileExtensionByValue(String value) {
    for (FileExtensionTypes extension : values())
      if (extension.value.equalsIgnoreCase(value))
        return extension;
    return null;
  }

  public String getValue() {
    return value;
  }
}
