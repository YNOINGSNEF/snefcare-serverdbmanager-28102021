package fr.snef.dbmanager

import fr.snef.dbmanager.anfr.AnfrDatabase
import fr.snef.dbmanager.comsis.ComsisDatabase
import fr.snef.dbmanager.ocean.OceanDatabase
import fr.snef.dbmanager.rrcap.RrcapDatabase
import java.io.File
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

var isDebugEnabled = true

private val formattedDate: String = SimpleDateFormat("yyyy-MM-dd").format(Date())
private val logFolderPath
    get() = if (isDebugEnabled) "D:" + File.separator + "dump" + File.separator + "tools" + File.separator + "log" + File.separator
    else File.separator + "dump" + File.separator + "tools" + File.separator + "log" + File.separator
private val logFilename = "$logFolderPath$formattedDate.log"
private val databases: List<Database> = listOf(
        ComsisDatabase,
        AnfrDatabase,
        RrcapDatabase,
        OceanDatabase
)

fun main() {
    if (!isDebugEnabled) {
        File(logFolderPath).mkdirs()
        val output = PrintStream(File(logFilename))
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