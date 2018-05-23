import anfr.AnfrDatabase
import comsis.ComsisDatabase
import ocean.OceanDatabase
import rrcap.RrcapDatabase
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

private val databases: List<Database> = listOf(
        ComsisDatabase,
        AnfrDatabase,
        RrcapDatabase,
        OceanDatabase
)

fun main(args: Array<String>) {
    println("> Initialising tasks\n")

    val timeMillis = measureTimeMillis {
        databases.forEach { db ->
            println("> ${db::class.java.simpleName} - Starting update")

            try {
                val newDumpUpdated = db.update()

                if (newDumpUpdated) {
                    println("> ${db::class.java.simpleName} - Finished update")
                } else {
                    println("> ${db::class.java.simpleName} - Update ignored, no new dump available")
                }
            } catch (ex: Exception) {
                println("> ${db::class.java.simpleName} - An error occurred while updating database")
                println()
                ex.printStackTrace(System.out)
            }

            println()
        }
    }

    println("> All tasks completed in ${timeMillis.toFormattedElapsedTime()}")
}

fun Long.toFormattedElapsedTime(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(hours)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes)
    val milliseconds = this - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds)
    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds)
}