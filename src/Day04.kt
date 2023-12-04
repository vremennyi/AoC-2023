import kotlin.math.min
import kotlin.math.pow

private data class Card(
    val id: Int,
    val winNumbers: Set<String>,
    val foundNumbers: List<String>
) {
    fun matchCount(): Int {
        return foundNumbers.count { winNumbers.contains(it) }
    }

    fun points(): Int {
        if (matchCount() > 0) {
            return 2.0.pow(matchCount() - 1).toInt()
        }
        return 0
    }

    companion object {
        // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        fun parse(line: String): Card {
            val cardId = line.substringBefore(":")
                .split(" ")
                .last()
                .toInt()
            val values = line.substringAfter(":")
                .split("|")
            val winNumbers = values.first()
                .windowed(3, 3)
                .map { it.trim() }
                .toSet()
            val foundNumbers = values.last()
                .windowed(3, 3)
                .map { it.trim() }
                .toList()
            return Card(cardId, winNumbers, foundNumbers)
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val cards = input.map { Card.parse(it) }
        return cards.sumOf { it.points() }
    }

    fun part2(input: List<String>): Int {
        val cards = input.map { Card.parse(it) }
        val copies = HashMap<Int, Int?>()
        copies += cards.associate { it.id to 1 }
        cards.forEach { card ->
            val matchCount = card.matchCount()
            val multiplier = copies[card.id]
            for (i in card.id + 1..min(card.id + matchCount, cards.count() + 1)) {
                copies[i] = copies[i]?.plus(multiplier?:0)
            }
        }

        return copies.values.sumOf { it?:0 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
