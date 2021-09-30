package com.iamunclexu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

  public static boolean isStaticUri(String uri) {
    if (uri.contains("/static/")) {
      return true;
    }
    return false;
  }

  public static String dateFormatter(String stamp, String formatter) {
    SimpleDateFormat sf = new SimpleDateFormat(formatter);
    Date date = new Date(Long.parseLong(stamp));
    return sf.format(date);
  }

  public static String dateFormatter(String stamp) {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
    Date date = new Date(Long.parseLong(stamp));
    return sf.format(date);
  }
}
