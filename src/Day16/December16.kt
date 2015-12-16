package Day16

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream
import kotlin.text.Regex

/**
 * Your Aunt Sue has given you a wonderful gift, and you'd like to send her a thank you card. However, there's a small
 * problem: she signed it "From, Aunt Sue".
 *
 * You have 500 Aunts named "Sue".
 *
 * So, to avoid sending the card to the wrong person, you need to figure out which Aunt Sue (which you conveniently
 * number 1 to 500, for sanity) gave you the gift. You open the present and, as luck would have it, good ol' Aunt Sue
 * got you a My First Crime Scene Analysis Machine! Just what you wanted. Or needed, as the case may be.
 *
 * The My First Crime Scene Analysis Machine (MFCSAM for short) can detect a few specific compounds in a given sample,
 * as well as how many distinct kinds of those compounds there are. According to the instructions, these are what the
 * MFCSAM can detect:
 *
 *      - children, by human DNA age analysis.
 *      - cats. It doesn't differentiate individual breeds.
 *      - Several seemingly random breeds of dog: samoyeds, pomeranians, akitas, and vizslas.
 *      - goldfish. No other kinds of fish.
 *      - trees, all in one group.
 *      - cars, presumably by exhaust or gasoline or something.
 *      - perfumes, which is handy, since many of your Aunts Sue wear a few kinds.
 *
 * In fact, many of your Aunts Sue have many of these. You put the wrapping from the gift into the MFCSAM. It beeps
 * inquisitively at you a few times and then prints out a message on ticker tape:
 *
 *      children: 3
 *      cats: 7
 *      samoyeds: 2
 *      pomeranians: 3
 *      akitas: 0
 *      vizslas: 0
 *      goldfish: 5
 *      trees: 3
 *      cars: 2
 *      perfumes: 1
 *
 * You make a list of the things you can remember about each Aunt Sue. Things missing from your list aren't zero - you
 * simply don't remember the value.
 *
 * Part 1
 *
 * What is the number of the Sue that got you the gift?
 *
 * Part 2
 *
 * As you're about to send the thank you note, something in the MFCSAM's instructions catches your eye. Apparently, it
 * has an outdated retroencabulator, and so the output from the machine isn't exact values - some of them indicate ranges.
 *
 * In particular, the cats and trees readings indicates that there are greater than that many (due to the unpredictable
 * nuclear decay of cat dander and tree pollen), while the pomeranians and goldfish readings indicate that there are
 * fewer than that many (due to the modial interaction of magnetoreluctance).
 *
 * What is the number of the real Aunt Sue?
 *
 * Created by Simon on 16/12/2015.
 */

data class Aunt(val id: Int,
                var children: Int? = null,
                var cats: Int? = null,
                var samoyeds: Int? = null,
                var pomeranians: Int? = null,
                var akitas: Int? = null,
                var vizslas: Int? = null,
                var goldfish: Int? = null,
                var trees: Int? = null,
                var cars: Int? = null,
                var perfumes: Int? = null) {
    fun equalsPart1(other: Any?): Boolean {
        if (other == null || other !is Aunt) return false

        if (other.children != null && !(other.children as Int).equals(children)) return false
        if (other.cats != null && !(other.cats as Int).equals(cats)) return false
        if (other.samoyeds != null && !(other.samoyeds as Int).equals(samoyeds)) return false
        if (other.pomeranians != null && !(other.pomeranians as Int).equals(pomeranians)) return false
        if (other.akitas != null && !(other.akitas as Int).equals(akitas)) return false
        if (other.vizslas != null && !(other.vizslas as Int).equals(vizslas)) return false
        if (other.goldfish != null && !(other.goldfish as Int).equals(goldfish)) return false
        if (other.trees != null && !(other.trees as Int).equals(trees)) return false
        if (other.cars != null && !(other.cars as Int).equals(cars)) return false
        if (other.perfumes != null && !(other.perfumes as Int).equals(perfumes)) return false

        return true
    }

    fun equalsPart2(other: Any?): Boolean {
        if (other == null || other !is Aunt) return false

        if (other.children != null && !(other.children as Int).equals(children)) return false
        if (other.cats != null && (other.cats as Int) <= (cats!!)) return false
        if (other.samoyeds != null && !(other.samoyeds as Int).equals(samoyeds)) return false
        if (other.pomeranians != null && (other.pomeranians as Int) >= (pomeranians!!)) return false
        if (other.akitas != null && !(other.akitas as Int).equals(akitas)) return false
        if (other.vizslas != null && !(other.vizslas as Int).equals(vizslas)) return false
        if (other.goldfish != null && (other.goldfish as Int) >= (goldfish!!)) return false
        if (other.trees != null && (other.trees as Int) <= (trees!!)) return false
        if (other.cars != null && !(other.cars as Int).equals(cars)) return false
        if (other.perfumes != null && !(other.perfumes as Int).equals(perfumes)) return false

        return true
    }

}

enum class PARAMETERS {
    CHILDREN, CATS, SAMOYEDS, POMERANIANS, AKITAS, VIZSLAS, GOLDFISH, TREES, CARS, PERFUMES;
}

class December16 {
    val mystery = Aunt(0, 3, 7, 2, 3, 0, 0, 5, 3, 2, 1)

    fun main() {
        val lines = loadFile("src/Day16/16.dec_input.txt")
        var aunts = arrayListOf<Aunt>()

        var id = 1;
        for (line in lines) {
            val parameterMap = HashMap<String, Int>()
            line.replace(Regex("Sue \\d+: "), "").split(", ").forEach { p ->
                val pm = p.split(": ");
                parameterMap.put(pm[0], pm[1].toInt())
            }

            val aunt = Aunt(id)

            for (key in parameterMap.keys) {
                when (PARAMETERS.valueOf(key.toUpperCase())) {
                    PARAMETERS.CHILDREN -> aunt.children = parameterMap[key]
                    PARAMETERS.CATS -> aunt.cats = parameterMap[key]
                    PARAMETERS.SAMOYEDS -> aunt.samoyeds = parameterMap[key]
                    PARAMETERS.POMERANIANS -> aunt.pomeranians = parameterMap[key]
                    PARAMETERS.AKITAS -> aunt.akitas = parameterMap[key]
                    PARAMETERS.VIZSLAS -> aunt.vizslas = parameterMap[key]
                    PARAMETERS.GOLDFISH -> aunt.goldfish = parameterMap[key]
                    PARAMETERS.TREES -> aunt.trees = parameterMap[key]
                    PARAMETERS.CARS -> aunt.cars = parameterMap[key]
                    PARAMETERS.PERFUMES -> aunt.perfumes = parameterMap[key]
                }
            }

            aunts.add(aunt)

            id++;
        }

        aunts.filter { a -> mystery.equalsPart1(a) }
                .forEach { a -> println("If the amounts given by the MFCSAM are exact, then Sue ${a.id} was the sender") }

        aunts.filter { a -> mystery.equalsPart2(a) }
                .forEach { a -> println("If the amounts given by the MFCSAM are not exact, then Sue ${a.id} was the sender") }
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path)
        val reader = Files.newBufferedReader(input)
        return reader.lines()
    }

}

fun main(args: Array<String>) {
    December16().main()
}