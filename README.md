## Overview

JERBIL stands for Java Entity Relational dataBase Information Language

JERBIL is a framework for expressing database concepts including sql queries and statements, database, table, column, and indexType schemas, and the differences between these. It is intended to replace all hand written sql statements in java code with compile time checked data types which would eliminate runtime sql statement syntax errors.

## High level architecture
 -> needs to catch bugs when you compile it. Not later when it's running.
 -> User table, can be a variable or enumeration. 
 user = user.table()
 user.select();

## Capability
 -> Create a function that is able to fetch column names from the user table, and compare it to what you need to retrieve or insert, and auto-generate a alter table statement based on that.
 
## Jerbil features
 -> Guaranteed generation of compatible query statements because of compile-time checking. </br>
 -> Auto-completion for any queries written in java. </br>
 -> Compatible with any database language including SQL/NOSQL because of reliance on user specified translators and bridges. </br>
 -> A framework which can be used as a base to create alternative language generation library for almost any programming language. for example: html, javascript, xml etc... </br>
 -> Built on Immutable classes which allows for simple and thread-safe operations. </br>
 -> Designed to simplify the amount of work required from developers and to allow developers without knowledge of SQL to write advanced queries in java. </br>
 -> Uses Factory pattern to generate the query objects which gets converted to a string when time to send query to database. </br>
 </br>