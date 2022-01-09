package com.ple.jerbil.testcommon;

import java.io.IOException;
import java.util.Properties;

public class ConfigProps {

  public static Properties getProperties() {
    final Properties propConfig = new Properties();
    try {
      propConfig.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return propConfig;
  }

}
