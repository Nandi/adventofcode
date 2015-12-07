package sixth

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * 6.December challenge from www.adventofcode.com
 *
 * Because your neighbors keep defeating you in the holiday house decorating contest year after year, you've decided to
 * deploy one million lights in a 1000x1000 grid.
 *
 * Furthermore, because you've been especially nice this year, Santa has mailed you instructions on how to display the
 * ideal lighting configuration.
 *
 * Part 1
 *
 * Lights in your grid are numbered from 0 to 999 in each direction; the lights at each corner are at 0,0, 0,999,
 * 999,999, and 999,0. The instructions include whether to turn on, turn off, or toggle various inclusive ranges
 * given as coordinate pairs. Each coordinate pair represents opposite corners of a rectangle, inclusive; a coordinate
 * pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights all start turned off.
 *
 * To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent
 * you in order.
 *
 * After following the instructions, how many lights are lit?
 *
 * Part 2
 *
 * You just finish implementing your winning light pattern when you realize you mistranslated Santa's message from
 * Ancient Nordic Elvish.
 *
 * The light grid you bought actually has individual brightness controls; each light can have a brightness of zero or
 * more. The lights all start at zero.
 *
 * The phrase turn on actually means that you should increase the brightness of those lights by 1.
 *
 * The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of
 * zero.
 *
 * The phrase toggle actually means that you should increase the brightness of those lights by 2.
 *
 * What is the total brightness of all lights combined after following Santa's instructions?
 *
 * Created by Simon on 06/12/2015.
 */
class December6 {
    enum class State(val string: String) {
        TOGGLE("toggle"),
        TURN_OFF("turn off"),
        TURN_ON("turn on");
    }

    var lightsV1 = Array(1000, { BooleanArray(1000) })
    var lightsV2 = Array(1000, { IntArray(1000) })

    fun main() {
        val lines = loadFile("src/sixth/6.dec_input.txt")
        for (line in lines) {
            val state = determineState(line)
            val parts = line.substringAfter(state.string + " ").split(" ");

            val start = parts[0].split(",")
            val end = parts[2].split(",")

            for (i in start[0].toInt()..end[0].toInt()) {
                for (j in start[1].toInt()..end[1].toInt()) {
                    when (state) {
                        State.TOGGLE -> {
                            lightsV1[i][j] = !lightsV1[i][j];
                            lightsV2[i][j] += 2;
                        }
                        State.TURN_ON -> {
                            lightsV1[i][j] = true
                            lightsV2[i][j] += 1
                        }
                        State.TURN_OFF -> {
                            lightsV1[i][j] = false
                            if (lightsV2[i][j] > 0)
                                lightsV2[i][j] -= 1
                        }
                    }
                }
            }
        }

        var count = 0;
        for (light in lightsV1) {
            for (state in light) {
                if (state) {
                    count++
                }
            }
        }

        val brightnes = lightsV2.sumBy { a -> a.sum() }

        println(count)
        println(brightnes)
    }

    fun determineState(line: String): State {
        if (line.startsWith(State.TOGGLE.string)) {
            return State.TOGGLE
        } else if (line.startsWith(State.TURN_OFF.string)) {
            return State.TURN_OFF
        } else if (line.startsWith(State.TURN_ON.string)) {
            return State.TURN_ON
        } else {
            return State.TOGGLE;
        }
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path);
        val reader = Files.newBufferedReader(input);
        return reader.lines();
    }
}

fun main(args: Array<String>) {
    December6().main()
}