package y2023.d20.p2

import edu.uci.ics.jung.graph.DirectedSparseGraph
import lcm
import println
import readInput
import runMeasure


fun solve() {
    val input = readInput()

    val graph = DirectedSparseGraph<String, String>()

    val modules = mutableMapOf<String, Module>()

    val outs = mutableMapOf<String, List<String>>()

    val ins = mutableMapOf<String, MutableList<String>>(
        "broadcaster" to mutableListOf()
    )

    val sinks = mutableListOf<Sink>()

    for (l in input) {
        if (l == "broadcaster") continue

        val (name, dest) = l.split(" -> ")

        val conns = dest.split(", ")

        if (name.startsWith('%')) {
            modules[name.drop(1)] = FlipFlop(name.drop(1))
            outs[name.drop(1)] = conns
            graph.addVertex(name.drop(1))
            conns.forEach { c ->
                graph.addEdge("${name.drop(1)} -> $c", name.drop(1), c)
            }
        }

        if (name.startsWith('&')) {
            modules[name.drop(1)] = Conjunction(name.drop(1))
            outs[name.drop(1)] = conns
            graph.addVertex(name.drop(1))
            conns.forEach { c ->
                graph.addEdge("${name.drop(1)} -> $c", name.drop(1), c)
            }
        }

        if (name == "broadcaster") {
            modules[name] = Broadcaster()
            outs[name] = conns
            graph.addVertex(name)
            conns.forEach { c ->
                graph.addEdge("$name -> $c", name, c)
            }
        }

        sinks.addAll(conns.map { Sink(it) })

    }

    for (sink in sinks.filter { !modules.keys.contains(it.name) }) {
        modules[sink.name] = sink
        graph.addVertex(sink.name)
    }

    outs.forEach { out ->
        out.value.forEach {
            if (!ins.containsKey(it)) {
                ins[it] = mutableListOf()
            }

            ins[it]!!.add(out.key)
            graph.addEdge("${out.key} -> $it", out.key, it)
        }
    }

    for (m in modules.values) {
        if (m is Conjunction) {
            m.input = ins[m.name]!!.map { modules[it]!! }.toMutableList()
        }

        if (m !is Sink) {
            m.connected = outs[m.name]!!.map { modules[it]!! }.toMutableList()
        }
    }

    val rxInput = ins["rx"]!! // in my input its "&kz"
    val inputsOfInput = ins[rxInput.first()]!! // in my inputs it is &qq, &sq, &ls, &bg

    // holds the number of "presses" when, as the result, the corresponding module sends high signal
    val counts = inputsOfInput.associateWith { 0 }.toMutableMap()

    val m = modules["broadcaster"]!!
    var k = 1
    big@while (k <= 1_000_000) {
        val queue = mutableListOf(m to Pulse(PulseType.LOW))
        var i = 0
        while (i < queue.size) {
            val (module, receivedPulse) = queue[i]

            for (c in module.connected) {
                val newPulse =
                    (if (c is Conjunction) c.function(module, receivedPulse)
                    else c.function(receivedPulse)) ?: continue

                if (c is Conjunction) {
                    if (c.name in inputsOfInput) {
                        if (newPulse.isHigh()) {
                            counts[c.name] = k
                            if (counts.all { it.value > 0 }) break@big
                        }
                    }
                }

                queue.add(c to newPulse)
            }

            i++
        }
        k++
    }

    counts.values
        .map { it.toLong() }
        .lcm()
        .println()
}

fun main() {
    runMeasure { solve() }
}

data class Pulse(var type: PulseType) {
    fun isHigh() = this.type == PulseType.HIGH
}

enum class PulseType {
    HIGH, LOW
}

class Broadcaster : Module("broadcaster", listOf()) {
    override fun function(pulse: Pulse): Pulse = pulse
}

class Sink(override val name: String) : Module(name, listOf()) {
    override fun function(pulse: Pulse): Pulse = pulse
}

abstract class Module(
    open val name: String, open var connected: List<Module> = listOf()
) {
    abstract fun function(pulse: Pulse): Pulse?
}

data class FlipFlop(
    override val name: String, override var connected: List<Module> = listOf()
) : Module(name, connected) {

    enum class State {
        ON, OFF
    }

    private var state = State.OFF

    private fun toggle() {
        state = if (state == State.OFF) State.ON else State.OFF
    }

    override fun function(pulse: Pulse): Pulse? {
        if (pulse.type == PulseType.HIGH) {
            return null
        }

        toggle()

        return when (state) {
            State.ON -> Pulse(PulseType.HIGH)
            State.OFF -> Pulse(PulseType.LOW)
        }
    }

}

class Conjunction(
    override val name: String,
    override var connected: List<Module> = listOf(),
    input: MutableList<Module> = mutableListOf()
) : Module(name, connected) {
    private var cache = mutableMapOf<String, PulseType>()

    var input = input
        set(value) {
            field = value
            cache = input.associate { it.name to PulseType.LOW }.toMutableMap()
        }

    fun function(source: Module, pulse: Pulse): Pulse {
        cache[source.name] = pulse.type

        val type = if (cache.values.distinct().count() == 1) {
            if (cache.values.first() === PulseType.HIGH) {
                PulseType.LOW
            } else {
                PulseType.HIGH
            }
        } else {
            PulseType.HIGH
        }

        return Pulse(type)
    }

    override fun function(pulse: Pulse): Pulse? {
        TODO("Not yet implemented")
    }
}