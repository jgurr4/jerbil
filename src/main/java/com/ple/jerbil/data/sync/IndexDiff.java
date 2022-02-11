package com.ple.jerbil.data.sync;

import com.ple.jerbil.data.IndexSpec;

public class IndexDiff implements Diff {

  public final VectorDiff<IndexSpec> indexSpecs;

  protected IndexDiff(VectorDiff<IndexSpec> indexSpecs) {
    this.indexSpecs = indexSpecs;
  }

  public static IndexDiff make(VectorDiff<IndexSpec> indexSpecs) {
    return new IndexDiff(indexSpecs);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
