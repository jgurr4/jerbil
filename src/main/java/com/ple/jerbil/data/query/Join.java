package com.ple.jerbil.data.query;

import com.ple.util.Immutable;
import com.ple.util.IList;

@Immutable
public class Join extends FromExpression {

  public final FromExpression fe1;
  public final FromExpression fe2;

  protected Join(FromExpression fe1, FromExpression fe2) {
    this.fe1 = fe1;
    this.fe2 = fe2;
  }

  public static FromExpression make(FromExpression fe1, FromExpression fe2) {
    return new Join(fe1, fe2);
  }

  @Override
  protected void diffJoin() {
  }

  public IList<TableContainer> tableList() {
    return fe1.tableList().addAll(fe2.tableList());
  }

}
