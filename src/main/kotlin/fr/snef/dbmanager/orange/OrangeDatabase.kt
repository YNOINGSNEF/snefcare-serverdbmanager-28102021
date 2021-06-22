package fr.snef.dbmanager.orange

import fr.snef.dbmanager.Database
import fr.snef.dbmanager.config
import fr.snef.dbmanager.orange.model.Antenna
import fr.snef.dbmanager.orange.model.AntennaCell
import fr.snef.dbmanager.orange.model.AntennaDetails
import fr.snef.dbmanager.orange.model.Site
import java.io.File
import java.sql.Connection

object OrangeDatabase : Database() {
    override val dumpFolder = "orange" + File.separator + "radio" + File.separator

    override val dbName get() = if (config.isDebug) "dump_orf_dev" else "dump_orf"

    private val dumpFileNames = getDumpFile()
            .takeIf { it.isDirectory }
            ?.listFiles { _, name -> name.endsWith(".csv", true) }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()

    override val filesToProcess = dumpFileNames.mapNotNull { Site.from(it) }
            .plus(dumpFileNames.mapNotNull { Antenna.from(it) })
            .plus(dumpFileNames.mapNotNull { AntennaCell.from(it) })
            .plus(dumpFileNames.mapNotNull { AntennaDetails.from(it) })

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