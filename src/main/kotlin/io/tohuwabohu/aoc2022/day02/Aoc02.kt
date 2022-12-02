package io.tohuwabohu.aoc2022.day02

val pointsMap = mutableMapOf(
    'X' to 1, // rock
    'Y' to 2, // paper
    'Z' to 3  // scissors
)

fun part01(): Number {
    val combinationPointsMap = hashMapOf(
        'X' to hashMapOf(
            'A' to 3, // rock -> rock
            'B' to 0, // rock -> paper
            'C' to 6  // rock -> scissors
        ),
        'Y' to hashMapOf(
            'A' to 6, // paper -> rock
            'B' to 3, // paper -> paper
            'C' to 0, // paper -> scissors
        ),
        'Z' to hashMapOf(
            'A' to 0, // scissors -> rock
            'B' to 6, // scissors -> paper
            'C' to 3  // scissors -> scissors
        )
    )

    var score = 0;

    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc02.txt");

    reader.lines().forEach {
        score += pointsMap[it[2]]!!
        score += combinationPointsMap[it[2]]!![it[0]]!!
    }

    return score;
}

fun part02(): Number {
    val gameStrategy = hashMapOf(
        'X' to hashMapOf( // lose
            'A' to 'Z',   // rock     -> scissors
            'B' to 'X',   // paper    -> rock
            'C' to 'Y'    // scissors -> paper
        ),
        'Y' to hashMapOf( // draw
            'A' to 'X',   // rock     -> rock
            'B' to 'Y',   // paper    -> paper
            'C' to 'Z'    // scissors -> scissors
        ),
        'Z' to hashMapOf( // win
            'A' to 'Y',   // rock     -> paper
            'B' to 'Z',   // paper    -> scissors
            'C' to 'X'    // scissors -> rock
        )
    )

    val strategyPoints = hashMapOf(
        'X' to 0,
        'Y' to 3,
        'Z' to 6
    )

    var score = 0;

    val reader = io.tohuwabohu.aoc2022.util.getBufferedReader("src/main/resources/aoc02.txt");

    reader.lines().forEach {
        val shape = gameStrategy[it[2]]!![it[0]]

        score += pointsMap[shape]!!
        score += strategyPoints[it[2]]!!
    }

    return score;
}
