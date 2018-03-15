import comsis.ComsisDatabase
import java.util.concurrent.TimeUnit

private val databases = listOf<Database>(
        ComsisDatabase
//        AtollDatabase
)

fun main(args: Array<String>) {
    println("--> Initialising tasks")
    val startTimeMillis = System.currentTimeMillis()

    databases.forEach { it.importDump() }

    val diff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTimeMillis)
    println("--> Completed all tasks in $diff seconds")
}