package ca.jonathanfritz.adventofcode2021.day3

import ca.jonathanfritz.adventofcode2021.Utils

class BinaryDiagnostic {
    fun part1(binary: List<List<Int>>) {
        // key is position, value is count
        val positionCounts: MutableMap<Int, Int> = HashMap()
        binary.forEach { diagnostic ->
            diagnostic.forEachIndexed { position, bit ->
                    if (positionCounts.containsKey(position)) {
                        positionCounts[position] = positionCounts[position]!! + bit
                    } else {
                        positionCounts[position] = bit
                    }
                }
        }

        // gamma is made up of most common value of each position, and epsilon is the inverse
        val gamma = positionCounts.map { if (it.value > binary.size/2) { 1 } else { 0 } }
            .joinToString("")
            .toInt(2)
        val epsilon = positionCounts.map { if (it.value <= binary.size/2) { 1 } else { 0 } }
            .joinToString("")
            .toInt(2)

        println("Part1: Gamma is $gamma and Epsilon is $epsilon, Power Consumption is ${gamma * epsilon}")
    }

    fun part2(binary: List<List<Int>>) {
        // iteratively filter a copy of the list to find the oxygen generator rating
        val oxygenGeneratorRating = findRating(binary) { numOnes, numZeros ->
            if (numOnes >= numZeros) 1 else 0
        }

        // iteratively filter a copy of the list to find the CO2 scrubber rating
        val co2ScrubberRating = findRating(binary) { numOnes, numZeros ->
            if (numOnes >= numZeros) 0 else 1
        }

        println("Part 2: Oxygen Generator Rating is $oxygenGeneratorRating, CO2 Scrubber Rating is $co2ScrubberRating, " +
                "Life Support Rating is ${oxygenGeneratorRating * co2ScrubberRating}")
    }

    private fun findRating(binary: List<List<Int>>, criteria: (numOnes:Int, numZeroes:Int) -> Int): Int {
        var position = -1
        var remaining = binary
        do {
            position++
            val numZeros = remaining.filter { it[position] == 0 }.size
            val numOnes = remaining.filter { it[position] == 1 }.size
            val bitCriteria = criteria.invoke(numOnes, numZeros)
            remaining = remaining.filter { it[position] == bitCriteria }
        } while(remaining.size > 1)
        return remaining[0].joinToString("").toInt(2)
    }
}

fun main() {
    val bd = BinaryDiagnostic()
    val binary = Utils.loadFromFile("day3.txt") { line ->
        line.toCharArray().map { char -> char.digitToInt() }
    }
    bd.part1(binary)
    bd.part2(binary)
}