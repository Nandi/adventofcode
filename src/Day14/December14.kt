package Day14

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream

/**
 * todo: visualize
 *
 * This year is the Reindeer Olympics! Reindeer can fly at high speeds, but must rest occasionally to recover their
 * energy. Santa would like to know which of his reindeer is fastest, and so he has them race.
 *
 * Reindeer can only either be flying (always at their top speed) or resting (not moving at all), and always spend
 * whole seconds in either state.
 *
 * Part 1
 *
 * Given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, what distance has the
 * winning reindeer traveled?
 *
 * Part 2
 *
 * Seeing how reindeer move in bursts, Santa decides he's not pleased with the old scoring system.
 *
 * Instead, at the end of each second, he awards one point to the reindeer currently in the lead. (If there are
 * multiple reindeer tied for the lead, they each get one point.) He keeps the traditional 2503 second time limit, of
 * course, as doing otherwise would be entirely ridiculous.
 *
 * Again given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, how many points
 * does the winning reindeer have?
 *
 * Created by Simon on 14/12/2015.
 */

enum class STATE {
    RESTING, MOVING
}

data class Reindeer(val name: String, val speed: Int, val duration: Int, val rest: Int, var state: STATE = STATE.MOVING, var distance: Int = 0, var points: Int = 0) {
    var restTimer = rest
    var durationTimer = duration

    fun updateState() {
        when (state) {
            STATE.RESTING -> {
                restTimer--
                if (restTimer == 0) {
                    state = STATE.MOVING
                    restTimer = rest
                }
            }
            STATE.MOVING -> {
                durationTimer--
                if (durationTimer == 0) {
                    state = STATE.RESTING
                    durationTimer = duration
                }

                distance += speed
            }
        }
    }
}

class December14 {
    fun main() {

        var reindeerList = arrayListOf<Reindeer>()
        val lines = loadFile("src/Day14/14.dec_input.txt")
        for (line in lines) {
            var importantInfo = line.replace("can fly ", "")
                    .replace("km/s for ", "")
                    .replace("seconds, but then must rest for ", "")
                    .replace(" seconds.", "")

            val (name, speed, duration, rest) = importantInfo.split(" ")
            if (name !is String || speed !is String || duration !is String || rest !is String) return

            reindeerList.add(Reindeer(name, speed.toInt(), duration.toInt(), rest.toInt()))
        }

        val byDistance = Comparator<Reindeer> { r1, r2 -> r2.distance.compareTo(r1.distance) }
        val byPoints = Comparator<Reindeer> { r1, r2 -> r2.points.compareTo(r1.points) }

        for (i in 1..2503) {
            reindeerList.forEach { r -> r.updateState() }
            reindeerList.sort(byDistance)
            reindeerList.filter({ r -> r.distance.equals(reindeerList[0].distance) }).forEach { r -> r.points++ }
        }

        println("==== Sorted by distance ====")
        reindeerList.sort(byDistance)
        reindeerList.forEach { r -> println("${r.name} - ${r.distance}") }

        println()
        println("==== Sorted by points ====")
        reindeerList.sort(byPoints)
        reindeerList.forEach { r -> println("${r.name} - ${r.points}") }
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path)
        val reader = Files.newBufferedReader(input)
        return reader.lines()
    }

}

fun main(args: Array<String>) {
    December14().main()
}