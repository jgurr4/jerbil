package com.ple.jerbil.data.bridge;

import com.ple.jerbil.data.DataBridge;
import com.ple.jerbil.data.LanguageGenerator;
import com.ple.jerbil.data.translator.MysqlLanguageGenerator;

public class MysqlBridge implements DataBridge {

  public final LanguageGenerator generator = MysqlLanguageGenerator.make();

  public MysqlBridge() {
  }

  public static DataBridge make() {
    return new MysqlBridge();
  }

  @Override
  public LanguageGenerator getGenerator() {
    return generator;
  }

}
