package fr.snef.dbmanager.free

import fr.snef.dbmanager.Database
import fr.snef.dbmanager.free.model.*
import java.io.File
import java.sql.Connection

object FreeDatabase : Database() {
    override val dumpFolder = "free" + File.separator + "radio" + File.separator
    override val dbName = "dump_free"

    private val dumpFileNames = getDumpFile()
            .takeIf { it.isDirectory }
            ?.listFiles { _, name -> name.endsWith(".csv", true) }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()

    override val filesToProcess = dumpFileNames.map { Site(it) }
            .plus(dumpFileNames.map { Antenne(it) })
            .plus(dumpFileNames.map { AntenneTilt(it) })
            .plus(dumpFileNames.map { Cell3G(it) })
            .plus(dumpFileNames.map { Cell4G(it) })

    override fun retrieveNewDump(): Boolean = dumpFileNames.isNotEmpty()

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