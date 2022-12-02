package io.tohuwabohu.aoc2022.day02

/**
 * --- Day 2: Rock Paper Scissors ---
 *
 * The Elves begin to set up camp on the beach. To decide whose tent gets to be closest to the snack storage, a giant
 * Rock Paper Scissors tournament is already in progress.
 *
 * Rock Paper Scissors is a game between two players. Each game contains many rounds; in each round, the players each
 * simultaneously choose one of Rock, Paper, or Scissors using a hand shape. Then, a winner for that round is selected:
 * Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock. If both players choose the same shape, the
 * round instead ends in a draw.
 *
 * Appreciative of your help yesterday, one Elf gives you an encrypted strategy guide (your puzzle input) that they say
 * will be sure to help you win. "The first column is what your opponent is going to play: A for Rock, B for Paper, and
 * C for Scissors. The second column--" Suddenly, the Elf is called away to help with someone's tent.
 *
 * The second column, you reason, must be what you should play in response: X for Rock, Y for Paper, and Z for Scissors.
 * Winning every time would be suspicious, so the responses must have been carefully chosen.
 *
 * The winner of the whole tournament is the player with the highest score. Your total score is the sum of your scores
 * for each round. The score for a single round is the score for the shape you selected (1 for Rock, 2 for Paper, and 3
 * for Scissors) plus the score for the outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you
 * won).
 */
val pointsMap = mutableMapOf(
    'X' to 1, // rock
    'Y' to 2, // paper
    'Z' to 3  // scissors
)

/**
 * Since you can't be sure if the Elf is trying to help you or trick you, you should calculate the score you would get
 * if you were to follow the strategy guide.
 *
 * For example, suppose you were given the following strategy guide:
 *
 * A Y
 * B X
 * C Z
 *
 * This strategy guide predicts and recommends the following:
 *
 *  * In the first round, your opponent will choose Rock (A), and you should choose Paper (Y). This ends in a win for
 *  you with a score of 8 (2 because you chose Paper + 6 because you won).
 * * In the second round, your opponent will choose Paper (B), and you should choose Rock (X). This ends in a loss for
 *  you with a score of 1 (1 + 0).
 * * The third round is a draw with both players choosing Scissors, giving you a score of 3 + 3 = 6.
 *
 * In this example, if you were to follow the strategy guide, you would get a total score of 15 (8 + 1 + 6).
 *
 * What would your total score be if everything goes exactly according to your strategy guide?
 */
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

/**
 * The Elf finishes helping with the tent and sneaks back over to you. "Anyway, the second column says how the round
 * needs to end: X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
 * Good luck!"
 *
 * The total score is still calculated in the same way, but now you need to figure out what shape to choose so the round
 * ends as indicated. The example above now goes like this:
 *
 * * In the first round, your opponent will choose Rock (A), and you need the round to end in a draw (Y), so you also
 *  choose Rock. This gives you a score of 1 + 3 = 4.
 * * In the second round, your opponent will choose Paper (B), and you choose Rock so you lose (X) with a score of
 *  1 + 0 = 1.
 * * In the third round, you will defeat your opponent's Scissors with Rock for a score of 1 + 6 = 7.
 *
 * Now that you're correctly decrypting the ultra top secret strategy guide, you would get a total score of 12.
 *
 * Following the Elf's instructions for the second column, what would your total score be if everything goes exactly
 * according to your strategy guide?
 */
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
