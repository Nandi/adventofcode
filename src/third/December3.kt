package third

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * 3.December challenge from www.adventofcode.com
 * Created by Simon on 03/12/2015.
 */
class December3 {
    val directions = loadFile("src/third/3.dec_input.txt");

    data class Position(val x: Int, val y: Int)

    fun partOne() {
        var santa = Position(0,0);
        var houses = HashSet<String>();
        houses.add("${santa.x}${santa.y}");

        for (c in directions) {
            santa = move(c, santa);
            houses.add("${santa.x}${santa.y}");
        }

        println("Alone, santa delivers presents to ${houses.size} houses");
    }

    fun partTwo() {
        var robo = Position(0,0);
        var santa = Position(0,0);
        var houses = HashSet<String>();
        houses.add("x:${robo.x}y:${robo.y}");

        for (i in directions.indices) {
            if(i % 2 == 1){
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
        when(c) {
            '^' -> return Position(position.x, position.y-1);
            '>' -> return Position(position.x+1, position.y);
            '<' -> return Position(position.x-1, position.y);
            'v' -> return Position(position.x, position.y+1);
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
