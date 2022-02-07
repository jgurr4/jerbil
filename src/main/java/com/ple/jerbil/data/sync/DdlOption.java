package com.ple.jerbil.data.sync;

/**
 * Configure how the bridge will handle diffs between the schema object and the actual schema inside the database.
 *
 * The options are as follows: Each one above create is a lower level of preserving existing schema data and higher
 * level of altering existing database structure.
 *
 **/

public class DdlOption {
  public final byte option;
  private static final byte create = 1 << 2;
  private static final byte update = 1 << 1;
  private static final byte delete = 1;
  protected DdlOption(int option) {
    this.option = (byte) option;
  }

  //FIXME: Empty make should be create and update by default. Delete has to be explicit from developer.
  public static DdlOption make() {
    return new DdlOption(0b000);
  }

  public static DdlOption make(byte i) {
    return new DdlOption(i);
  }

  public DdlOption create() {
    return new DdlOption(create|option);
  }

  public DdlOption update() {
    return new DdlOption(update|option);
  }

  public DdlOption delete() {
    return new DdlOption(delete|option);
  }

  public DdlOption all() {
    return new DdlOption(0b111);
  }

}