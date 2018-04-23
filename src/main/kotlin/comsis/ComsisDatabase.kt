package comsis

import Database
import comsis.model.LastComsis
import java.io.File

object ComsisDatabase : Database() {
    override val dumpFolder = "sfr" + File.separator + "comsis" + File.separator
    override val dbName = "comsis_tmp"
    override val dbUser = "admin"
    override val dbPassword = "_023HUdu6yQar8n4P_1f"

    override val filesToProcess = listOf(LastComsis())

    private val dumpFilename = with(LastComsis()) { "$fileName.$fileExtension" }

    override fun retrieveNewDump(): Boolean = getLocalFile(dumpFilename).isFile

    override fun archiveDump() {
        getLocalFile(dumpFilename).copyTo(getArchiveFile("$formattedDate.csv"), true)
    }

    override fun prepareDump() {
        // Nothing to do
    }
}