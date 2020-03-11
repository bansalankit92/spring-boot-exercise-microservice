package com.telstra.codechallenge.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

  public static LocalDate getLastLocalDate(int days) {
    LocalDate now = LocalDate.now();
    return now.minusDays(days + now.getDayOfWeek().getValue() - 1);
  }

  public static String getYYYYMMDD(LocalDate localDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return localDate.format(formatter);
  }

}
