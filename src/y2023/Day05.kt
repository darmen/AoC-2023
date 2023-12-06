package y2023

import println
import readInput
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min
import kotlin.streams.asStream

typealias Destination = String
typealias Source = String

data class Range(val source: Pair<Long, Long>, val destination: Pair<Long, Long>)
data class Seed(var start: Long, var end: Long)
data class Data(val destination: Destination, val ranges: MutableList<Range> = mutableListOf())

fun rec1(source: Source, m: Map<Source, Data>, sourceValue: Long): Long {

    if (!m.containsKey(source)) { // location
        return sourceValue
    }

    val data = m[source]!!

    var found = false
    var destinationValue: Long = -1

    for (i in 0..data.ranges.size - 1) {
        val range = data.ranges[i]

        if (sourceValue >= range.source.first && sourceValue <= range.source.second) {
            destinationValue = range.destination.first + (sourceValue - range.source.first)
            found = true
            break
        }

    }

    val result = if (found) destinationValue else sourceValue

    return rec1(data.destination, m, result)
}


fun main() {
    fun part1(input: List<String>): Long {
        val seeds = mutableListOf<Long>()
        val conversionMap = mutableMapOf<Source, Data>()

        var inConvertion = false
        var source = ""
        var destination = ""
        var destinationRangeStarts = mutableListOf<Long>()
        var sourceRangeStarts = mutableListOf<Long>()
        var rangeLength: Long = 0

        for (i in 0..input.size - 1) {
            val s = input[i]

            if (i == 0) {
                seeds.addAll(s.split("seeds: ").last().split(" ").map { it.toLong() })
                continue
            }

            if (s == "") {
                inConvertion = false

                continue
            }

            if (s.contains("-to-")) {
                inConvertion = true
                source = s.split(" ").first().split("-to-").first()
                destination = s.split(" ").first().split("-to-").last()
                continue
            }

            if (inConvertion) {
                val destinationRangeStart = s.split(" ")[0].toLong()
                val sourceRangeStart = s.split(" ")[1].toLong()
                destinationRangeStarts.add(destinationRangeStart)
                sourceRangeStarts.add(sourceRangeStart)
                rangeLength = s.split(" ")[2].toLong()

                if (conversionMap.containsKey(source)) {
                    conversionMap[source]!!.ranges.add(
                        Range(
                            source = sourceRangeStart to sourceRangeStart + rangeLength - 1,
                            destination = destinationRangeStart to destinationRangeStart + rangeLength - 1
                        )
                    )
                } else {
                    conversionMap[source] = Data(
                        destination,
                        mutableListOf(
                            Range(
                                source = sourceRangeStart to sourceRangeStart + rangeLength - 1,
                                destination = destinationRangeStart to destinationRangeStart + rangeLength - 1
                            )
                        )
                    )
                }
//                }

                continue
            }

        }

        val locationValues = mutableListOf<Long>()

        seeds.forEachIndexed { i, s ->
            locationValues.add(rec1("seed", conversionMap, s))
        }

        return locationValues.min()
    }


    fun part2(input: List<String>): Long {

        var seeds = mutableListOf<LongRange>()
        val conversionMap = mutableMapOf<Source, Data>()

        var inConvertion = false
        var source = ""
        var destination = ""
        var destinationRangeStarts = mutableListOf<Long>()
        var sourceRangeStarts = mutableListOf<Long>()
        var rangeLength: Long = 0

        for (i in 0..input.size - 1) {
            val s = input[i]

            if (i == 0) {
                seeds =
                    s.split("seeds: ")
                        .last()
                        .split(" ")
                        .map { it.toLong() }
                        .chunked(2)
                        .map { (it.first()..<it.first()+it.last()) }
                        .toMutableList()

                continue
            }

            if (s == "") {
                inConvertion = false
                continue
            }

            if (s.contains("-to-")) {
                inConvertion = true
                source = s.split(" ").first().split("-to-").first()
                destination = s.split(" ").first().split("-to-").last()
                continue
            }

            if (inConvertion) {
                val destinationRangeStart = s.split(" ")[0].toLong()
                val sourceRangeStart = s.split(" ")[1].toLong()
                destinationRangeStarts.add(destinationRangeStart)
                sourceRangeStarts.add(sourceRangeStart)
                rangeLength = s.split(" ")[2].toLong()

                if (conversionMap.containsKey(source)) {
                    conversionMap[source]!!.ranges.add(
                        Range(
                            source = sourceRangeStart to sourceRangeStart + rangeLength - 1,
                            destination = destinationRangeStart to destinationRangeStart + rangeLength - 1
                        )
                    )
                } else {
                    conversionMap[source] = Data(
                        destination,
                        mutableListOf(
                            Range(
                                source = sourceRangeStart to sourceRangeStart + rangeLength - 1,
                                destination = destinationRangeStart to destinationRangeStart + rangeLength - 1
                            )
                        )
                    )
                }

                continue
            }

        }

        val result = AtomicLong(100000000000000000L)
        seeds.forEach {s ->
            s.asSequence().asStream().parallel().forEach {seed ->
                result.getAndAccumulate(rec1("seed", conversionMap, seed), ::min)
            }
        }

        return result.get()
    }

//    part1(readInput(5, 1)).println()
    part2(readInput(5, 2)).println()
}
