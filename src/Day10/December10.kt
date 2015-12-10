package Day10

/**
 * Today, the Elves are playing a game called look-and-say. They take turns making sequences by reading aloud the
 * previous sequence and using that reading as the next sequence. For example, 211 is read as "one two, two ones",
 * which becomes 1221 (1 2, 2 1s).
 *
 * Look-and-say sequences are generated iteratively, using the previous value as input for the next step. For each
 * step, take the previous value, and replace each run of digits (like 111) with the number of digits (3) followed by
 * the digit itself (1).
 *
 * Part 1
 *
 * Starting with the digits in your puzzle input, apply this process 40 times. What is the length of the result?
 *
 * Part 2
 *
 * Now, starting again with the digits in your puzzle input, apply this process 50 times. What is the length of the
 * new result?
 *
 * Created by Simon on 10/12/2015.
 */
class December10 {
    val input = "1113122113"

    fun calculateOutputLength(repeats: Int) {
        var output = input
        for (i in 1..repeats) {
            output = lookAndSay(output)
            println("$i - ${output.length}")
        }

        println(output.length)
    }

    fun lookAndSay(numberSequence: String): String {
        var last: Char = numberSequence[0]
        var count = 0
        var output = ""
        for (c in numberSequence) {
            if (last.equals(c)) {
                count++
            } else {
                output += "$count$last"
                count = 1
            }

            last = c
        }

        output += "$count$last"

        return output;
    }
}

fun main(args: Array<String>) {
    //December10().calculateOutputLength(40)
    December10().calculateOutputLength(50)
}