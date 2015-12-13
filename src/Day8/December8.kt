package Day8

import org.unbescape.java.JavaEscape
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * Space on the sleigh is limited this year, and so Santa will be bringing his list as a digital copy. He needs to know
 * how much space it will take up when stored.
 *
 * It is common in many programming languages to provide a way to escape special characters in strings. For example, C,
 * JavaScript, Perl, Python, and even PHP handle special characters in very similar ways.
 *
 * However, it is important to realize the difference between the number of characters in the code representation of
 * the string literal and the number of characters in the in-memory string itself.
 *
 * Part 1
 *
 * Santa's list is a file that contains many double-quoted string literals, one on each line. The only escape sequences
 * used are \\ (which represents a single backslash), \" (which represents a lone double-quote character), and \x plus
 * two hexadecimal characters (which represents a single character with that ASCII code).
 *
 * Disregarding the whitespace in the file, what is the number of characters of code for string literals minus the
 * number of characters in memory for the values of the strings in total for the entire file?
 *
 * Part 2
 *
 * Now, let's go the other way. In addition to finding the number of characters of code, you should now encode each
 * code representation as a new string and find the number of characters of the new encoded representation, including
 * the surrounding double quotes.
 *
 * Your task is to find the total number of characters to represent the newly encoded strings minus the number of
 * characters of code in each original string literal.
 *
 * Created by Simon on 09/12/2015.
 */
class December8 {
    fun part1() {

        val input = Paths.get("src/Day8/8.dec_input.txt")
        val lines = loadFile(input)
        var bytes = 0
        var sizePart1 = 0
        var sizePart2 = 0
        for (line in lines) {

            bytes += line.length
            var actualString = line.substring(1..(line.length - 2))
            actualString = actualString.replace("\\\\", "$")
            actualString = actualString.replace("\\x", "\\u00")
            actualString = JavaEscape.unescapeJava(actualString)
            sizePart1 += actualString.length
            sizePart2 += JavaEscape.escapeJava(line).length + 2
        }
        println(bytes - sizePart1);
        println(sizePart2 - bytes);
    }

    fun loadFile(path: Path): Stream<String> {
        val reader = Files.newBufferedReader(path);
        return reader.lines();
    }

}

fun main(args: Array<String>) {
    December8().part1()
}