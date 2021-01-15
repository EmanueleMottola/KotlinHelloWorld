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

// ...
```

To conclude, in Kotlin you can put multiple classes in the same file.



### Enum
Enum classes work as in Java, they give a fixed range of values.

```kotlin
enum class Color(
    val r: Int, val g: Int, val b: Int
) {
    RED(255, 0, 0), ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), WHITE(255, 255, 255); 
    // this semicolon in important

    fun findrgb() = r * b * g

}
```
In the block we have created some enum constants, that must have 
the property values set, following the declaration of the Enum class.

##### The _when_ expression

It takes the place of the _switch_ statement. Combined with the Enum 
class, it can be used to return a value based on the constant one.
For example:

```kotlin
fun getNumber(color: Color){
    when (color){
        Color.RED -> "red"
        Color.ORANGE -> "orange"
        Color.YELLOW -> "yellow"
    }
}

fun getifWarm (color: Color){
    when (color){
            Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
        }
}
```

_when_ may also have different objects as parameter. 
In that case, for example:
```kotlin
fun mix (color1: Color, color2: Color){
    when (setOf(color1, color2)){
        setOf(Color.RED, Color.YELLOW) -> "ORANGE"
        setOf(Color.YELLOW, Color.ORANGE) -> "DARKER YELLOW"
        else -> throw Exception("wrong colors")
    }
}
```

_when_ can be used without a parameter, too.
```kotlin
fun mixOptimized(c1: Color, c2: Color) =
    when {
        (c1 == RED && c2 == YELLOW) ||
        (c1 == YELLOW && c2 == RED) ->
            "ORANGE"
        (c1 == YELLOW && c2 == ORANGE) ||
        (c1 == ORANGE && c2 == YELLOW) ->
            "DARKER YELLOW"
        else -> throw Exception("Dirty color")
}
```
This other version of the previous snippet is more efficient than the previous one,
and used in case the function is called multiple times: it avoids 
the creation of multiple _sets_ for the comparison. The drawback is 
that it's harder to read.


### Smart Casts

It is possible to combine type checks and cast. When we check a 
variable with the **is** keyword in the if clause, consequently the 
variable assumes the type of the checked type. For example:

```kotlin
if (e is Int) {
    println ( e.value ) // here e is of type Int
}
```
Consequently, no explicit cast is necessary, and the variable is
easily casted to the required type.

Condition when this is possible:
 - the variable is declared as _val_ i.e. read-only.
 
In case it is not a _val_ variable, the explicit cast is required,
and consequently we need:
```kotlin
val n = e as Int
```

### Loops and Ranges

Kotlin does not provide normal for loop like in Java. The classic
syntax is 
```kotlin
for (book in books){
    // do something
}
```
In the previous snippet of code, the for-each loop is used.
This is mainly used to iterate over collections.

Kotlin provides also a _while_ loop, identical to Java.

Anyway, to emulate the C style for loop, it provides the **range** 
element. A range is an interval between two values, using the _.._ operator.
For example:
```kotlin
for (i in 1..100){
    // do something with i
}
```
_Note_: in the previous code snippet, the values 1 and 100 are included, 
so the range is _closed_. This synta also works for characters not
only with integers.

In case a range going 'backward', the _downTo_ keyword comes into play.
When it is necessary to create a range where the step is not + or - 1,
the keyword _step_ has to be used.
Example:
```kotlin
for (i in 100 downTo 1 step 4){
    // do something with i
}
```

The _in_ keyword checks whether an element belongs to a collection
or not. For example:

```kotlin
fun isLetter(a: Char) = a in 'a'..'z' || a in 'A'..'Z'
fun isDigit(n: Int) = n !in 0..9
```

_Note_: This operator can be used with any object created out of a class
implementing the _java.lang.Comparable_ interface.

### Exceptions

Exceptions are handled in a very similar way to Java. 
It is not possible to insert the _throws_ keyword after the functions declaration.
The tip to handle this is to use documentation. In case you want to use _try with resources_
it is necessary to use the _try_ version that uses brackets: in this
case the expression can return automatically a value.
No other difference from Java.


## Collections

- Set creation:
```kotlin
val set = hashSetOf(1, 7, 53)
```

- List creation:
```kotlin
val list = arrayListOf(1, 5, 4)
```

- Map creation:
```kotlin
val map = hashMapOf(1 to "one", 7 to "seven", 8 to "eight")
```

How to call functions more easily?
Let's suppose we want to print the elements of a collections.
Usually the procedure would be the following:
```kotlin
val list  = arrayListOf(1, 5, 7)
println(list) // here .toString()
```

The output is always standard. What if we want to change it?

In this case we need to specify a custom function to deal with it.
For example:
```kotlin
fun <T> joinString(
    collection: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String,
){
    val result = StringBuilder(prefix)
    
    for ((index, el) in collection){
        if (index > 0 ) result.append(separator)
        result.append(el)
    }
    
    result.append(postfix)
    return result.toString()
}
```
The previous snippet of code creates a custom printing style.

But it could be hard to read and understand the function
when it's called.
So the solution are:
- named argument.
- default parameter values. 

#### Tip and tricks to organize functions
In Kotlin it is not necessary that the functions belong to a class.
It is possible to declare them at the beginning of the file.

For example, file _join.kt_ contains after the package declaration 
the list of functions:
```kotlin
package strings

