package anfr

import Database
import anfr.model.*

object AnfrDatabase : Database() {
    override val dumpFolder = "anfr\\"
    override val dbName = "atoll"
    override val dbUser = "atoll"
    override val dbPassword = "Ye2sw49pxG"

    override val filesToProcess: List<AnfrDataFile> = listOf(
            Nature(),
            Proprietaire(),
            Exploitant(),
            TypeAntenne(),
            Station(),
            Support(),
            Antenne(),
            Emetteur(),
            Bande()
    )

    private const val dumpArchiveFilename = "20180228_Export_Etalab_Ref.zip"
    private const val dumpSubArchiveFilename = "20180228_Export_Etalab_Data.zip"

    override fun retrieveNewDump(): Boolean {
        return getLocalFile(dumpArchiveFilename).isFile
    }

    override fun archiveDump() {
        getLocalFile(dumpArchiveFilename).copyTo(getArchiveFile("$formattedDate.zip"), true)
    }

    override fun prepareDump() {
        extractArchive(dumpArchiveFilename)
        extractArchive(dumpSubArchiveFilename)
    }

    override fun cleanDump() {
        getLocalFile(dumpArchiveFilename).delete()
        getLocalFile(dumpSubArchiveFilename).delete()
        getLocalFile().listFiles { _, name -> name.endsWith(".txt", true) }.forEach { it.delete() }
    }
}