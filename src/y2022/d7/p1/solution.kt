package y2022.d7.p1

import java.util.*
import println
import readInput
import kotlin.collections.HashMap

fun main() {
    val sizes = mutableMapOf<String, Int>()
    val tree = mutableMapOf<String, String>()
    var currentDir = "/"

    val input = readInput()

    var latestCommand = "CD"

    for (i in input.indices) {
        val s = input[i]
        if (s.isCommand()) {
            latestCommand = s.extractCommand()
            if (s.isCd()) {
                val argument = s.extractArgument()
                if (argument == "..") {
                    if (currentDir != "/") currentDir = tree[currentDir]!!
                } else {
                    currentDir = argument
                }
            }

            continue
        }

        if (latestCommand == "LS") {
            if (s.isDir()) {
                tree[s.getDirName()] = currentDir
                continue
            }

            if (sizes.containsKey(currentDir)) {
                sizes[currentDir] = sizes[currentDir]!! + s.getFileSize()
            } else {
                sizes[currentDir] = s.getFileSize()
            }

            var p = tree[currentDir]!!

            while (p != "/") {
//                sizes[p] = sizes[p] + s.getFileSize()
            }

            // TODO – update the whole tree
        }
    }

    tree.println()
    sizes.println()
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