package y2023.d20.p1

import println
import readInput
import runMeasure

fun solve() {
    val input = readInput()

    var res = 0L

    val modules = mutableMapOf<String, Module>()

    val order = mutableMapOf<Module, List<Module>>()

    val outs = mutableMapOf<String, List<String>>()

    val ins = mutableMapOf<String, MutableList<String>>(
        "broadcaster" to mutableListOf()
    )

    for (l in input) {
        if (l == "broadcaster") continue

        val (name, dest) = l.split(" -> ")

        if (modules.containsKey(name)) continue

        val conns = dest.split(", ")

        if (name.startsWith('%')) {
            modules[name.drop(1)] = FlipFlop(name.drop(1))
            outs[name.drop(1)] = conns
        }

        if (name.startsWith('&')) {
            modules[name.drop(1)] = Conjunction(name.drop(1))
            outs[name.drop(1)] = conns
        }

        if (name == "broadcaster") {
            modules[name] = Broadcaster()
            outs[name] = conns
        }
    }

    for (l in input) {
        val (left, rest) = l.split(" -> ")
        val dest = rest.split(", ")

        val name = if (left == "broadcaster") left else left.drop(1)

        order[modules[name]!!] = dest.map { modules[it]!! }
    }


    for (k in 1..1) {
        for ((m, dests) in order) {
            if (m is Broadcaster) {

            }
        }
    }

    outs.forEach { out ->
        out.value.forEach {
            if (!ins.containsKey(it)) {
                ins[it] = mutableListOf()
            }

            ins[it]!!.add(out.key)
        }
    }

    for ((name, module) in modules) {
        module.input = ins[name]!!.map {
            modules[it]!!
        }
    }

    var cn = listOf("broadcaster")
    var cm = modules.filterKeys { it in cn }.values

    fun f(pulse: Pulse, dest: List<Module>): Pair<Int, Int> {
        val pulses = dest.map {
            it.process(pulse)
        }

        val nextModules = outs[pulse.generatedBy.name]!!.map { modules[it]!! }

        nextModules.forEachIndexed { index, module ->

        }

        println(1)
        return 1 to 1
    }


    val button = Button()
//    f(Pulse(PulseType.LOW, button), listOf(modules["broadcaster"]!!))


    val br = modules["broadcaster"]!!
    var pulse = br.process(Pulse(PulseType.LOW, Button()))

//    while (pulse.type != PulseType.NONE) {
//        val nM = outs[pulse.generatedBy.name]!!
//        nM.forEach { pulse = modules[it]!!.process(pulse) }
//        println(1)
//    }

    res.println()
}

fun main() {
    runMeasure { solve() }
}

data class Pulse(var type: PulseType, var generatedBy: Module)

enum class PulseType {
    HIGH, LOW, NONE
}

class Button : Module("button", listOf()) {
    override fun process(pulse: Pulse): Pulse {
        return Pulse(PulseType.LOW, this)
    }
}

abstract class Module(open val name: String, open var input: List<Module> = listOf()) {
    abstract fun process(pulse: Pulse): Pulse
}

data class Broadcaster(override var input: List<Module> = listOf()) : Module("broadcaster", input) {
    override fun process(pulse: Pulse): Pulse {
        pulse.generatedBy = this
        return pulse
    }
}

data class FlipFlop(override val name: String, override var input: List<Module> = listOf()) : Module(name, input) {

    private var state = false

    private fun toggle() {
        state = !state
    }

    override fun process(pulse: Pulse): Pulse {
        pulse.generatedBy = this
        if (pulse.type == PulseType.HIGH) {
            pulse.type = PulseType.NONE
            return pulse
        }

        if (!state) {
            toggle()
            pulse.type = PulseType.HIGH
        } else {
            toggle()
            pulse.type = PulseType.LOW
        }

        return pulse
    }
}

class Conjunction(override val name: String, override var input: List<Module> = listOf()) : Module(name, input) {

    private var types: MutableMap<Module, PulseType> = input.associateWith { PulseType.LOW }.toMutableMap()

    override fun process(pulse: Pulse): Pulse {
        pulse.generatedBy = this
        types[pulse.generatedBy] = pulse.type
        pulse.type = if (types.values.all { it == PulseType.HIGH }) PulseType.LOW else PulseType.HIGH

        return pulse
    }
}