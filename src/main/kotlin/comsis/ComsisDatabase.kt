package comsis

import Database
import comsis.model.LastComsis

object ComsisDatabase : Database() {
    override val dumpFolder = "sfr\\comsis\\"
    override val dbName = "atoll"
    override val dbUser = "atoll"
    override val dbPassword = "Ye2sw49pxG"

    override val filesToProcess = listOf(LastComsis())

    private const val dumpFilename = "last_comsis_15-03-2018_070001.csv"

    override fun retrieveNewDump(): Boolean = getLocalFile(dumpFilename).isFile

    override fun archiveDump() {
        getLocalFile(dumpFilename).copyTo(getArchiveFile("$formattedDate.csv"), true)
    }

    override fun prepareDump() {
        // Nothing to do
    }
}