package Day15

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream

/**
 * Today, you set out on the task of perfecting your milk-dunking cookie recipe. All you have to do is find the right
 * balance of ingredients.
 *
 * Your recipe leaves room for exactly 100 teaspoons of ingredients. You make a list of the remaining ingredients you
 * could use to finish the recipe (your puzzle input) and their properties per teaspoon:
 *
 *      - capacity (how well it helps the cookie absorb milk)
 *      - durability (how well it keeps the cookie intact when full of milk)
 *      - flavor (how tasty it makes the cookie)
 *      - texture (how it improves the feel of the cookie)
 *      - calories (how many calories it adds to the cookie)
 *
 * You can only measure ingredients in whole-teaspoon amounts accurately, and you have to be accurate so you can
 * reproduce your results in the future. The total score of a cookie can be found by adding up each of the properties
 * (negative totals become 0) and then multiplying together everything except calories.
 *
 * Part 1
 *
 * Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie
 * you can make?
 *
 * Part 2
 *
 * Your cookie recipe becomes wildly popular! Someone asks if you can make another recipe that has exactly 500 calories
 * per cookie (so they can use it as a meal replacement). Keep the rest of your award-winning process the same (100
 * teaspoons, same ingredients, same scoring system).
 *
 * Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie
 * you can make with a calorie total of 500?
 *
 * Created by Simon on 16/12/2015.
 */

data class Ingredient(val name: String, val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int, var amount: Int = 0)

class December15 {
    fun main() {
        val lines = loadFile("src/Day15/15.dec_input.txt")
        var ingredientList = arrayListOf<Ingredient>()

        for (line in lines) {
            val (name, ingredients) = line.split(":")

            if (name !is String || ingredients !is String) return

            val (capacity, durability, flavor, texture, calories) = ingredients.replace(Regex(" \\w+ "), "").split(",")
            if (capacity !is String || durability !is String || flavor !is String || texture !is String || calories !is String) return

            ingredientList.add(Ingredient(name, capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(), calories.toInt()))
        }

        val combinations = arrayListOf<List<Int>>()
        val counts = ingredientList.map { i -> 0 }.toArrayList()

        combinations(100, 0, combinations, counts)

        val answer1 = combinations
                .asSequence()
                .map { amounts -> sumAll(ingredientList, amounts) }
                .max()
        val answer2 = combinations
                .asSequence()
                .filter { amounts -> sum(ingredientList, amounts, { i: Ingredient -> i.calories }) == 500 }
                .map { amounts -> sumAll(ingredientList, amounts) }
                .max();

        println("The best cookie is has a score of $answer1")
        println("The best cookie with exactly 500 calories has a score of $answer2")
    }

    private fun combinations(target: Int, index: Int, results: ArrayList<List<Int>>, list: ArrayList<Int>) {
        for (i in 0..target) {
            val newList = list.toArrayList()
            newList[index] = i
            if (index < list.size - 1 && newList.subList(0, index).asSequence().sumBy { v -> v } <= target) {
                combinations(target, index + 1, results, newList)
            }
            if (index == list.size - 1 && newList.asSequence().sumBy { v -> v } == target) {
                results.add(newList)
            }
        }
    }

    fun sumAll(list: List<Ingredient>, amounts: List<Int>): Int {
        return sum(list, amounts, { i: Ingredient -> i.capacity }) *
                sum(list, amounts, { i: Ingredient -> i.durability }) *
                sum(list, amounts, { i: Ingredient -> i.flavor }) *
                sum(list, amounts, { i: Ingredient -> i.texture })
    }

    fun sum(list: List<Ingredient>, amounts: List<Int>, property: (Ingredient) -> Int): Int {
        var sum = 0
        for (i in 0..list.size - 1) {
            sum += property.invoke(list[i]) * amounts[i]
        }
        return Math.max(sum, 0)
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path)
        val reader = Files.newBufferedReader(input)
        return reader.lines()
    }

}

fun main(args: Array<String>) {
    December15().main()
}