fun joinToString(): String {}
```

If we use only Kotlin, this is all. In case of interaction with Java,
we need to understand the corresponding matching:

```java
/*Java*/
package strings;

public class JoinKt {
    public static String joinToString(...) {...}
}
```

All the functions declared at the beginning of the file in Kotlin 
are mapped to static methods belonging to the class with name 
equal to file's name. Then, we can directly access them as static 
methods.

In the same way, **properties** will be accessed as global variables:
val as read-only, var as a modifiable variable.


### Extension functions and properties
To extend the classes already defined by other developers, Kotlin provides
the possibility to write extension functions. 

The idea is to write in the current file,utility functions
 to a class without accessing the source file of the class itself.
This is very flexible.
For example:
```kotlin
package strings

fun String.lastChar(): Char = this.get(this.length - 1)
``` 

In the previous snippet of code, String is the class we want to extend,
while this is the object the function will be called on. Even if the
function is implemented in the current file, it is necessay to 
import it in order to use it.

String is called the **receiver type** and 'this' is called the 
**receiver object**. 

So for example, this extension functions are used to provide utilities:

```kotlin
fun <T> Collection<T>.joinToString(
    separator: String,
    prefix:String,
    postfix: String
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex())
    if (index > 0) result.append(separator)
    result.append(element)
    }
    result.append(postfix)
    return result.toString()
}
```

This can be used somewhere else in the code as:
```kotlin
val list = listOf(1, 3, 5)
println(list.joinToString(";", "(", ")"))
```

### Function override
```kotlin
open class View {
open fun click() = println("View clicked")
}

class Button: View() {
override fun click() = println("Button clicked")
}
```

Button extends View. This is how to override _click_ function.

Now let's suppose we:
```kotlin
val a: View = Button()
a.click() // "Button clicked"
```
Here the method of the sub-class is called. 
If we do the same, but the overridden function is an extension function,
the function that will be called is the one of View, consequently 
displaying "View clicked".

#### Varargs
It is possible to use varargs in Kotlin. To do it:
- put the keyword _vararg_ before the name of the parameter.
- use the spread operator * before the parameter when passing it
as argument calling the function.

For example:
```kotlin
fun printList<T>(vararg values: T){
    for (value in values)
        println(value)
}
```

where vararg has been used. Moreover, to call it:
```kotlin
val list = listOf(1, 3, 5)
printList(*list)
```

### Nested functions
In order to improve readability, Kotlin allows developers to write functions
inside other functions.
For example:
```kotlin
class User(val id: Int, val name: String, val address: String)
fun saveUser(user: User) {
    if (user.name.isEmpty()) {
        throw IllegalArgumentException(
            "Can't save user ${user.id}: empty Name")
    }
    if (user.address.isEmpty()) {
        throw IllegalArgumentException(
        "Can't save user ${user.id}: empty Address")
    }
    // Save user to the database
}
```

You can see the block of code for the check is essentially repeated.

To improve it, you can do:
```kotlin
fun saveUser(user: User) {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
            "Can't save user ${user.id}: " +
            "empty $fieldName")
        }
    }
    validate(user.name, "Name")
    validate(user.address, "Address")
