package ocean

import Database
import java.io.File

object OceanDatabase : Database() {

    override val dumpFolder = "sfr" + File.separator + "ocean" + File.separator
    override val dbName = "TODO"
    override val dbUser = "TODO"
    override val dbPassword = "TODO"

    override val filesToProcess: List<OceanDataFile> = emptyList()

    private const val dumpFileName = "TODO.zip"

    override fun retrieveNewDump(): Boolean = getLocalFile(dumpFileName).isFile

    override fun archiveDump() {
        getLocalFile(dumpFileName).copyTo(getArchiveFile(formattedDate), true)
    }

    override fun prepareDump() {
        extractArchive(dumpFileName)
    }
}