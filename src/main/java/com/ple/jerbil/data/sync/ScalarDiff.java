package com.ple.jerbil.data.sync;

public class ScalarDiff<T> {

  public final T before;
  public final T after;

  protected ScalarDiff(T before, T after){
    this.before = before;
    this.after = after;
  }

  public static <T> ScalarDiff<T> make(T before, T after) {
    return new ScalarDiff<>(before, after);
  }

}
