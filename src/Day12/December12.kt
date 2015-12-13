package Day12

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.text.Regex

/**
 * Santa's Accounting-Elves need help balancing the books after a recent order. Unfortunately, their accounting
 * software uses a peculiar storage format. That's where you come in.
 *
 * They have a JSON document which contains a variety of things: arrays ([1,2,3]), objects ({"a":1, "b":2}), numbers,
 * and strings. Your first job is to simply find all of the numbers throughout the document and add them together.
 *
 * You will not encounter any strings containing numbers.
 *
 * Part 1
 *
 * What is the sum of all numbers in the document?
 *
 * Part 2
 *
 * Uh oh - the Accounting-Elves have realized that they double-counted everything red.
 *
 * Ignore any object (and all of its children) which has any property with the value "red". Do this only for objects
 * ({...}), not arrays ([...]).
 *
 * Created by Simon on 13/12/2015.
 */
class December12 {
    fun part1() {
        val json = loadFile("src/Day12/12.dec_input.txt")
        val matches = Regex("(-?\\d+)").findAll(json)

        var total = 0
        for (match in matches) {
            total += match.value.toInt()
        }

        println("Sum total $total")
    }

    fun part2() {
        val json = loadFile("src/Day12/12.dec_input.txt")
        val parser = JSONParser()
        val jsonObject = parser.parse(json) as JSONObject

        val amount = getAmountFromJsonObject(jsonObject)

        println("Revised sum total $amount")
    }

    fun getAmountFromJsonObject(jsonObject: JSONObject): Long {
        var result = 0L
        if (jsonObject.values.contains("red")) return result

        for (child in jsonObject.values) {
            if (child is JSONObject) {
                result += getAmountFromJsonObject(child)
            } else if (child is Long) {
                result += child
            } else if (child is JSONArray) {
                result += getAmountFromJsonArray(child)
            }
        }

        return result;
    }

    fun getAmountFromJsonArray(jsonArray: JSONArray): Long {
        var result = 0L
        for (arrayPart in jsonArray) {
            if (arrayPart is JSONObject) {
                result += getAmountFromJsonObject(arrayPart)
            } else if (arrayPart is Long) {
                result += arrayPart
            } else if (arrayPart is JSONArray) {
                result += getAmountFromJsonArray(arrayPart)
            }
        }

        return result
    }

    fun loadFile(path: String): String {
        val input = Paths.get(path)
        val reader = Files.newBufferedReader(input)
        return reader.readText()
    }

}

fun main(args: Array<String>) {
    December12().part1()
    December12().part2()
}