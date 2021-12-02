package ca.jonathanfritz.adventofcode2021.day1

import ca.jonathanfritz.adventofcode2021.Utils

class SonarSweep {

    fun part1(depths: List<Int>) {
        var count = 0
        depths.forEachIndexed { i, depth ->
            if ( i > 0 && depth > depths[i - 1]) {
                count++
            }
        }
        println("Part 1: $count measurements are larger than the previous measurement")
    }
}

fun main() {
    val sonarSweep = SonarSweep()
    sonarSweep.part1(
        Utils.loadFromFile("day1.txt") { it.toInt() }
    )
}