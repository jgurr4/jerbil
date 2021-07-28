package com.ple.jerbil.expression;

public class Equals extends BooleanExpression {


  private final Expression e1;
  private final Expression e2;

  public Equals(Expression e1, Expression e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public static Equals make(Expression e1, Expression e2) {
   return new Equals(e1, e2);
  }

}
