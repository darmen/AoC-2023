package y2022.d5.p1

import readInput

fun main() {
    val input = readInput()

    val m = mutableMapOf<Int, MutableList<String>>()

    for (i in input.indices) {
        val s = input[i]

        if (!s.contains("move")) {
            if (!s.contains("1")) {
                s.chunked(4).forEachIndexed { j, it ->
                    val crate = it.replace(" ", "")
                        .replace("[", "")
                        .replace("]", "")

                    if (crate != "") {
                        if (m.containsKey(j + 1)) {
                            m[j + 1]!!.add(crate)
                        } else {
                            m[j + 1] = mutableListOf(crate)
                        }
                    }
                }
            }
        } else {
            val n = s.split(" ")[1].toInt()
            val src = s.split(" ")[3].toInt()
            val dst = s.split(" ")[5].toInt()

            val l = m[src]!!.take(n)
            m[dst]!!.addAll(0, l.reversed())
            m[src] = m[src]!!.drop(n).toMutableList()
        }
    }

    m.entries.sortedBy { it.key }.forEach {
        print(it.value.first())
    }
}
