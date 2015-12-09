package seventh

import java.util.*

/**
 * The node class represents a gate, it contains its potential input wires, gate and input value.
 * The getOutput method calculates the output of all
 *
 * Created by Simon on 08/12/2015.
 */

enum class GATES {
    AND, OR, NOT, LSHIFT, RSHIFT, NONE
}

val nodes = HashMap<String, Node>();

class Node(val gate: GATES, val inputValue: Int = 0, val inputWire: Array<String>) {
    private var checked = false
    private var output = 0;

    fun getOutput(): Int {
        if (checked)
            return output

        checked = true
        when (gate) {
            GATES.AND -> {
                var second = inputValue
                if (inputWire.size == 2) {
                    second = nodes[inputWire[1]]!!.getOutput()
                }
                output = (nodes[inputWire[0]]!!.getOutput() and second) and 0xffff
            }
            GATES.OR -> {
                var second = inputValue
                if (inputWire.size == 2) {
                    second = nodes[inputWire[1]]!!.getOutput()
                }
                output = (nodes[inputWire[0]]!!.getOutput() or second) and 0xffff
            }
            GATES.NOT -> {
                if (inputWire.size == 1) {
                    output = (nodes[inputWire[0]]!!.getOutput().inv()) and 0xffff
                } else {
                    throw RuntimeException("Wrong input amount for NOT gate. Need exactly 1 input, has ${inputWire.size}")
                }
            }
            GATES.LSHIFT -> {
                if (inputWire.size == 1) {
                    output = (nodes[inputWire[0]]!!.getOutput() shl inputValue) and 0xffff
                } else {
                    throw RuntimeException("Wrong input amount for RSHIFT. Need exactly 1 input, has ${inputWire.size}")
                }
            }
            GATES.RSHIFT -> {
                if (inputWire.size == 1) {
                    output = (nodes[inputWire[0]]!!.getOutput() shr inputValue) and 0xffff
                } else {
                    throw RuntimeException("Wrong input amount for RSHIFT. Need exactly 1 input, has ${inputWire.size}")
                }
            }
            GATES.NONE -> {
                if (inputWire.size == 1) {
                    output = nodes[inputWire[0]]!!.getOutput()
                } else {
                    output = inputValue;
                }
            }
        }
        return output
    }

    fun reset() {
        checked = false;
    }
}