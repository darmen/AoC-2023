package y2023.d20.p1

import println
import readInput
import runMeasure

fun solve() {
    val input = readInput()

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
        }

        if (name.startsWith('&')) {
            modules[name.drop(1)] = Conjunction(name.drop(1))
            outs[name.drop(1)] = conns
        }

        if (name == "broadcaster") {
            modules[name] = Broadcaster()
            outs[name] = conns
        }

        sinks.addAll(conns.map { Sink(it) })

    }

    for (sink in sinks.filter { !modules.keys.contains(it.name) }) {
        modules[sink.name] = sink
    }
    println()


    outs.forEach { out ->
        out.value.forEach {
            if (!ins.containsKey(it)) {
                ins[it] = mutableListOf()
            }

            ins[it]!!.add(out.key)
        }
    }

    for (m in modules.values) {
        if (m is Conjunction) {
            m.input = ins[m.name]!!.map { modules[it]!! }.toMutableList()
        }
    }

    for (m in modules.values) {
        if (m !is Sink) {
            m.connected = outs[m.name]!!.map { modules[it]!! }.toMutableList()
        }
    }

    val counts = mutableMapOf(
        PulseType.LOW to 1000,
        PulseType.HIGH to 0
    )

    val m = modules["broadcaster"]!!
    for (k in 1..1000) {
        val queue = mutableListOf(m to Pulse(PulseType.LOW))
        var i = 0
        while (i < queue.size) {
            val item = queue[i]
            for (c in item.first.connected) {
                counts[item.second.type] = counts[item.second.type]!! + 1

//                println("${item.first.name} ${item.second.type} ${c.name}")

                val np =
                    (if (c is Conjunction) c.function(item.first, item.second)
                    else c.function(item.second)) ?: continue

                queue.add(c to np)
            }

            i++
        }
    }

    counts.mapValues { it.value }
        .values
        .fold(1) { acc, el -> acc * el }
        .println()

//    val res = ff(source, pulse)

//    ((res.first + 1) * res.second * 1_000_000).println()
}

fun main() {
    runMeasure { solve() }
}

data class Pulse(var type: PulseType)

enum class PulseType {
    HIGH, LOW
}

class Broadcaster : Module("broadcaster", listOf()) {
    override fun function(pulse: Pulse): Pulse? = pulse
}

class Sink(override val name: String) : Module(name, listOf()) {
    override fun function(pulse: Pulse): Pulse? = pulse
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

    fun function(source: Module, pulse: Pulse): Pulse? {
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