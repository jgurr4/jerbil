package com.ple.jerbil.data.sync;


import com.ple.jerbil.data.*;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import org.jetbrains.annotations.Nullable;

@Immutable
public class ColumnDiff implements Diff<Column> {

  public static final Diff<Column>[] empty = new ColumnDiff[0];
  @Nullable public final ScalarDiff<String> name;
  @Nullable public final VectorDiff<ColumnAttribute> columnAttributes;
  @Nullable public final ScalarDiff<DataSpec> dataSpec;
  @Nullable public final VectorDiff<Index> indexSpec;
  @Nullable public final ScalarDiff<Expression> generatedFrom;
  @Nullable public final ScalarDiff<Expression> defaultValue;
//  private final ScalarDiff<String> comment;
  //ColumnAttribute has these fields: unique, nullable, invisible, autoIncrement,
  //Index has these fields: primary, secondary, foreign, fulltext, spatial.
  // IndexSpec has these fields: order (ASC or DESC), and size for prefixed indexes.

  protected ColumnDiff(@Nullable ScalarDiff<String> name, @Nullable VectorDiff<ColumnAttribute> columnAttributes,
                       @Nullable ScalarDiff<DataSpec> dataSpec, @Nullable VectorDiff<Index> indexSpec,
                       @Nullable ScalarDiff<Expression> generatedFrom, @Nullable ScalarDiff<Expression> defaultValue) {
    this.name = name;
    this.columnAttributes = columnAttributes;
    this.dataSpec = dataSpec;
    this.indexSpec = indexSpec;
    this.generatedFrom = generatedFrom;
    this.defaultValue = defaultValue;
//    this.comment = comment;
  }

  public static ColumnDiff make(ScalarDiff<String> name, VectorDiff<ColumnAttribute> columnAttributes,
                                ScalarDiff<DataSpec> dataSpec, VectorDiff<Index> indexSpec,
                                ScalarDiff<Expression> generatedFrom, ScalarDiff<Expression> defaultValue) {
    return new ColumnDiff(name, columnAttributes, dataSpec, indexSpec, generatedFrom, defaultValue);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
