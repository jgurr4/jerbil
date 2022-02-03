package com.ple.jerbil.data;

import com.ple.jerbil.data.sync.DbDiff;
import com.ple.jerbil.data.sync.Diff;
import com.ple.util.IList;

public class FilteredDiff {

  public final Diff diff;
  public final IList<String> errors;
  public final IList<String> warnings;

  protected FilteredDiff(Diff diff, IList<String> errors, IList<String> warnings) {
    this.diff = diff;
    this.errors = errors;
    this.warnings = warnings;
  }

  public static FilteredDiff make(Diff diff, IList<String> errors, IList<String> warnings) {
    return new FilteredDiff(diff, errors, warnings);
  }

  public String toSql() {
    return DataGlobal.bridge.getGenerator().toSql(this.diff);
  }

}
