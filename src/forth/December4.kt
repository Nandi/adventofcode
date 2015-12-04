package forth

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

/**
 * 4.December challenge from www.adventofcode.com
 *
 * Santa needs help mining some AdventCoins (very similar to bitcoins) to use as gifts for all the economically
 * forward-thinking little girls and boys.
 *
 * Part 1
 *
 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes. The input to the
 * MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal. To mine AdventCoins,
 * you must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.
 *
 * Part 2
 *
 * Now find one that starts with six zeroes.
 *
 * Created by Simon on 04/12/2015.
 */
class December4 {
    val key = "ckczppom";

    fun part1() {
        var counter = 0;
        while (!md5("$key$counter", "00000")) {
            counter++;
        }
        println("To get a hash starting with 5 zeros take the md5 hash of $key$counter");
    }

    fun part2() {
        var counter = 0;
        while (!md5("$key$counter", "000000")) {
            counter++;
        }

        println("To get a hash starting with 6 zeros take the md5 hash of $key$counter");
    }

    fun md5(original: String, startsWith: String): Boolean {
        val hash = Hex.encodeHex(DigestUtils.md5(original));
        return String(hash).startsWith(startsWith);
    }
}

fun main(args: Array<String>) {
    December4().part1();
    December4().part2();
}