package Day13

import org.jgrapht.experimental.permutation.CollectionPermutationIter
import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleWeightedGraph
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * In years past, the holiday feast with your family hasn't gone so well. Not everyone gets along! This year, you
 * resolve, will be different. You're going to find the optimal seating arrangement and avoid all those awkward
 * conversations.
 *
 * You start by writing up a list of everyone invited and the amount their happiness would increase or decrease if they
 * were to find themselves sitting next to each other person. You have a circular table that will be just big enough to
 * fit everyone comfortably, and so each person will have exactly two neighbors.
 *
 * Part 1
 *
 * What is the total change in happiness for the optimal seating arrangement of the actual guest list?
 *
 * Part 2
 *
 * In all the commotion, you realize that you forgot to seat yourself. At this point, you're pretty apathetic toward
 * the whole thing, and your happiness wouldn't really go up or down regardless of who you sit next to. You assume
 * everyone else would be just as ambivalent about sitting next to you, too.
 *
 * So, add yourself to the list, and give all happiness relationships that involve you a score of 0.
 *
 * What is the total change in happiness for the optimal seating arrangement that actually includes yourself?
 *
 * Created by Simon on 13/12/2015.
 */
class December13 {
    val seatingGraph: SimpleWeightedGraph<String, DefaultWeightedEdge> = SimpleWeightedGraph(DefaultWeightedEdge::class.java)

    fun createGraph(seatSelf: Boolean) {

        val lines = loadFile("src/Day13/13.dec_input.txt")
        for (line in lines) {
            var importantInfo = line.replace("would ", "").replace("happiness units by sitting next to ", "").replace(".", "")
            val (from, type, weight, to) = importantInfo.split(" ")

            if (from !is String || to !is String || type !is String || weight !is String) return

            var sign: Double
            if ("lose".equals(type)) {
                sign = -1.0
            } else {
                sign = 1.0
            }

            seatingGraph.addVertex(from)
            seatingGraph.addVertex(to)

            val edge = seatingGraph.getEdge(from, to)
            if (edge == null) {
                seatingGraph.setEdgeWeight(seatingGraph.addEdge(from, to), weight.toDouble().times(sign))
            } else {
                seatingGraph.setEdgeWeight(edge, seatingGraph.getEdgeWeight(edge) + (weight.toDouble().times(sign)))
            }
        }

        if (seatSelf) {
            seatingGraph.addVertex("Simon")
            for (person in seatingGraph.vertexSet()) {
                if ("Simon".equals(person)) continue
                seatingGraph.setEdgeWeight(seatingGraph.addEdge("Simon", person), 0.0)
            }
        }

        var max = 0.0
        var maxPath = listOf<String>()

        val iterator = CollectionPermutationIter<String>(seatingGraph.vertexSet())

        while (iterator.hasNext()) {
            val permutation = iterator.nextArray
            var len = 0.0

            for (i in 0..(permutation.size - 1)) {
                val from = permutation[i]
                val to = if ((i + 1) == permutation.size) permutation[0] else permutation[i + 1]

                val edge = seatingGraph.getEdge(from, to) ?: break
                len += seatingGraph.getEdgeWeight(edge)
            }

            if (len > max) {
                max = len
                maxPath = permutation
            }
        }
        println("The longest distance was ${max.toInt()} through $maxPath")

    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path)
        val reader = Files.newBufferedReader(input)
        return reader.lines()
    }
}

fun main(args: Array<String>) {
    December13().createGraph(false)
    December13().createGraph(true)
}