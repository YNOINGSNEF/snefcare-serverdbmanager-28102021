package fr.snef.dbmanager.comsis

import fr.snef.dbmanager.Database
import fr.snef.dbmanager.comsis.model.LastComsis
import java.io.File
import java.sql.Connection

object ComsisDatabase : Database() {
    override val dumpFolder = "sfr" + File.separator + "comsis" + File.separator
    override val dbName = "comsis"

    override val filesToProcess = listOf(LastComsis())

    private val dumpFilename = with(LastComsis()) { "$fileName.$fileExtension" }

    override fun retrieveNewDump(): Boolean = getDumpFile(dumpFilename).isFile

    override fun archiveDump() {
        getDumpFile(dumpFilename).copyTo(getBackupFile("$formattedDate.csv"), true)
    }

    override fun prepareDump() {
        // Nothing to do
    }

    override fun executePostImportActions(dbConnection: Connection) {
        // Nothing to do
    }
}