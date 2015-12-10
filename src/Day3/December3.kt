package Day3

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * todo: visualize
 *
 * 3.December challenge from www.adventofcode.com
 *
 * Santa is delivering presents to an infinite two-dimensional grid of houses.
 *
 * He begins by delivering a present to the house at his starting location, and then an elf at the North Pole calls him
 * via radio and tells him where to move next. Moves are always exactly one house to the north (^), south (v), east (>),
 * or west (<). After each move, he delivers another present to the house at his new location.
 *
 * Part 1
 *
 * However, the elf back at the north pole has had a little too much eggnog, and so his directions are a little off,
 * and Santa ends up visiting some houses more than once. How many houses receive at least one present?
 *
 * Part 2
 *
 * The next year, to speed up the process, Santa creates a robot version of himself, Robo-Santa, to deliver
 * presents with him.
 *
 * Santa and Robo-Santa start at the same location (delivering two presents to the same starting house), then take
 * turns moving based on instructions from the elf, who is eggnoggedly reading from the same script as the previous
 * year.
 *
 * This year, how many houses receive at least one present?
 *
 * Created by Simon on 03/12/2015.
 */
class December3 {
    val directions = loadFile("src/Day3/3.dec_input.txt");

    data class Position(val x: Int, val y: Int)

    fun partOne() {
        var santa = Position(0, 0);
        var houses = HashSet<String>();
        houses.add("${santa.x}${santa.y}");

        for (c in directions) {
            santa = move(c, santa);
            houses.add("${santa.x}${santa.y}");
        }

        println("Alone, santa delivers presents to ${houses.size} houses");
    }

    fun partTwo() {
        var robo = Position(0, 0);
        var santa = Position(0, 0);
        var houses = HashSet<String>();
        houses.add("x:${robo.x}y:${robo.y}");

        for (i in directions.indices) {
            if (i % 2 == 1) {
                //robo-santa
                robo = move(directions[i], robo);
                houses.add("x:${robo.x}y:${robo.y}");
            } else {
                //santa
                santa = move(directions[i], santa);
                houses.add("x:${santa.x}y:${santa.y}");
            }
        }

        println("Together with Robo-Santa, santa delivers presents to ${houses.size} houses");
    }

    fun move(c: Char, position: Position): Position {
        when (c) {
            '^' -> return Position(position.x, position.y - 1);
            '>' -> return Position(position.x + 1, position.y);
            '<' -> return Position(position.x - 1, position.y);
            'v' -> return Position(position.x, position.y + 1);
            else -> {
                println("something is wrong with: $c")
                return position;
            }
        }
    }

    fun loadFile(path: String): String {
        val input = Paths.get(path);
        val reader = Files.newBufferedReader(input);
        return reader.readText();
    }
}

fun main(args: Array<String>) {
    December3().partOne();
    December3().partTwo();
}
