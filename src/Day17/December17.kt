package Day17

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream

/**
 * The elves bought too much eggnog again - 150 liters this time. To fit it all into your refrigerator, you'll need to
 * move it into smaller containers. You take an inventory of the capacities of the available containers.
 *
 * Part 1
 *
 * Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of
 * eggnog?
 *
 * Part 2
 *
 * While playing with all the containers in the kitchen, another load of eggnog arrives! The shipping and receiving
 * department is requesting as many containers as you can spare.
 *
 * Find the minimum number of containers that can exactly fit all 150 liters of eggnog. How many different ways can you
 * fill that number of containers and still hold exactly 150 litres?
 *
 * Created by Simon on 19/12/2015.
 */
class December17 {
    val size = 150
    var containers = arrayListOf<Int>()
    var combos = arrayListOf<ArrayList<Int>>()

    fun main() {
        val lines = loadFile("src/Day17/17.dec_input.txt")
        for (line in lines) {
            containers.add(line.toInt())
        }
        containers.sort { i, j -> j.compareTo(i) }

        for (i in 0..containers.size - 1) {
            var bin = arrayListOf(containers[i])

            fillBin(i + 1, bin)
        }

        println("The total number of combinations of containers that can fit exactly $size l of eggnog is ${combos.size}")

        combos.sort { list1, list2 -> list1.size - list2.size }
        val minComobs = combos.filter { bin -> bin.size == combos[0].size }
        println("The number of combinations that only uses ${combos[0].size} containers is ${minComobs.size}")
    }

    fun fillBin(index: Int, bin: ArrayList<Int>) {

        if (index == containers.size) {
            return
        }

        for (i in index..containers.size - 1) {
            if (bin.sum() + containers[i] > size) continue

            bin.add(containers[i])
            if (bin.sum() == size) {
                combos.add(bin.toArrayList())
            } else if (bin.sum() < size) {
                fillBin(i + 1, bin)
            }
            bin.removeAt(bin.size - 1)
        }

    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path)
        val reader = Files.newBufferedReader(input)
        return reader.lines()
    }
}

fun main(args: Array<String>) {
    December17().main()
}