fun main (args: Array<String>){
    println("Hello world!")
    println("The max is " + max(5, 7))
}

// write a function returning the max
fun max (a: Int, b: Int): Int {
    return if (a > b) a else b
}