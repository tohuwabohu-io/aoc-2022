package aoc

import java.math.BigDecimal

class Day01 {
    companion object {
        fun part01(): List<BigDecimal> {
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

            return totalCalories
        }

        fun part02(): BigDecimal {
            return this.part01()
                .subList(0, 3)
                .fold(BigDecimal.ZERO, BigDecimal::add)
        }
    }
}
