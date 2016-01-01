package Day20

/**
 * To keep the Elves busy, Santa has them deliver some presents by hand, door-to-door. He sends them down a street with
 * infinite houses numbered sequentially: 1, 2, 3, 4, 5, and so on.
 *
 * Each Elf is assigned a number, too, and delivers presents to houses based on that number:
 *
 *      - The first Elf (number 1) delivers presents to every house: 1, 2, 3, 4, 5, ....
 *      - The second Elf (number 2) delivers presents to every second house: 2, 4, 6, 8, 10, ....
 *      - Elf number 3 delivers presents to every third house: 3, 6, 9, 12, 15, ....
 *
 * There are infinitely many Elves, numbered starting with 1. Each Elf delivers presents equal to ten times his or her
 * number at each house.
 *
 * Part 1
 *
 * What is the lowest house number of the house to get at least as many presents as the number in your puzzle input?
 *
 * Part 2
 *
 * The Elves decide they don't want to visit an infinite number of houses. Instead, each Elf will stop after delivering
 * presents to 50 houses. To make up for it, they decide to deliver presents equal to eleven times their number at each
 * house.
 *
 * With these changes, what is the new lowest house number of the house to get at least as many presents as the number
 * in your puzzle input?
 *
 * Created by Simon on 01/01/2016.
 */

class December20 {
    val input = 29000000

    constructor(part2: Boolean) {
        val max = 1000000
        val houses = IntArray(max + 1)
        var answer = 0

        for (elf in 1..max) {
            if (!part2) {
                for (visited in elf..max step elf) {
                    houses[visited] += elf * 10
                }
            } else {
                for (visited in elf..(elf * 50) step elf) {
                    if (visited >= max) break
                    houses[visited] += elf * 11
                }
            }
        }

        for (i in 0..max) {
            if (houses[i] >= input) {
                answer = i
                break
            }
        }

        System.out.println(answer);
    }
}

fun main(args: Array<String>) {
    December20(false)
    December20(true)
}