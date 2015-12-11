package Day9

import com.google.common.collect.Collections2
import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleWeightedGraph
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * todo: visualize
 *
 * Every year, Santa manages to deliver all of his presents in a single night.
 *
 * Part 1
 *
 * This year, however, he has some new locations to visit; his elves have provided him the distances between every
 * pair of locations. He can start and end at any two (different) locations he wants, but he must visit each location
 * exactly once. What is the shortest distance he can travel to achieve this?
 *
 * Part 2
 *
 * The next year, just to show off, Santa decides to take the route with the longest distance instead.
 *
 * He can still start and end at any two (different) locations he wants, and he still must visit each location exactly
 * once.
 *
 * Created by Simon on 10/12/2015.
 */

class December9 {
    val cityGraph: SimpleWeightedGraph<String, DefaultWeightedEdge> = SimpleWeightedGraph(DefaultWeightedEdge::class.java)

    fun createGraph() {

        val lines = loadFile("src/Day9/9.dec_input.txt")
        for (line in lines) {
            val (cities, weight) = line.split(" = ")
            if (cities !is String) return

            val(start, end) = cities.split(" to ")

            cityGraph.addVertex(start)
            cityGraph.addVertex(end)

            cityGraph.setEdgeWeight(cityGraph.addEdge(start, end), weight.toDouble());
        }

        var min = Double.MAX_VALUE;
        var minPath = listOf<String>()
        var max = 0.0
        var maxPath = listOf<String>()
        for (permutation in Collections2.permutations(cityGraph.vertexSet())) {
            var len = 0.0

            for (i in 0..(permutation.size - 2)) {
                val edge = cityGraph.getEdge(permutation[i], permutation[i + 1])
                len += cityGraph.getEdgeWeight(edge)
            }

            if (len < min) {
                min = len
                minPath = permutation
            }

            if (len > max) {
                max = len
                maxPath = permutation
            }
        }
        println("The shortest distance was $min through $minPath")
        println("The longest distance was $max through $maxPath")

    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path);
        val reader = Files.newBufferedReader(input);
        return reader.lines();
    }

}

fun main(args: Array<String>) {
    December9().createGraph()
}