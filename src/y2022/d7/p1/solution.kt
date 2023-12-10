package y2022.d7.p1

import java.util.*
import println
import readInput

fun main() {
    val input = readInput()

    val sizes = mutableMapOf<String, Int>()

    var currentDir = "/"

    val tree = mutableMapOf<String, MutableList<String>>(
        currentDir to mutableListOf()
    )

    var latestCommand = "cd"

    for (i in 1..<input.size) {
        val s = input[i]

        if (s.isCommand()) {
            latestCommand = s.split(" ")[1]

            if (s.isCd()) {
                var argument = s.extractArgument()

                if (argument == "..") {
                    if (currentDir == "/") {
                        argument = "/"
                    } else {
                        argument = tree.filter { it.value.contains(currentDir) }.keys.first()
                    }
                }

                if (!tree.containsKey(argument)) tree[argument] = mutableListOf()

                currentDir = argument
            }

            continue
        }

        if (latestCommand == "ls") {
            if (s.isDir()) {
                tree[currentDir]!!.add(s.split("dir ").last())
            } else {
                if (sizes.containsKey(currentDir)) {
                    sizes[currentDir] = sizes[currentDir]!! + s.getFileSize()
                } else {
                    sizes[currentDir] = s.getFileSize()
                }
            }
        }

    }

    var res = 0
    tree.forEach {
        val sizeOfFiles = if (sizes.containsKey(it.key)) {
            sizes[it.key]!!
        } else 0

        val size = it.value.sumBy { t ->
            if (sizes.containsKey(t)) sizes[t]!! else 0
        } + sizeOfFiles

        if (size <= 100000) {
            res += size
        }
    }

    fun getSize(dir: String) {
        val sizeOfFiles = if (sizes.containsKey(dir)) {
            sizes[dir]!!
        } else 0


    }

    println(res)
}


enum class Command() {
    LS(), CD()
}

fun String.isDir(): Boolean = this.startsWith("dir ")
fun String.isFile(): Boolean = !this.startsWith("dir") && !this.startsWith("$")
fun String.getDirName(): String = this.split("dir ").last()
fun String.getFile(): File = File(this.split(" ").last(), this.split(" ").first().toInt())
fun String.getFileSize(): Int = this.split(" ").first().toInt()
fun String.isCommand(): Boolean = this.startsWith("$")
fun String.isLs(): Boolean = this.startsWith("$ ls")
fun String.isCd(): Boolean = this.startsWith("$ cd")
fun String.extractCommand(): String = this.split(" ")[1].uppercase(Locale.getDefault())
fun String.extractArgument(): String = this.split(" ")[2]

data class Item(
    val name: String,
    val size: Int = 0
)

data class Dir(
    val name: String,
    val files: MutableList<File> = mutableListOf(),
    val dirs: MutableList<Dir> = mutableListOf()
)

data class File(val name: String, val size: Int = 0)