package com.ple.jerbil.data;

import com.ple.jerbil.data.query.Table;

/**
 * Configure how the bridge will handle diffs between the schema object and the actual schema inside the database.
 *
 * The options are as follows: Each one above create is a lower level of preserving existing schema data and higher
 * level of altering existing database structure.
 *
 **/

public class DdlOption {
  public final byte option;
  protected DdlOption(int option) {
    this.option = (byte) option;
  }

  public static DdlOption make() {
    return new DdlOption(0b000);
  }

  public static DdlOption make(byte i) {
    return new DdlOption(i);
  }

  public DdlOption create() {
    if (option < 0b111) {
      return new DdlOption(0b100 + option);
    }
    return this;
  }

  public DdlOption update() {
    if (option < 0b111) {
      return new DdlOption(0b010 + option);
    }
    return this;
  }

  public DdlOption delete() {
    if (option < 0b111) {
      return new DdlOption(0b001 + option);
    }
    return this;
  }

  public DdlOption all() {
    if (option < 0b111) {
      return new DdlOption(0b111);
    }
    return this;
  }

}