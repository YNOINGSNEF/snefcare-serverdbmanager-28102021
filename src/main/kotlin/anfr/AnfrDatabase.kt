package anfr

import Database
import anfr.api.RestApi
import anfr.model.*
import java.io.File
import java.io.IOException
import java.sql.Connection
import java.text.ParseException
import java.util.*

object AnfrDatabase : Database() {
    override val dumpFolder = "anfr" + File.separator + "france" + File.separator
    override val dbName = "anfr_tmp"
    override val dbUser = "admin"
    override val dbPassword = "_023HUdu6yQar8n4P_1f"

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

    private const val dumpReferencesArchiveFilename = "ReferencesTables.zip"
    private const val dumpDataArchiveFilename = "DataTables.zip"

    override fun retrieveNewDump(): Boolean {
        val lastDumpDate = getLastDumpUpdate()
        cleanDump()

        try {
            val anfrResourceInfo = RestApi.getAnfrResources()

            val referenceResource = anfrResourceInfo.resources
                    .filter { it.title.contains("Tables de références") }
                    .maxBy { it.lastModifiedDateTime }

            val dataResource = anfrResourceInfo.resources
                    .filter { it.title.contains("Tables supports antennes emetteurs bandes") }
                    .maxBy { it.lastModifiedDateTime }

            return if (referenceResource != null
                    && dataResource != null
                    && (lastDumpDate == null || lastDumpDate.before(dataResource.lastModifiedDate))) {

                RestApi.downloadResourceFile(referenceResource, getLocalFile(dumpReferencesArchiveFilename))
                RestApi.downloadResourceFile(dataResource, getLocalFile(dumpDataArchiveFilename))
                true
            } else {
                false
            }
        } catch (ex: ParseException) {
            println(ex)
            return false
        } catch (ex: IOException) {
            println(ex)
            return false
        } catch (ex: RuntimeException) {
            println(ex)
            return false
        }
    }

    override fun archiveDump() {
        getLocalFile(dumpReferencesArchiveFilename).copyTo(getArchiveFile("$formattedDate - REF.zip"), true)
        getLocalFile(dumpDataArchiveFilename).copyTo(getArchiveFile("$formattedDate - DATA.zip"), true)
    }

    override fun prepareDump() {
        extractArchive(dumpReferencesArchiveFilename)
        extractArchive(dumpDataArchiveFilename)
    }

    override fun executePostImportActions(dbConnection: Connection) {
        // Nothing to do
    }

    private fun getLastDumpUpdate(): Date? = getArchiveFile()
            .listFiles()
            ?.mapNotNull {
                try {
                    dateFormat.parse(it.nameWithoutExtension.take(10))
                } catch (ex: ParseException) {
                    null
                }
            }
            ?.max()
}