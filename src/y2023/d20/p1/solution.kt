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

//        if (modules.containsKey(name)) continue

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
            m.output = outs[m.name]!!.map { modules[it]!! }.toMutableList()
        }
    }

//    fun ff(module: Module, pulse: Pulse): Pair<Int, Int> {
//        var lowPulses = 0
//        var highPulses = 0
//
//        val modulesTouched = mutableListOf<Module>()
//
//        for (output in outs[module.name]!!) {
//            val destination = modules[output]!!
//            if (module is Broadcaster) {
//                if (pulse.type == PulseType.LOW) lowPulses++
//                if (pulse.type == PulseType.HIGH) highPulses++
//
//                destination.receive(module, pulse)
//                println("${module.name} ${pulse.type}-> ${destination.name}")
//            } else {
//                if (pulse.type == PulseType.LOW) lowPulses++
//                if (pulse.type == PulseType.HIGH) highPulses++
//
//                destination.receive(module, Pulse(pulse.type))
//
//                println("${module.name} ${pulse.type}-> ${destination.name}")
//            }
//
//            modulesTouched.add(destination)
//        }
//
//        for (next in modulesTouched) {
//            if (next is FlipFlop && pulse.type == PulseType.HIGH) {
//                return lowPulses to highPulses
//            }
//            val r = ff(next, next.produce())
//            lowPulses += r.first
//            highPulses += r.second
//        }
//
//        return lowPulses to highPulses
//    }

    val pulse = Pulse(PulseType.LOW)

    val pulses = mutableListOf(pulse)

    val broadcaster = modules["broadcaster"]!!
    pulses += broadcaster.process(pulse)

    pulses.println()

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
    override fun ack(source: Module, pulse: Pulse) {
        return
    }

    override fun produce(source: Module, pulse: Pulse): Pulse? = pulse
    override fun producable(pulse: Pulse): Boolean {
        return true
    }
}

class Sink(override val name: String) : Module(name, listOf()) {
    override fun ack(source: Module, pulse: Pulse) {
        return
    }

    override fun produce(source: Module, pulse: Pulse): Pulse? = pulse
    override fun producable(pulse: Pulse): Boolean = false
}

abstract class Module(
    open val name: String,
    open var output: List<Module> = listOf()
) {
    abstract fun ack(source: Module, pulse: Pulse)
    abstract fun produce(source: Module, pulse: Pulse): Pulse?
    abstract fun producable(pulse: Pulse): Boolean

    open fun process(pulse: Pulse): List<Pulse> {
        val pulseToBeSent = this.produce(this, pulse) ?: return listOf()

        val pulseReturned = mutableListOf(pulseToBeSent)

        this.output.forEach {
            print("$name ${pulseToBeSent.type} -> ${it.name}")
            println("")
            it.ack(this, pulseToBeSent)
        }

        this.output.forEach {
            pulseReturned += it.process(pulseToBeSent)
        }

        return pulseReturned
    }
}

data class FlipFlop(
    override val name: String,
    override var output: List<Module> = listOf()
) : Module(name, output) {

    enum class State {
        ON, OFF
    }

    private var state = State.OFF

    private fun toggle() {
        state = if (state == State.OFF) State.ON else State.OFF
    }

    override fun ack(source: Module, pulse: Pulse) {
        if (pulse.type == PulseType.HIGH) {
            return
        }

        toggle()
    }

    override fun produce(source: Module, pulse: Pulse): Pulse? {
        return when (state) {
            State.ON -> Pulse(PulseType.HIGH)
            State.OFF -> Pulse(PulseType.LOW)
        }
    }

    override fun producable(pulse: Pulse): Boolean {
        return pulse.type == PulseType.LOW
    }
}

class Conjunction(
    override val name: String,
    override var output: List<Module> = listOf(),
    var input: MutableList<Module> = mutableListOf()
) : Module(name, output) {
    val cache = input.associateWith { PulseType.LOW }.toMutableMap()

    override fun ack(source: Module, pulse: Pulse) {
        cache[source] = pulse.type
    }

    override fun produce(source: Module, pulse: Pulse): Pulse? {
        return Pulse(
            if (cache.values.all { it == PulseType.HIGH }) PulseType.LOW else PulseType.HIGH
        )
    }

    override fun producable(pulse: Pulse): Boolean {
        return true
    }
}