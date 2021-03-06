package com.google.gwt.sample.elearning.client.eLearningUtils;

/***
 * Created by razvanolar on 13.11.2015.
 */
public class TextUtil {

  /**
   * Check if the received value is empty or not. (If value is null, we assume that is an empty string)
   * @param value - String
   * @return true - if value is null or empty
   *         false - otherwise
   */
  public static boolean isEmptyString(String value) {
    if (value == null)
      return true;
    value = value.trim();
    return value.isEmpty();
  }

  public static String getFileExtentsion(String fileName) {
    StringBuilder builder = new StringBuilder(fileName);
    int index = builder.lastIndexOf(".");
    if (index == -1)
      return "";
    return builder.substring(index);
  }

  public static String getFileSubpath(String filePath) {
    StringBuilder builder = new StringBuilder(filePath);
    int index = builder.indexOf("elearning\\app_files");
    if (index == -1)
      return null;
    return builder.substring(index);
  }
}
