package com.ple.jerbil.data.sync;


import com.ple.jerbil.data.*;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

@Immutable
public class ColumnDiff implements Diff<Column> {

  public static final Diff<Column>[] empty = new ColumnDiff[0];
  @Nullable public final ScalarDiff<String> name;
  @Nullable public final VectorDiff<ColumnAttribute> columnAttributes;
  @Nullable public final ScalarDiff<DataSpec> dataSpec;
  @Nullable public final VectorDiff<Index> index;
  @Nullable public final ScalarDiff<Expression> generatedFrom;
  @Nullable public final ScalarDiff<Expression> defaultValue;
//  private final ScalarDiff<String> comment;
  //ColumnAttribute has these fields: unique, nullable, invisible, autoIncrement,
  //Index has these fields: primary, key, foreign, fulltext, spatial, order (ASC or DESC), and size for prefixed indexes.

  protected ColumnDiff(@Nullable ScalarDiff<String> name, @Nullable VectorDiff<ColumnAttribute> columnAttributes,
                       @Nullable ScalarDiff<DataSpec> dataSpec, @Nullable VectorDiff<Index> index,
                       @Nullable ScalarDiff<Expression> generatedFrom, @Nullable ScalarDiff<Expression> defaultValue) {
    this.name = name;
    this.columnAttributes = columnAttributes;
    this.dataSpec = dataSpec;
    this.index = index;
    this.generatedFrom = generatedFrom;
    this.defaultValue = defaultValue;
//    this.comment = comment;
  }

  public static ColumnDiff make(ScalarDiff<String> name, VectorDiff<ColumnAttribute> columnAttributes,
                                ScalarDiff<DataSpec> dataSpec, VectorDiff<Index> index,
                                ScalarDiff<Expression> generatedFrom, ScalarDiff<Expression> defaultValue) {
    return new ColumnDiff(name, columnAttributes, dataSpec, index, generatedFrom, defaultValue);
  }

  @Override
  public int getTotalDiffs() {
    return 0;
  }

}
