package com.ple.jerbil.data.sync;


import com.ple.jerbil.data.*;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import org.jetbrains.annotations.Nullable;

@Immutable
public class ColumnDiff implements Diff<Column> {

  public static final Diff<Column>[] empty = new ColumnDiff[0];
  @Nullable public final ScalarDiff<String> columnName;
  @Nullable public final ScalarDiff<Table> table;
  @Nullable public final ScalarDiff<DataSpec> dataSpec;
//  @Nullable public final ScalarDiff<Expression> generatedFrom;
  @Nullable public final ScalarDiff<Expression> defaultValue;
  @Nullable public final ScalarDiff<BuildingHints> buildingHints;
//  private final ScalarDiff<String> comment;

  protected ColumnDiff(@Nullable ScalarDiff<String> columnName, @Nullable ScalarDiff<Table> table,
                       @Nullable ScalarDiff<DataSpec> dataSpec, @Nullable ScalarDiff<Expression> defaultValue,
                       @Nullable ScalarDiff<BuildingHints> buildingHints) {
    this.columnName = columnName;
    this.table = table;
    this.dataSpec = dataSpec;
//    this.generatedFrom = generatedFrom;
    this.defaultValue = defaultValue;
//    this.comment = comment;
    this.buildingHints = buildingHints;
  }

  public static ColumnDiff make(ScalarDiff<String> columnName, ScalarDiff<Table> table,
                                ScalarDiff<DataSpec> dataSpec, ScalarDiff<Expression> defaultValue,
                                ScalarDiff<BuildingHints> buildingHints) {
    return new ColumnDiff(columnName, table, dataSpec, defaultValue, buildingHints);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

  @Override
  public ColumnDiff filter(DdlOption ddlOption) {
    ScalarDiff<String> newColName = columnName.filter(ddlOption);
    ScalarDiff<Table> newTable = table.filter(ddlOption);
    ScalarDiff<DataSpec> newDataSpec = dataSpec.filter(ddlOption);
    ScalarDiff<Expression> newDefaultVal = defaultValue.filter(ddlOption);
    ScalarDiff<BuildingHints> newBuildingHints = buildingHints.filter(ddlOption);
    return new ColumnDiff(newColName, newTable, newDataSpec, newDefaultVal, newBuildingHints);
  }

}
