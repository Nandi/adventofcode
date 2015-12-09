package seventh

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream
import kotlin.text.Regex

/**
 * todo: visualize
 *
 * This year, Santa brought little Bobby Tables a set of wires and bitwise logic gates! Unfortunately, little Bobby is
 * a little under the recommended age range, and he needs help assembling the circuit.
 *
 * Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a number from 0 to 65535). A
 * signal is provided to each wire by a gate, another wire, or some specific value. Each wire can only get a signal
 * from one source, but can provide its signal to multiple destinations. A gate provides no signal until all of its
 * inputs have a signal.
 *
 * The included instructions booklet describes how to connect the parts together: x AND y -> z means to connect wires
 * x and y to an AND gate, and then connect its output to wire z.
 *
 * Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If, for some reason, you'd like to emulate
 * the circuit instead, almost all programming languages (for example, C, JavaScript, or Python) provide operators for
 * these gates.
 *
 * Part 1
 *
 * In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided to
 * wire a?
 *
 * Part 2
 *
 * Now, take the signal you got on wire a, override wire b to that signal, and reset the other wires (including wire a).
 * What new signal is ultimately provided to wire a?
 *
 * Created by Simon on 07/12/2015.
 */
class December7 {
    fun main() {
        val lines = loadFile("src/seventh/7.dec_input.txt")
        for (line in lines) {
            val (input, label) = line.split(" -> ")
            if (line.contains(Regex("AND|OR"))) {
                val values = input.split(" ");
                try {
                    nodes.put(label, Node(GATES.valueOf(values[1]), values[0].toInt(), arrayOf(values[2])))
                } catch (e: NumberFormatException) {
                    nodes.put(label, Node(GATES.valueOf(values[1]), inputWire = arrayOf(values[0], values[2])))
                }
            } else if (line.contains("NOT")) {
                nodes.put(label, Node(GATES.NOT, inputWire = arrayOf(input.substringAfter("NOT "))))
            } else if (line.contains(Regex("LSHIFT|RSHIFT"))) {
                val values = input.split(" ");
                nodes.put(label, Node(GATES.valueOf(values[1]), values[2].toInt(), arrayOf(values[0])))
            } else {
                try {
                    nodes.put(label, Node(GATES.NONE, input.toInt(), arrayOf()))
                } catch (e: NumberFormatException) {
                    nodes.put(label, Node(GATES.NONE, inputWire = arrayOf(input)))
                }
            }
        }

        val valueA = nodes["a"]?.getOutput()

        //part 1
        println("The value of wire a is $valueA")

        for (entry in nodes) {
            entry.value.reset()
        }

        nodes["b"] = Node(GATES.NONE, valueA!!, arrayOf());

        //part 2
        println("The value of wire a after wire b value change ${nodes["a"]?.getOutput()}")
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path);
        val reader = Files.newBufferedReader(input);
        return reader.lines();
    }
}

fun main(args: Array<String>) {
    December7().main()
}
