package com.ple.jerbil.data;

import com.ple.jerbil.data.selectExpression.Column;
import com.ple.util.IList;
import org.jetbrains.annotations.Nullable;

//TODO: Add @Nullable FkReference which contains tableName, list of columns, @Nullable onDelete and @Nullable onUpdate
// ReferenceOption.
@Immutable
public class FkReference {
  public final String tblName;
  public final IList<Column> columns;
  @Nullable public final ReferenceOption onDelete;
  @Nullable public final ReferenceOption onUpdate;

  protected FkReference(String tblName, IList<Column> columns, @Nullable ReferenceOption onDelete,
                     @Nullable ReferenceOption onUpdate) {
    this.tblName = tblName;
    this.columns = columns;
    this.onDelete = onDelete;
    this.onUpdate = onUpdate;
  }

  public static FkReference make(String tblName, IList<Column> columns, @Nullable ReferenceOption onDelete,
                                 @Nullable ReferenceOption onUpdate) {
    return new FkReference(tblName, columns, onDelete, onUpdate);
  }
}
