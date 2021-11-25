package fr.snef.dbmanager.free

import fr.snef.dbmanager.Database
import fr.snef.dbmanager.config
import fr.snef.dbmanager.free.model.*
import java.io.File
import java.sql.Connection

object FreeDatabase : Database() {
    override val dumpFolder = "free" + File.separator + "radio" + File.separator
    override val dbName get() = if (config.useDevelopmentDb) "dump_free_dev" else "dump_free"

    private val dumpFileNames by lazy { // Should be lazy because config property is initialized in main() of Application.kt
        getDumpFile().takeIf { it.isDirectory }
            ?.listFiles { _, name -> name.endsWith(".csv", true) }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()
    }

    override val filesToProcess by lazy {
        dumpFileNames.asSequence().map { Site(it) }
            .plus(dumpFileNames.map { Antenna(it) })
            .plus(dumpFileNames.map { AntennaTilt(it) })
            .plus(dumpFileNames.map { Cell3G(it) })
            .plus(dumpFileNames.map { Cell4G(it) })
            .toList()
    }

    override fun retrieveNewDump(): Boolean {
        if (config.isLocal) return true
        return dumpFileNames.isNotEmpty()
    }

    override fun archiveDump() {
        // Nothing to do
    }

    override fun prepareDump() {
        // Nothing to do
    }

    override fun executePostImportActions(dbConnection: Connection) {
        // Nothing to do
    }
}
