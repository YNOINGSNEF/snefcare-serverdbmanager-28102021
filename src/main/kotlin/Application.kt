import anfr.AnfrDatabase
import comsis.ComsisDatabase
import java.util.concurrent.TimeUnit

private val databases: List<Database> = listOf(
        ComsisDatabase,
        AnfrDatabase
)

fun main(args: Array<String>) {
    println("> Initialising tasks\n")
    val startTimeMillis = System.currentTimeMillis()

    databases.forEach { db ->
        println("> ${db::class.java.simpleName} - Starting update")
        if (db.update()) {
            println("> ${db::class.java.simpleName} - Finished update")
        } else {
            println("> ${db::class.java.simpleName} - Ignored update, no new dump available")
        }
        println()
    }

    val diff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTimeMillis)
    println("> Completed all tasks in $diff seconds")
}