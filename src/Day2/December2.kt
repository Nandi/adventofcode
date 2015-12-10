package Day2

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * 2.December challenge from www.adventofcode.com
 *
 * Part 1
 *
 * The elves are running low on wrapping paper, and so they need to submit an order for more. They have a list of the
 * dimensions (length l, width w, and height h) of each present, and only want to order exactly as much as they need.
 *
 * Fortunately, every present is a box (a perfect right rectangular prism), which makes calculating the required
 * wrapping paper for each gift a little easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l.
 * The elves also need a little extra paper for each present: the area of the smallest side.
 *
 * All numbers in the elves' list are in feet. How many total square feet of wrapping paper should they order?
 *
 * Part 2
 *
 * The elves are also running low on ribbon. Ribbon is all the same width, so they only have to worry about the length
 * they need to order, which they would again like to be exact.
 *
 * The ribbon required to wrap a present is the shortest distance around its sides, or the smallest perimeter of any
 * one face. Each present also requires a bow made out of ribbon as well; the feet of ribbon required for the perfect
 * bow is equal to the cubic feet of volume of the present. Don't ask how they tie the bow, though; they'll never tell.
 *
 * How many total feet of ribbon should they order?
 *
 * Created by Simon on 02/12/2015.
 */

class December2 {
    fun main() {
        var square = 0;
        var ribbon = 0;

        val lines = loadFile("src/Day2/2.dec_input.txt");
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
