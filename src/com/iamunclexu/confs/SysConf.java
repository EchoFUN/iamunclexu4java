package com.iamunclexu.confs;

import java.util.HashMap;
import java.util.Map;

import static com.iamunclexu.confs.Constant.CONNECTION_POOL_SIZE;
import static com.iamunclexu.confs.Constant.DEVELOPMENT_ENV_FLAG;
import static com.iamunclexu.confs.Constant.PASSWORD;
import static com.iamunclexu.confs.Constant.PRODUCTION_ENV_FLAG;
import static com.iamunclexu.confs.Constant.URL;
import static com.iamunclexu.confs.Constant.USERNAME;

public class SysConf {

  // public static String build = PRODUCTION_ENV_FLAG;
  public static String build = DEVELOPMENT_ENV_FLAG;

  public static int WEB_PORT = 8001;

  public static String FRONTEND_ROOT = (build.equals(PRODUCTION_ENV_FLAG) ? "/frontend" : "/Users/xukai/Documents/workspace/iamunclexu4java");

  private static Map<String, String> databaseInfo = new HashMap<>();

  public static Map<String, String> fetchDatabaseInfo() {
    databaseInfo.put(URL, "jdbc:mysql://127.0.0.1:3306/yymg?useSSL=false&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT&allowPublicKeyRetrieval=true");
    databaseInfo.put(USERNAME, "root");
    databaseInfo.put(PASSWORD, "123456");

    databaseInfo.put(CONNECTION_POOL_SIZE, "100");
    return databaseInfo;
  }

  public static int PAGE_COUNT = 10;
}
