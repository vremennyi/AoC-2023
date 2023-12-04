private data class Part(
    val value: String,
    val range: IntRange,
    var isAdjacent: Boolean = false
) {
    val area: IntRange = range.first - 1..range.last + 1
}

private data class Symbol(
    val value: String,
    val pos: Int,
    var partCount: Int = 0,
    var ratio: Int = 1
)

fun main() {

    fun part1(input: List<String>): Int {
        val partList = ArrayList<List<Part>>()
        val symbolsList = ArrayList<List<Int>>()
        input.forEach { line ->
            val parts = "(\\d+)".toRegex()
                .findAll(line)
                .filter { it.groups.isNotEmpty() }
                .map { Part(it.groups.first()!!.value, it.range) }
                .toList()
            val symbols = "[^\\d.]".toRegex()
                .findAll(line)
                .map { it.groups.first()!!.range.first }
                .toList()
            partList.add(parts)
            symbolsList.add(symbols)
        }

        partList.forEachIndexed { line, parts ->
            symbolsList.subList((line - 1).coerceAtLeast(0), (line + 2).coerceAtMost(symbolsList.size))
                .flatten()
                .forEach { symbol ->
                    parts.forEach { part ->
                        if (part.area.contains(symbol)) {
                            part.isAdjacent = true;
                        }
                    }
                }
        }

        return partList.flatten().filter { it.isAdjacent }.sumOf { it.value.toInt() }
    }

    fun part2(input: List<String>): Int {
        val partList = ArrayList<List<Part>>()
        val symbolsList = ArrayList<List<Symbol>>()
        input.forEach { line ->
            val parts = "(\\d+)".toRegex()
                .findAll(line)
                .filter { it.groups.isNotEmpty() }
                .map { Part(it.groups.first()!!.value, it.range) }
                .toList()
            val symbols = "\\*".toRegex()
                .findAll(line)
                .map { Symbol("*", it.groups.first()!!.range.first) }
                .toList()
            partList.add(parts)
            symbolsList.add(symbols)
        }

        partList.forEachIndexed { line, parts ->
            symbolsList.subList((line - 1).coerceAtLeast(0), (line + 2).coerceAtMost(symbolsList.size))
                .flatten()
                .forEach { symbol ->
                    parts.forEach { part ->
                        if (part.area.contains(symbol.pos)) {
                            symbol.partCount++
                            symbol.ratio *= part.value.toInt()
                        }
                    }
                }
        }

        return symbolsList.flatten().filter { it.partCount == 2 }.sumOf { it.ratio }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
