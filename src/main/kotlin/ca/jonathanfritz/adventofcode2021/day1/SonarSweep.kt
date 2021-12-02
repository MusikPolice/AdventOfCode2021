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

    fun part2(depths: List<Int>) {
        var count = 0;
        depths.forEachIndexed { i, _ ->
            if (i+3 >= depths.size) return@forEachIndexed
            val sw1 = depths[i] + depths[i+1] + depths[i+2]
            val sw2 = depths[i+1] + depths[i+2] + depths[i+3]
            if (sw2 > sw1) {
                count++
            }
        }
        println("Part 2: $count sums are larger than the previous sum")
    }
}

fun main() {
    val sonarSweep = SonarSweep()
    val depths = Utils.loadFromFile("day1.txt") { it.toInt() }
    sonarSweep.part1(depths)
    sonarSweep.part2(depths)
}