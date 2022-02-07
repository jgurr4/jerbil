package com.ple.jerbil.data.sync;


import com.ple.jerbil.data.ColumnAttribute;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.EnumSpec;
import com.ple.jerbil.data.Index;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;

public class ColumnDiff implements Diff<Column> {

  private final ScalarDiff<String> name;
  private final VectorDiff<ColumnAttribute> columnAttributes;
  private final ScalarDiff<DataSpec> dataSpec;
  private final VectorDiff<Index> index;
  private final ScalarDiff<Expression> generatedFrom;
  private final ScalarDiff<Expression> defaultValue;
//  private final ScalarDiff<String> comment;
  //ColumnAttribute has these fields: unique, nullable, invisible, autoIncrement,
  //Index has these fields: primary, key, foreign, fulltext, spatial, order (ASC or DESC), and size for prefixed indexes.

  protected ColumnDiff(ScalarDiff<String> name, VectorDiff<ColumnAttribute> columnAttributes,
                       ScalarDiff<DataSpec> dataSpec, VectorDiff<Index> index,
                       ScalarDiff<Expression> generatedFrom, ScalarDiff<Expression> defaultValue) {
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

}
