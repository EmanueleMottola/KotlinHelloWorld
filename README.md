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
 - *Performance*: faster calling of methods
 - _Reliability_: The compiler verifies the correctness of the program.
 - _Maintainability_: easy to read and to extend.
 
Most of the job is done by **type inference**.

It supports both functional and object-oriented programming. The characteristics
of functional programming are:
 - _First-class functions_ -- values can be passed by value.
 - _Immutability_ -- work with immutable when possible. This assures thread safety.
 - _No side effects_ -- no modification of elements outside the function.
 

Kotlin assures **complete interoperability** with Java. 

##### Compiling code 
The compiler analyses the source code and creates .class files.
Then, the procedure is identical to the Java compilation process.
The command for the compilation is:
```shell script
kotlinc source.kt -include-runtime -d jar_name.jar
```

The one to run the code:
```shell script
java -jar jar_name.jar
```

source -> .class -> .jar -> application (with Kotlin runtime).


Kotlin basics
-------------------------------------

### Function declaration
```kotlin
fun main (args: Array<String>){
    println("Hello world!")
}
```

- **fun** is the keyword used to declare a function.
- **println** stands for *System.out.println*.
- **Array** declares an array.

If a return value is needed, the returned type is written after a
column:


```kotlin
fun min (a: Int, b: Int): Int {
    return if (a < b) a else b
}
```
where the function *min* returns the minimum between two integers.
The inline syntax is possible since the *if* operator declares a block of code that
returns a value. Same as ternary operator in Java (where this if 
construction is not possible). _In Kotlin the if is an expression
(with return value): in Java, if is a statement
(without return value)._ Moreover:

 - **function return type** is mandatory when the function returns a value.
 - **return** statement is mandatory when a block body (code wrapped 
 between parenthesis) is used.
 - **return** can be omitted when an expression body is used. Type is 
 automatically inferred. i.e. 
 ```kotlin
fun min (a: Int, b: Int): Int = if (a < b) a else b
```

### Variables

How to declare variables:

```kotlin
val a = 5 //Int, type inference and initialization
val a: Int = 5 //Int explicit declaration and initialization
val a: Int // Int variable declaration, without initialization
val d = 7.5 // Double variable declaration and initialization
```

Kotlin distiguishes between _mutable_ and _immutable_ variables:
 - _immutable_ (val): cannot be modified,
 - _mutable_ (var): can be modified.
 
_Best practice_: use immutable objects as more as possible.

_Note_: even if the reference of an object is immutable (val),
the object pointed by that reference can be mutable. i.e.
```kotlin
val names = arrayListOf("Alice")
names.add("Naomi") // valid!!
```
 
 
### Classes
Classes work as in Java. Different is the definition of properties.
Example:
```kotlin
class Person (
    val name: String,
    var age: Int
)
```
In the definition of the _Person_ class, _name_ is a read-only String,
that can be accessed but not modified (as if it is final and only has getter), _age_ is a writable Int, 
that can be modified when required (as if it has getter and setter).
This declaration implies both the fields are private.

##### Custom accessors
This is how to create a custom implementation of a 
property accessor. It puts a _get()_ method followed by a block of
code implementing the logic under the declaration of the property.

```kotlin
class Person(
    val name: String,
    var age: Int
){
    val isOlderThan25: Boolean
        get() {
            return age > 25
        }
}
```

The previous code creates a property called _isOlderThan25_ that has
a custom getter. The value is not stored, but calculated on the fly.

##### Packages
The class and functions can be inserted into a package.
To create a package we need to declare at the beginning of the file,
the name of the package we are going to insert these elements in.
For example:

```kotlin
package persons

class Person(
    val name: String,
    var age: Int
){
    val isOlderThan25: Boolean
        get() {
            return age > 25
        }
}

fun sayHelloToPerson(val person: Person){
    println("Hello ${person.name}!")
}
```

We can access this functions and classes from another package in this way:
```kotlin
import persons.*

...
```

To conclude, in Kotlin you can put multiple classes in the same file.



 
 







 
