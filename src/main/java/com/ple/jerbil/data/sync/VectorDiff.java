package com.ple.jerbil.data.sync;

import com.ple.util.IList;

public class VectorDiff<T> {

  public final IList<T> create;
  public final IList<T> delete;
  public final IList<Diff<T>> update;

  protected VectorDiff(IList<T> create, IList<T> delete, IList<Diff<T>> update){
    this.create = create;
    this.delete = delete;
    this.update = update;
  }

  public static <T2> VectorDiff<T2> make(IList<T2> create, IList<T2> delete, IList<Diff<T2>> update) {
    return new VectorDiff<>(create, delete, update);
  }

}
