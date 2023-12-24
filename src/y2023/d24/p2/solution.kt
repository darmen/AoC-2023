package y2023.d24.p2

import com.microsoft.z3.Context
import com.microsoft.z3.Status
import println
import readInput
import runMeasure


/**
 * 1. Download Z3 arm64 release from https://github.com/Z3Prover/z3/
 * 2. Specify java.library.path to the bin folder
 * 3. Specify DYLD_LIBRARY_PATH env var to the bin golder
 */
private fun solve() {
    val input = readInput()

    val ctx = Context()
    val solver = ctx.mkSolver()

    val (x, y, z, vx, vy, vz) = listOf("x", "y", "z", "vx", "vy", "vz").map { ctx.mkIntConst(it) }

    val hailstones = input.map {
        val first = it.split(" @ ").first()
        val second = it.split(" @ ").last()

        val (hx, hy, hz) = first.split(", ").map { t -> t.replace(" ", "").toLong() }
        val (sx, sy, sz) = second.split(", ").map { t -> t.replace(" ", "").toInt() }

        Hailstone(hx, hy, hz, sx, sy, sz)
    }

    for (i in hailstones.indices.take(3)) {
        val h = hailstones[i]
        val t = ctx.mkIntConst("t$i")

        solver.add(
            ctx.mkEq(
                // x + t*vx == hx + t*hvx
                ctx.mkAdd(x, ctx.mkMul(vx, t)),
                ctx.mkAdd(ctx.mkInt(h.x), ctx.mkMul(ctx.mkInt(h.vx), t))
            )
        )

        solver.add(
            ctx.mkEq(
                // y + t*vy == hy + t*hvy
                ctx.mkAdd(y, ctx.mkMul(vy, t)),
                ctx.mkAdd(ctx.mkInt(h.y), ctx.mkMul(ctx.mkInt(h.vy), t))
            )
        )

        solver.add(
            ctx.mkEq(
                // z + t*vz == hz + t*hvz
                ctx.mkAdd(z, ctx.mkMul(vz, t)),
                ctx.mkAdd(ctx.mkInt(h.z), ctx.mkMul(ctx.mkInt(h.vz), t))
            )
        )

        if (solver.check() == Status.SATISFIABLE) {
            solver.model.eval(ctx.mkAdd(x, ctx.mkAdd(y, z)), false)
                .println()
        }
    }
}

private operator fun <E> List<E>.component6(): E {
    return this[5]
}

fun main() {
    runMeasure { solve() }
}

private data class Hailstone(
    var x: Long,
    var y: Long,
    var z: Long,
    val vx: Int,
    val vy: Int,
    val vz: Int
)