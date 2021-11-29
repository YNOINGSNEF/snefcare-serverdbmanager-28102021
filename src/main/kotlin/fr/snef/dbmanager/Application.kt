package fr.snef.dbmanager

import fr.snef.dbmanager.anfr.AnfrDatabase
import fr.snef.dbmanager.comsis.ComsisDatabase
import fr.snef.dbmanager.free.FreeDatabase
import fr.snef.dbmanager.ocean.OceanDatabase
import fr.snef.dbmanager.orange.OrangeDatabase
import fr.snef.dbmanager.rrcap.RrcapDatabase
import java.io.PrintStream
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

lateinit var config: Config

private val databases get() = if (config.isLocal) databasesForLocalConfig else databasesForServerConfig

// Databases to be updated when local configuration is used, usually for testing from a local machine
private val databasesForLocalConfig = listOf(
    OrangeDatabase,
    AnfrDatabase,
    RrcapDatabase,
    OrangeDatabase
)

// Databases to be updated when server configuration is used
private val databasesForServerConfig = listOf(
    //ComsisDatabase,
    AnfrDatabase,
    RrcapDatabase,
    //OceanDatabase,
    FreeDatabase,
    OrangeDatabase
)

// By default, this will run the server configuration, and update the PROD databases
// --> you can use `local` as program argument to use the local configuration
// --> you can use `dev` as program argument to update DEV databases, otherwise PROD ones will be updated
fun main(args: Array<String>) {
    val useLocalConfiguration = args.contains("local")
    val updateDevDatabases = args.contains("dev")

    config = if (useLocalConfiguration) {
        Config.Local(updateDevDatabases)
    } else {
        Config.Server(updateDevDatabases)
    }

    println("> Starting with following settings: isLocal=$useLocalConfiguration, updateDevDatabases=$updateDevDatabases\n")

    println("> Initialising tasks\n")

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
        println()
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
