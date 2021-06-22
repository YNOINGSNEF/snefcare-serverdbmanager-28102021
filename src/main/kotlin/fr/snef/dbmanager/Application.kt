package fr.snef.dbmanager

import fr.snef.dbmanager.orange.OrangeDatabase
import java.io.PrintStream
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

/**
 * Possible values are:
 * - Config.Debug --> will use development settings (folder paths, databases, etc.)
 * - Config.Release --> will use production settings
 */
var config: Config = Config.Debug

private val databases: List<Database> = listOf(
        ComsisDatabase,
        AnfrDatabase,
        RrcapDatabase,
        OceanDatabase,
        FreeDatabase
)

fun main() {
    if (config.shouldLogInFile) {
        config.logFolder.mkdirs()
        val output = PrintStream(config.logFile)
        System.setOut(output)
        System.setErr(output)
    }

    println("> Initialising tasks\n")

    val timeMillis = measureTimeMillis {
        databases.forEach { db -> processDatabaseUpdate(db) }
    }

    println("> All tasks completed in ${timeMillis.toFormattedElapsedTime()}")
}

private fun processDatabaseUpdate(db: Database) {
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

fun Long.toFormattedElapsedTime(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(hours)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes)
    val milliseconds = this - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds)
    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds)
}