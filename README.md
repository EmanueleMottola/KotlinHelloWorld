Kotlin introduction
========================================

This file is used to illustrate the generic characteristics of the Kotlin language.


Primary traits
----------------------------------------


Kotlin can be used on any Platform running a **JVM**. Consequently,
it runs as and on:
 - server-side code,
 - android devices.
 
It is a **statistically typed** language. It means the type is known at compile time. Hence, 
this implies different benefits, like:
 - Performance: faster calling of methods
 - Reiability: The compiler verifies the correctness of the program.
 - Maintanability: easy to read.
 
Most of the job is done by **type inference**.

It supports both functional and object-oriented programming. The characteristics
of functional programming are:
 - First-class functions -- values can be passed by value.
 - Immutability -- work with immutable when possible. This assures thread safety.
 - No side effects -- no modification of elements outside the function.
 

Kotlin assures complete interoperability with Java. 

##### Compiling code 
The compiler analyses the source code and creates .class files.
Then, the procedure is identical to the Java compilation process.

source -> .class -> .jar -> application (with Kotlin runtime).

 
