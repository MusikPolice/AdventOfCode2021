package ca.jonathanfritz.adventofcode2021.day4

import ca.jonathanfritz.adventofcode2021.Utils

class GiantSquid {
    fun part1(numbers: List<Int>, boards: List<BingoBoard>) {
        // play bingo
        var winner: BingoBoard? = null
        var lastNumber = 0
        for (number in numbers) {
            println("Called $number")
            lastNumber = number
            for ((i, board) in boards.withIndex()) {
                board.call(number)
                if (board.hasBingo()) {
                    println("Board ${i + 1} has a Bingo")
                    winner = board
                    break
                }
            }
            if (winner != null) {
                break
            }
        }

        // find the score of the winning board
        val score = (winner?.sumOfUnmarkedNumbers() ?: 0) * lastNumber
        println("Final score is $score")
    }

    fun part2(numbers: List<Int>, boards: MutableList<BingoBoard>) {
        // play bingo
        var lastWinner: BingoBoard? = null
        var lastNumber = 0
        for (number in numbers) {
            println("Called $number")
            for ((i, board) in boards.withIndex()) {
                if (!board.hasBingo()) {
                    board.call(number)
                    if (board.hasBingo()) {
                        println("Board ${i + 1} has a Bingo")
                        lastWinner = board
                        lastNumber = number
                    }
                }
            }
        }

        // find the score of the winning board
        val score = (lastWinner?.sumOfUnmarkedNumbers() ?: 0) * lastNumber
        println("Last winner is Board ${boards.indexOf(lastWinner) + 1}. Final score is $score")
    }
}

class BingoBoard(
    private val spaces: List<Int>,
    private val board: Array<Array<Boolean>> = Array(5) {Array(5) {false} }
) {

    fun call(number: Int) {
        val index = spaces.indexOf(number)
        if (index == -1) return
        val row = index / 5
        val col = index % 5
        board[row][col] = true
    }

    fun hasBingo(): Boolean {
        // check each row
        for (row in 0..4) {
            var rowBingo = true
            for (col in 0..4) {
                rowBingo = rowBingo && board[row][col]
                if (!rowBingo) break
            }
            if (rowBingo) return true;
        }

        // check each column
        for (col in 0..4) {
            var colBingo = true
            for (row in 0..4) {
                colBingo = colBingo && board[row][col]
                if (!colBingo) break
            }
            if (colBingo) return true
        }

        return false
    }

    fun sumOfUnmarkedNumbers(): Int {
        var sum = 0
        for ((index, number) in spaces.withIndex()) {
            val row = index / 5
            val col = index % 5
            if (!board[row][col]) {
                sum += number
            }
        }
        return sum
    }

    fun reset() {
        for (row in 0..4) {
            for (col in 0..4) {
                board[row][col] = false
            }
        }
    }
}

fun main() {
    val bd = GiantSquid()
    val numbers: MutableList<Int> = ArrayList()
    val boards: MutableList<BingoBoard> = ArrayList()

    var boardLines: MutableList<Int> = ArrayList()
    Utils.loadFromFile("day4.txt") { it }.forEachIndexed{ i, line ->
        if (i == 0) {
            numbers.addAll(line.split(",")
                .map { it.toInt() })
        } else if (line.isNotBlank()) {
            // board line
            boardLines.addAll(line.split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() })
        } else if (boardLines.isNotEmpty()) {
            // space between boards
            boards.add(BingoBoard(boardLines))
            boardLines = ArrayList()
        }
    }
    if (boardLines.isNotEmpty()) {
        boards.add(BingoBoard(boardLines))
    }

    bd.part1(numbers, boards)
    boards.forEach { it.reset() }
    bd.part2(numbers, boards)
}