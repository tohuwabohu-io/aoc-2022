package aoc

import java.math.BigDecimal

fun calc01(): List<BigDecimal> {
    val reader = util.getBufferedReader("src/main/resources/aoc01.txt");

    val elfCaloriesMap = mutableMapOf<Number, BigDecimal>();
    var elfIndex = 0;

    for (line in reader.lineSequence().iterator()) {
        if ("" == line) {
            elfIndex++
        } else {
            var calories = elfCaloriesMap[elfIndex]

            if (calories == null)
                calories = BigDecimal.ZERO

            calories = calories!!.add(line.toBigDecimal())

            elfCaloriesMap[elfIndex] = calories
        }
    }

    val totalCalories = elfCaloriesMap.values.toMutableList().sortedDescending();
    println(totalCalories)

    return totalCalories
}