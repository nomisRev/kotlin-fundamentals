package collections

class Member(val name: String, val age: Int)

fun names(members: List<Member>): List<String> = members.map { it.name }

fun adults(members: List<Member>): List<Member> = members.filter { it.age >= 18 }

fun letters(words: List<String>): List<Char> = words.flatMap { it.toList() }

fun numbers(input: List<String>): List<Int> = input.mapNotNull { it.toIntOrNull() }

fun byLength(words: List<String>): Map<Int, List<String>> = words.groupBy { it.length }

fun adultNamesByAge(members: List<Member>): List<String> =
  members.filter { it.age >= 18 }.sortedBy { it.age }.map { it.name }
