package com.daykm.tiger.features.util;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class DateUtil {

  // Mon Mar 13 22:12:19 +0000 2017
  private static DateTimeFormatter twitterFormat =
      DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy");

  public static LocalDateTime parseTwitterCreatedAt(String createdAt) {
    return LocalDateTime.parse(createdAt, twitterFormat);
  }
}
