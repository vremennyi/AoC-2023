enum class Color {
    RED,
    GREEN,
    BLUE;

    companion object {
        fun findByName(input: String): Color {
            return entries.first { it.name.equals(input, true) }
        }
    }
}

data class Game(
    val id: Int,
    val draws: List<Map<Color, Int>> = ArrayList()
) {
    companion object {
        fun of(input: String): Game {
            val id = input.substringBefore(":")
                .substringAfter(" ")
                .toInt()
            val draws = input.substringAfter(":")
                .split(";")
                .map { draw ->
                    draw.split(", ")
                        .associate { group ->
                            val (count, countColor) = group.trim().split(" ")
                            val cubeColor = Color.findByName(countColor)
                            cubeColor to count.toInt()
                        }

                }
            return Game(id, draws)
        }
    }

    fun isPossible(red: Int, green: Int, blue: Int): Boolean {
        return draws.all {
            it.getOrDefault(Color.RED, 0) <= red
                    && it.getOrDefault(Color.GREEN, 0) <= green
                    && it.getOrDefault(Color.BLUE, 0) <= blue
        }
    }

    fun getPower(): Int {
        val red = draws.maxOf { it.getOrDefault(Color.RED, 0) }
        val green = draws.maxOf { it.getOrDefault(Color.GREEN, 0) }
        val blue = draws.maxOf { it.getOrDefault(Color.BLUE, 0) }
        return red * green * blue
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val gameList: List<Game> = input.map { Game.of(it) }

        return gameList.filter { it.isPossible(12, 13, 14) }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        val gameList: List<Game> = input.map { Game.of(it) }

        return gameList.sumOf { it.getPower() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
