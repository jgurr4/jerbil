package com.ple.jerbil.data.sync;

import com.ple.util.IList;

public class VectorDiff<T, D extends Diff<T>> {
  public final IList<T> create;
  public final IList<T> delete;
  public final IList<D> update;

  protected VectorDiff(IList<T> create, IList<T> delete, IList<D> update){
    this.create = create;
    this.delete = delete;
    this.update = update;
  }

  public static <T, D extends Diff<T>> VectorDiff<T, D> make(IList<T> create, IList<T> delete, IList<D> update) {
    return new VectorDiff<>(create, delete, update);
  }

}
