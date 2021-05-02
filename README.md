Explanation:
JERBIL is an acronym. It stands for Java Entity Relational dataBase Information Language

JERBIL is a framekwork for expressing database concepts including sql queries and statements, database, table, column, and index schemas, and the differences between these. It is intended to replace all hand written sql statements in java code with compile time checked data types which would eliminate runtime sql statement syntax errors.

High level architecture:
 -> needs to catch bugs when you compile it. Not later when it's running.
 -> User table, can be a variable or enumeration. 
 user = user.table()
 user.select();

Capability:
 -> Create a function that is able to fetch column names from the user table, and compare it to what you need to retrieve or insert, and auto-generate a alter table statement based on that.
