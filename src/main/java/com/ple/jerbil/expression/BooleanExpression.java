package com.ple.jerbil.expression;

public class BooleanExpression extends Expression {
/*
 There will also be NumericExpression, StringExpression, DateExpression, because each has to be handled slightly differently.
 For example, in order to be an expression, you have to be able to add them together, but a string doesn't add to another string in mysql.
   Similarly a date cannot add another date, it can only add a interval. You also cannot add boolean to a numeric or another boolean. So
   This is why each expression has it's own specific type. This will be used in places where each type of expression is allowed. For instance,
   A SelectExpression is the parent of all expressions. it is implemented in a AliasedExpression, or a normal Expression. The
   normal Expression can be used for any object expression. We specify the specific object so that no one is allowed to add things
   that should not be added as described above. But, for aliased expressions we use AliasedExpression class which doesn't need any
   specified data type classes because you already are not able to add a aliased expression to another one. For example, you cannot do this:
   select total AS myTotal + 3, name from tablename;   // but you could do:
   select total + 3 AS myTotal, name from tablename;
   So for this you have to be able to combine a AliasedExpression with a numericExpression, and any other data type expressions.
*/


}
