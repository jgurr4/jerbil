package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.StorageEngine;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.Immutable;

import java.util.ArrayList;

@Immutable
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

  public VectorDiff<T, D> filter(DdlOption ddlOption) {
    IList<T> newCreate = null;
    IList<T> newDelete = null;
    IList<D> newUpdate = null;
    if (ddlOption.isCreate()) {
      newCreate = create;
    }
    if (ddlOption.isDelete()) {
      newDelete = delete;
    }
    if (ddlOption.isUpdate()) {
      final ArrayList<D> list = new ArrayList<>();
      if (update != null) {
        for (D diff : update) {
          list.add(diff.filter(ddlOption));
        }
      newUpdate = IArrayList.make(list);
      }
    }
    return new VectorDiff<>(newCreate, newDelete, newUpdate);
  }

}