package second

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * 2.December challenge from www.adventofcode.com
 * Created by Simon on 02/12/2015.
 */

class December2 {
    fun main() {
        var square = 0;
        var ribbon = 0;

        val lines = loadFile("src/second/2.dec_input.txt");
        for (line in lines) {
            val dim = line.split("x");
            square += packaging(dim[0].toInt(), dim[1].toInt(), dim[2].toInt());
            ribbon += rapping(dim[0].toInt(), dim[1].toInt(), dim[2].toInt());
        }

        println("$square square feet of packing paper needed to wrap all the presents");
        println("$ribbon feet of ribbon is needed for all the presents")
    }

    fun packaging(l: Int, w: Int, h: Int): Int {
        var side1: Int = l * w;
        var side2 = w * h;
        var side3 = h * l;
        var extra: Int = Math.min(side1, Math.min(side2, side3));

        return 2 * (side1 + side2 + side3) + extra;
    }

    fun rapping(l: Int, w: Int, h: Int): Int {
        return (2 * (l + w + h) - 2 * Math.max(l, Math.max(w, h))) + (l * w * h);
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path);
        val reader = Files.newBufferedReader(input);
        return reader.lines();
    }
}

fun main(args: Array<String>) {
    December2().main();
}
