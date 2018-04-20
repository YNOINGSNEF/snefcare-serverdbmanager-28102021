import rrcap.RrcapDatabase
import java.util.concurrent.TimeUnit

private val databases: List<Database> = listOf(
//        ComsisDatabase,
//        AnfrDatabase
        RrcapDatabase
)

fun main(args: Array<String>) {
    println("> Initialising tasks\n")
    val startTimeMillis = System.currentTimeMillis()

    databases.forEach { db ->
        println("> ${db::class.java.simpleName} - Starting update")
        if (db.update()) {
            println("> ${db::class.java.simpleName} - Finished update")
        } else {
            println("> ${db::class.java.simpleName} - Update ignored, no new dump available")
        }
        println()
    }

    val diffMillis = System.currentTimeMillis() - startTimeMillis
    println("> All tasks completed in ${diffMillis.toFormattedElapsedTime()}")
}

fun Long.toFormattedElapsedTime(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(hours)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes)
    val milliseconds = this - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds)
    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds)
}