// Save user to the database
}
```

And even further, making the _saveUser()_ function as an extension function.
Hence becoming _fun User.validateBeforeSave()_.

As a consequence:
- Logic is not duplicated
- Inner class can access any member of the nesting class.


## Interfaces

Interfaces can contain:
- definition of abstract methods
- implementations of non-abstract methods
- cannot contain states.
- interfaces do not use _open_, _final_ or _abstract_.

```kotlin
interface Drawable(){
    fun draw() // abstract method
    fun showOff() = println("I am drawable!") // non-abstract method with defaut implementation
}
```

To implement the interface:
```kotlin
class Image : Drawable {
    override fun draw() = println("I was drawn!")
}
```

_Note_: A class can implement as many interfaces as wanted, but only
one class.

There is no need to mark a default function with the default keyword.

Let's suppose there is another interface:

```kotlin
interface Pushable(){
    fun showOff() = println("I am pushable")
}
```

When you implement both the interfaces:
```kotlin
class Image: Drawable, Pushable{
    override fun showOff(){
        super<Drawable>.showOff()
        super<Pushable>.showOff()
    }
}
```

### The fragile base class problem
When a class is extended by others, it is possible that a modification in
the behavior of the base one can affect the others. In this terms,
it is necessary to provide a solution:

- document which methods and classes can be extended and overridden.
- prohibit inheritance when you don't want it to happen.

For this reasons, Kotlin declares automatically all the classes as
**final** by default.

If you want to allow the creation of subclasses, use 
**open** modifier to every property which can be overridded.

For example:
```kotlin
open class RichImage : Drawable {
    fun cancel() {} // final by default, can't override
    open fun animate() {} // can be overridden, it's open
    override fun draw() {} // can be further overridden, it's open by default
}
```

With the open modifier in the example above we can extend the class,
override _animate()_ and also we can override _draw()_, since the 
override modifier makes the field _open_ by default.

Consequently if we don't want it to be overridden further, 
we need to declare it as _final_.

```kotlin
open class RichImage : Drawable {
    fun cancel() {} // final by default, can't override
    open fun animate() {} // can be overridden, it's open
    final override fun draw() {} // can't be further overridden, it's final
}
```

##### Abstract modifier

When declaring a class as **abstract**:
- you can't instantiate it

When applied to members:
- they have to be overridden

To conclude, the modifiers mean:
- **final**: can't be overridden, used by default in class members.
- **open**: can be overridden, explicitly specified.
- **abstract**: must be overridden, can be used only in abstract classes, abstract members do not need to have an implementation.


##### Visibility modifiers

Kotlin uses public by default. The other modifiers are the same as
in Java: public, protected and private.
Moreover, _internal_ is a modifier that allows the data to be visible inside
the module. 
_private_ fields are visible only inside the file where they are defined.

### Nested and Inner classes

In Java **nested** classes are classes that are defined inside another class.
The purpose of a nested class is to clearly group the nested 
class with its surrounding class, signaling that these two 
classes are to be used together. Or perhaps that the nested
class is only to be used from inside its enclosing (owning) class.

**Inner** classes are nested classes that store a reference to the outer
class. Nested classes which are not inner, do not store a reference to outer data.

For example:
```kotlin
class Button : View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) { /*...*/ }
    class ButtonState : State { /*...*/ } // static nested class, does not refer to outer class data
}
```

To declare a nested class as Inner, we need to use the _inner_ modifier:
```kotlin
class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}
```

#### Sealed classes
Sealed classes are implemented when you want to create a fixed bunch 
of classes that extend the base class, and no more.
In this case the base class is declared as **sealed**, and the extending 
classes are directly declared within the class itself.

For example:
```kotlin
sealed class Expr {
    class Num(val value: Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}
```

In this case, you can't create further classes out of that base one.

### Class constructors and initializer blocks

Class constructors, different ways to write the same thing:
```kotlin
class User(val nickname: String)

class User constructor(_nickname: String) {
    val nickname: String
    init {
        nickname = _nickname
    }
}

class User(_nickname: String) {
val nickname = _nickname
}
```

In the previous code snippet, they all do the same.

Default values can also be inserted, like:
```kotlin
class User( val nickname: String,
            val isSubscribed: Boolean = true)
```
TIP: In this case, you need to use the default values instead of secondary
constructors.
In case the class is an extension of another one, we need to
provide the parameters of the base class, too.
```kotlin
class TwitterUser(nickname: String) : User(nickname) { ... }
```
_Note_: there is a basic difference with the interfaces. Interfaces
do not have constructors.

Now, let's focus on secondary constructors. The followin snippet
of code, does not declare a primary constrcutor, and you can say that, since 
there is no parenthesis after the class declaration.
Consequently, there is only the secondary constructor in place: 
```kotlin
open class View {
    constructor(ctx: Context) {
        // some code
    }
    constructor(ctx: Context, attr: AttributeSet) {
        // some code
    }
}
```

To extend this class:
```kotlin
class MyButton : View {
    constructor(ctx: Context)
        : super(ctx) {
        // ...
    }
    constructor(ctx: Context, attr: AttributeSet)
        : super(ctx, attr) {
        // ...
    }
}
```

In the previous snippet, we need to use super to execute also the
constructors coming from the base class.


##### Implementing propertiesdeclared in interfaces
Interfaces can contain abstract properties declarations:
```kotlin
interface User {
    val nickname: String //abstract property declaration
}
```

Classes implementing _User_, how can they provide a way to obtain
the value of _nickname_?
Keep in mind that the interface cannot store any value in the
state. Anyway, there are three ways:

```kotlin
// primary constructor implements the nickname property, 
// where the state is stored, hence marked as override
class PrivateUser(override val nickname: String) : User

// it does not have a backing field. It invokes the getter every
// time the field is required
class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBefore('@') )
    }

