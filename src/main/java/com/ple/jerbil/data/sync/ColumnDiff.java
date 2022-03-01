package com.ple.jerbil.data.sync;


import com.ple.jerbil.data.*;
import com.ple.jerbil.data.GenericInterfaces.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import org.jetbrains.annotations.Nullable;

@Immutable
public class ColumnDiff implements Diff<Column> {

  public static final Diff<Column>[] empty = new ColumnDiff[0];
  @Nullable public final ScalarDiff<String> name;
  @Nullable public final ScalarDiff<Table> table;
  @Nullable public final ScalarDiff<DataSpec> dataSpec;
  @Nullable public final ScalarDiff<Expression> generatedFrom;
  @Nullable public final ScalarDiff<Expression> defaultValue;
  @Nullable public final ScalarDiff<Expression> onUpdate;
  @Nullable public final ScalarDiff<BuildingHints> buildingHints;
//  private final ScalarDiff<String> comment;

  protected ColumnDiff(@Nullable ScalarDiff<String> name, @Nullable ScalarDiff<Table> table,
                       @Nullable ScalarDiff<DataSpec> dataSpec, @Nullable ScalarDiff<Expression> generatedFrom,
                       @Nullable ScalarDiff<Expression> defaultValue, @Nullable ScalarDiff<Expression> onUpdate,
                       @Nullable ScalarDiff<BuildingHints> buildingHints) {
    this.name = name;
    this.table = table;
    this.dataSpec = dataSpec;
    this.generatedFrom = generatedFrom;
    this.defaultValue = defaultValue;
//    this.comment = comment;
    this.onUpdate = onUpdate;
    this.buildingHints = buildingHints;
  }

  public static ColumnDiff make(ScalarDiff<String> name, ScalarDiff<Table> table,
                                ScalarDiff<DataSpec> dataSpec, ScalarDiff<Expression> generatedFrom,
                                ScalarDiff<Expression> defaultValue, ScalarDiff<Expression> onUpdate,
                                ScalarDiff<BuildingHints> buildingHints) {
    return new ColumnDiff(name, table, dataSpec, generatedFrom, defaultValue, onUpdate, buildingHints);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
