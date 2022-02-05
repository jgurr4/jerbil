package com.ple.jerbil.data.sync;

import com.ple.util.IMap;

/**
 * It's useful to think of create() delete() and update() as the left, right, and middle section of a ven diagram respectively.
 * The create/left side contains properties that exist only in the left database. The inverse is true for the delete/right side.
 * The update/middle section has values that exist in both databases, but which are not matching perfectly.
 * In other words, if every list is empty, that means there are no diffs and the databases match perfectly.
 * @param <K>
 */
public interface Diff<K extends Props> {
  public IMap<K, Object> create();
  public IMap<K, Object> delete();
  public IMap<K, Object> update();
}
