package com.ple.jerbil.data.sync;


import com.ple.jerbil.data.*;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.BuildingHints;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
  public final Column columnA;
  public final Column columnB;

  protected ColumnDiff(@Nullable ScalarDiff<String> columnName, @Nullable ScalarDiff<Table> table,
                       @Nullable ScalarDiff<DataSpec> dataSpec, @Nullable ScalarDiff<Expression> defaultValue,
                       @Nullable ScalarDiff<BuildingHints> buildingHints, Column columnA, Column columnB) {
    this.columnName = columnName;
    this.table = table;
    this.dataSpec = dataSpec;
//    this.generatedFrom = generatedFrom;
    this.defaultValue = defaultValue;
//    this.comment = comment;
    this.buildingHints = buildingHints;
    this.columnA = columnA;
    this.columnB = columnB;
  }

  public static ColumnDiff make(ScalarDiff<String> columnName, ScalarDiff<Table> table,
                                ScalarDiff<DataSpec> dataSpec, ScalarDiff<Expression> defaultValue,
                                ScalarDiff<BuildingHints> buildingHints, Column columnA, Column columnB) {
    return new ColumnDiff(columnName, table, dataSpec, defaultValue, buildingHints, columnA, columnB);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

  @Override
  public ColumnDiff filter(DdlOption ddlOption) {
    ScalarDiff<String> newColName = null;
    ScalarDiff<Table> newTable = null;
    ScalarDiff<DataSpec> newDataSpec = null;
    ScalarDiff<Expression> newDefaultVal = null;
    ScalarDiff<BuildingHints> newBuildingHints = null;
    if (columnName != null) {
      newColName = columnName.filter(ddlOption);
    }
    if (table != null) {
      newTable = table.filter(ddlOption);
    }
    if (dataSpec != null) {
      newDataSpec = dataSpec.filter(ddlOption);
    }
    if (defaultValue != null) {
      newDefaultVal = defaultValue.filter(ddlOption);
    }
    if (buildingHints != null) {
      newBuildingHints = buildingHints.filter(ddlOption);
    }
    return new ColumnDiff(newColName, newTable, newDataSpec, newDefaultVal, newBuildingHints, columnA, columnB);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ColumnDiff)) return false;
    ColumnDiff that = (ColumnDiff) o;
    return Objects.equals(columnName, that.columnName) && Objects.equals(table,
        that.table) && Objects.equals(dataSpec, that.dataSpec) && Objects.equals(defaultValue,
        that.defaultValue) && Objects.equals(buildingHints, that.buildingHints) && columnA.equals(
        that.columnA) && columnB.equals(that.columnB);
  }

  @Override
  public int hashCode() {
    return Objects.hash(columnName, table, dataSpec, defaultValue, buildingHints, columnA, columnB);
  }

  @Override
  public String toString() {
    return "ColumnDiff{" +
        "columnName=" + columnName +
        ", table=" + table +
        ", dataSpec=" + dataSpec +
        ", defaultValue=" + defaultValue +
        ", buildingHints=" + buildingHints +
        ", columnA=" + columnA +
        ", columnB=" + columnB +
        '}';
  }
}
