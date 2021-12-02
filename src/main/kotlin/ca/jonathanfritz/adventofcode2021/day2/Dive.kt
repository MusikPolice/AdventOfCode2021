package ca.jonathanfritz.adventofcode2021.day2

import ca.jonathanfritz.adventofcode2021.Utils

class Dive {
    fun part1(instructions: List<Pair<String, Int>>) {
        var position = Position()
        instructions.forEach {
            position = when (it.first) {
                "forward" -> position.copy(horizontal = position.horizontal + it.second, depth = position.depth)
                "down" -> position.copy(horizontal = position.horizontal, depth = position.depth + it.second)
                "up" -> position.copy(horizontal = position.horizontal, depth = position.depth - it.second)
                else -> position
            }
        }
        print("Part 1: Horizontal position is ${position.horizontal}, vertical position is ${position.depth}, product is ${position.horizontal * position.depth}")
    }
}

data class Position(val horizontal: Int = 0, val depth: Int = 0)

fun main() {
    val dive = Dive()
    val instructions = Utils.loadFromFile("day2.txt") {
        val tokens = it.split(" ")
        val direction = tokens[0]
        val amount = tokens[1].toInt()
        direction to amount
    }
    dive.part1(instructions)
}