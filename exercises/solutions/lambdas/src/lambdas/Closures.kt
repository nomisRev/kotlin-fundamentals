package lambdas

fun startsWith(prefix: String): (String) -> Boolean = { it.startsWith(prefix) }
