package Day19

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very brightly, and he needs medicine.
 *
 * Red-Nosed Reindeer biology isn't similar to regular reindeer biology; Rudolph is going to need custom-made medicine.
 * Unfortunately, Red-Nosed Reindeer chemistry isn't similar to regular reindeer chemistry, either.
 *
 * The North Pole is equipped with a Red-Nosed Reindeer nuclear fusion/fission plant, capable of constructing any
 * Red-Nosed Reindeer molecule you need. It works by starting with some input molecule and then doing a series of
 * replacements, one per step, until it has the right molecule.
 *
 * However, the machine has to be calibrated before it can be used. Calibration involves determining the number of
 * molecules that can be generated in one step from a given starting point.
 *
 * The machine replaces without regard for the surrounding characters. For example, given the string H2O, the
 * transition H => OO would result in OO2O.
 *
 * Part 1
 *
 * Your puzzle input describes all of the possible replacements and, at the bottom, the medicine molecule for which you
 * need to calibrate the machine. How many distinct molecules can be created after all the different ways you can do
 * one replacement on the medicine molecule?
 *
 * Part 2
 *
 * Now that the machine is calibrated, you're ready to begin molecule fabrication.
 *
 * Molecule fabrication always begins with just a single electron, e, and applying replacements one at a time, just
 * like the ones during calibration.
 *
 * How long will it take to make the medicine? Given the available replacements and the medicine molecule in your
 * puzzle input, what is the fewest number of steps to go from e to the medicine molecule?
 *
 * Created by Simon on 31/12/2015.
 */
class December19 {
    private val medicalMolecule = "CRnSiRnCaPTiMgYCaPTiRnFArSiThFArCaSiThSiThPBCaCaSiRnSiRnTiTiMgArPBCaPMgYPTiRnFArFArCaSiRnBPMgArPRnCaPTiRnFArCaSiThCaCaFArPBCaCaPTiTiRnFArCaSiRnSiAlYSiThRnFArArCaSiRnBFArCaCaSiRnSiThCaCaCaFYCaPTiBCaSiThCaSiThPMgArSiRnCaPBFYCaCaFArCaCaCaCaSiThCaSiRnPRnFArPBSiThPRnFArSiRnMgArCaFYFArCaSiRnSiAlArTiTiTiTiTiTiTiRnPMgArPTiTiTiBSiRnSiAlArTiTiRnPMgArCaFYBPBPTiRnSiRnMgArSiThCaFArCaSiThFArPRnFArCaSiRnTiBSiThSiRnSiAlYCaFArPRnFArSiThCaFArCaCaSiThCaCaCaSiRnPRnCaFArFYPMgArCaPBCaPBSiRnFYPBCaFArCaSiAl"

    constructor() {
        val replacements = arrayListOf<Pair<String, String>>()
        val permutations = hashSetOf<String>()

        val lines = loadFile("src/Day19/19.dec_input.txt")
        for (line in lines) {
            val (from, to) = line.split(" => ")
            if (from !is String || to !is String) return

            replacements.add(Pair(from, to))
        }

        replacements.forEach {
            pair ->
            Regex(pair.first)
                    .findAll(medicalMolecule)
                    .forEach {
                        match ->
                        permutations.add(medicalMolecule.replaceRange(match.range, pair.second))
                    }
        }

        println("There are ${permutations.size} permutations")

        //Subtract of #Rn and #Ar because those are just extras. Subtract two times #Y because we get rid of the Ys and the extra elements following them. Subtract one because we start with "e".
        val steps = medicalMolecule.count { c -> c.isUpperCase() } - countMolecules("Rn") - countMolecules("Ar") - 2 * medicalMolecule.count { c -> c.equals('Y') } - 1

        println("Going for the original molecule to an electron took $steps steps")
    }

    fun countMolecules(filter: String): Int {
        return Regex(filter).findAll(medicalMolecule).count()
    }

    fun loadFile(path: String): Stream<String> {
        val input = Paths.get(path);
        val reader = Files.newBufferedReader(input);
        return reader.lines();
    }
}

fun main(args: Array<String>) {
    December19()
}