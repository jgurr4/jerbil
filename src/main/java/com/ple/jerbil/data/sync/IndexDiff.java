package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.Index;

public class IndexDiff implements Diff {

  public final VectorDiff<Index> indexSpecs;

  protected IndexDiff(VectorDiff<Index> indexSpecs) {
    this.indexSpecs = indexSpecs;
  }

  public static IndexDiff make(VectorDiff<Index> indexSpecs) {
    return new IndexDiff(indexSpecs);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
