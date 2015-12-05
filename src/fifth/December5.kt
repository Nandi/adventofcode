package fifth

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream
import kotlin.text.Regex

/**
 * 5.December challenge from www.adventofcode.com
 *
 * Santa needs help figuring out which strings in his text file are naughty or nice.
 *
 * Part 1
 *
 * A nice string is one with all of the following properties:
 *      - It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
 *      - It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc,
 *      or dd).
 *      - It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
 *
 * How many strings are nice?
 *
 * Part 2
 * Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or
 * nice. None of the old rules apply, as they are all clearly ridiculous.
 *
 * Now, a nice string is one with all of the following properties:
 *      - It contains a pair of any two letters that appears at least twice in the string without overlapping, like
 *      xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
 *      - It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi
 *      (efe), or even aaa.
 *
 * How many strings are nice under these new rules?
 *
 * Created by Simon on 05/12/2015.
 */
class December5 {
    fun main() {
        val lines = loadFile("src/fifth/5.dec_input.txt");


        var v1Count = 0;
        var v2Count = 0;
        for (line in lines) {
            if (naughtyOrNiceV1(line)) v1Count++;
            if (naughtyOrNiceV2(line)) v2Count++;
        }

        println(v1Count);
        println(v2Count);
    }

    fun naughtyOrNiceV1(code: String): Boolean {
        val vowelPattern = Regex("[aeiou]")
        val unWantedPattern = Regex("ab|cd|pq|xy")
        val repeatPattern = Regex("(\\w)\\1{1}")

        var count = 0;
        for (match in vowelPattern.findAll(code)) {
            count++;
            if (count > 2) {
                break;
            }
        }

        if (count > 2 && !unWantedPattern.containsMatchIn(code) && repeatPattern.containsMatchIn(code)) {
            return true;
        }

        return false;
    }

    fun naughtyOrNiceV2(code: String): Boolean {
        val pairRepeatPattern = Regex("(..).*\\1")
        val singleRepeatPattern = Regex("(.).\\1")

        if(pairRepeatPattern.containsMatchIn(code) && singleRepeatPattern.containsMatchIn(code)) {
            return true;
        }

        return false;
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path);
        val reader = Files.newBufferedReader(input);
        return reader.lines();
    }
}

fun main(args: Array<String>) {
    December5().main();
}