private enum class Digits(val text: String, val value: Int) {
    ONE("one", 1),
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9),
}

fun main() {
    fun calcCalibration(s: String): Int {
        return 10 * s.first { it.isDigit() }.digitToInt() + s.last { it.isDigit() }.digitToInt()
    }

    fun extractNumbers(s: String): String {
        val numbers = StringBuilder()
        s.windowed(5, 1, true)
            .forEach { sub ->
                if (sub.first().isDigit()) {
                    numbers.append(sub.first())
                } else {
                    Digits.entries.forEach { digit ->
                        if(sub.startsWith(digit.text)) {
                            numbers.append(digit.value)
                        }
                    }
                }
            }
        return numbers.toString()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { calcCalibration(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { extractNumbers(it) }
            .sumOf { calcCalibration(it) }
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")

    part1(input).println()

    testInput = readInput("Day01_test2")
    check(part2(testInput) == 281)
    part2(input).println()
}
