package com.ple.jerbil.data.sync;

/**
 * For use with the filter methods of Diffs, in order to eliminate certain diffs which violate parameters set by the user.
 *
 * For example, if you don't want to allow a sync() to delete extra tables or columns that exist in the database, then
 * you would pass in a ddloption which contains create and update only and leave off delete.
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
    return new DdlOption(0b0);
  }

  public static DdlOption make(int i) {
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