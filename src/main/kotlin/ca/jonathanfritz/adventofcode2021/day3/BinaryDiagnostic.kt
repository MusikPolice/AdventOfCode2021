package ca.jonathanfritz.adventofcode2021.day3

import ca.jonathanfritz.adventofcode2021.Utils
import java.util.function.BiFunction

class BinaryDiagnostic {
    fun part1(binary: List<String>) {
        // key is position, value is count
        val map: MutableMap<Int, Int> = HashMap()
        binary.forEach { diagnostic ->
            diagnostic.toCharArray()
                .map { it.digitToInt() }
                .forEachIndexed { position, bit ->
                    if (map.containsKey(position)) {
                        map[position] = map[position]!! + bit
                    } else {
                        map[position] = bit
                    }
                }
        }

        // gamma is made up of most common value of each position, and epsilon is its inverse
        val gamma = map.map { if (it.value > binary.size/2) { 1 } else { 0 } }
            .joinToString("")
            .toInt(2)
        val epsilon = map.map { if (it.value <= binary.size/2) { 1 } else { 0 } }
            .joinToString("")
            .toInt(2)

        println("Part1: Gamma is $gamma and Epsilon is $epsilon, Power Consumption is ${gamma * epsilon}")
    }

}

fun main() {
    val bd = BinaryDiagnostic()
    val binary = Utils.loadFromFile("day3.txt") { it }
    bd.part1(binary)
}