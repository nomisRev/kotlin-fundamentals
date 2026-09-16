package lambdas

val isEven: (Int) -> Boolean = { it % 2 == 0 }

val add: (Int, Int) -> Int = { a, b -> a + b }

val shout: String.() -> String = { uppercase() + "!" }

val len: (String) -> Int = String::length
