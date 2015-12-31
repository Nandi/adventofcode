package Day18

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * todo: visualize
 *
 * After the million lights incident, the fire code has gotten stricter: now, at most ten thousand lights are allowed.
 * You arrange them in a 100x100 grid.
 *
 * Never one to let you down, Santa again mails you instructions on the ideal lighting configuration. With so few
 * lights, he says, you'll have to resort to animation.
 *
 * Start by setting your lights to the included initial configuration (your puzzle input). A # means "on", and a .
 * means "off".
 *
 * Then, animate your grid in steps, where each step decides the next configuration based on the current one. Each
 * light's next state (either on or off) depends on its current state and the current states of the eight lights
 * adjacent to it (including diagonals). Lights on the edge of the grid might have fewer than eight neighbors; the
 * missing ones always count as "off".
 *
 * The state a light should have next is based on its current state (on or off) plus the number of neighbors that are
 * on:
 *
 *      - A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
 *      - A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
 *
 * All of the lights update simultaneously; they all consider the same current state before moving to the next.
 *
 * Part 1
 *
 * In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?
 *
 * Part 2
 *
 * You flip the instructions over; Santa goes on to point out that this is all just an implementation of Conway's Game
 * of Life. At least, it was, until you notice that something's wrong with the grid of lights you bought: four lights,
 * one in each corner, are stuck on and can't be turned off.
 *
 * In your grid of 100x100 lights, given your initial configuration, but with the four corners always in the on state,
 * how many lights are on after 100 steps?
 *
 * Created by Simon on 27/12/2015.
 */
class December18 {
    private val size = 100
    private var lightGrid = Array(size + 2, { IntArray(size + 2) })
    private var part2 = false

    fun main(part2: Boolean = false) {
        this.part2 = part2
        val lines = loadFile("src/Day18/18.dec_input.txt")

        var row = 0;
        for (line in lines) {
            row++
            for (cell in 0..line.length - 1) {
                if ('#'.equals(line[cell]))
                    lightGrid[row][cell + 1] = 1
            }
        }

        if (part2) {
            lightGrid[1][1] = 1
            lightGrid[1][size] = 1
            lightGrid[size][size] = 1
            lightGrid[size][1] = 1
        }

        for (i in 1..size) {
            updateLights()
        }

        var activeLights = lightGrid.sumBy { row -> row.sum() }

        println(activeLights)
    }

    fun updateLights() {
        var tempGrid = Array(size + 2, { IntArray(size + 2) })
        for (row in 1..size) {
            tempGrid[row] = lightGrid[row].copyOf()
        }

        for (row in 1..size) {
            for (cell in 1..size) {

                if (part2 && (row == 1 || row == 100) && (cell == 1 || cell == 100)) {
                    continue
                }

                var activeNeighbors = 0

                activeNeighbors += tempGrid[row - 1][cell - 1]
                activeNeighbors += tempGrid[row - 1][cell]
                activeNeighbors += tempGrid[row - 1][cell + 1]

                activeNeighbors += tempGrid[row][cell - 1]
                activeNeighbors += tempGrid[row][cell + 1]

                activeNeighbors += tempGrid[row + 1][cell - 1]
                activeNeighbors += tempGrid[row + 1][cell]
                activeNeighbors += tempGrid[row + 1][cell + 1]

                if (tempGrid[row][cell] == 1 && activeNeighbors != 2 && activeNeighbors != 3) {
                    lightGrid[row][cell] = 0
                }
                if (tempGrid[row][cell] == 0 && activeNeighbors == 3) {
                    lightGrid[row][cell] = 1
                }
            }
        }
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path);
        val reader = Files.newBufferedReader(input);
        return reader.lines();
    }

}

fun main(args: Array<String>) {
    December18().main()
    December18().main(true)
}