// it has a backing field. The function is invoked only once and
// the value is stored in there.
class FacebookUser(val accountId: Int) : User {
    override val nickname = getFacebookName(accountId)
}
```

Other alternative, is to implement with a custom getter directly in
the interface: this is the behavior of the second case.

And consequently:
```kotlin
interface User {
    val email: String
    val nickname: String
        get() = email.substringBefore('@')
}
```

The first property must be overridden in the implementing class,
the second does not need it.

It is possible to implement a logic every time that a field is implemented,
for example:

```kotlin
class User(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println("""
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent())
                field = value
        }
}
```

_field_ is used to update the _val address_. It is a reference to the
property.


### Data classes

Review of _Universal object methods_:
- toString(): string representation of the object.
- equals(): method to state whether two objects are equal. You can call it using '=='
- hashCode(): computes the hashCode of the object. 
For using objects as keys in hash-based containers such as HashMap

Data classes implement them automatically.

Moreover:
- provides _copy()_ method to copy an object, encouraging the usage of immutables.


### Class delegation
Problem: projects grow and base classes are modified, affecting the 
child-classes, too.
Usually the _Decorator_ pattern is used: the extending class includes
an instance of the base class to exploit its functionalities.

For example:
```kotlin
class DelegatingCollection<T> : Collection<T> {
    private val innerList = arrayListOf<T>()
    override val size: Int get() = innerList.size
    override fun isEmpty(): Boolean = innerList.isEmpty()
    override fun contains(element: T): Boolean = innerList.contains(element)
    override fun iterator(): Iterator<T> = innerList.iterator()
    override fun containsAll(elements: Collection<T>): Boolean =
    innerList.containsAll(elements)
}
```

But Kotlin can write all this code automatically, introducing the
**class delegation**.
Consequently, the same can be done simply writing:
```kotlin
class DelegatingCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList {}
```

In case we want to add functionalities to the new class:
```kotlin
class CountingSet<T>(
    val innerSet: MutableCollection<T> = HashSet<T>()
    ) : MutableCollection<T> by innerSet {
    
    var objectsAdded = 0
    
    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean {
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}
```

In the previous snippet of code, the methods are overridden, but actually
exploiting the methods previously created by the base class. 
The important part is that you aren’t introducing any dependency on how the
underlying collection is implemented.


### The 'object' keyword













 
