package fr.snef.dbmanager.anfr

import fr.snef.dbmanager.Database
import fr.snef.dbmanager.anfr.api.RestApi
import fr.snef.dbmanager.anfr.model.*
import fr.snef.dbmanager.config
import java.io.File
import java.io.IOException
import java.sql.Connection
import java.text.ParseException
import java.util.*

object AnfrDatabase : Database() {
    override val dumpFolder = "anfr" + File.separator + "france" + File.separator
    override val dbName = "anfr_prod"

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
        if (config.isDebug) return true

        val lastDumpDate = getLastDumpUpdate()
        cleanDump()

        try {
            val anfrResourceInfo = RestApi.getAnfrResources()

            val referenceResource = anfrResourceInfo.resources
                // .filter { it.title.contains("Table de référence") }
                .filter { it.title.contains("référence") }
                .maxByOrNull { it.lastModifiedDateTime }

            val dataResource = anfrResourceInfo.resources
                // .filter { it.title.contains("Tables supports antennes emetteurs bandes") }
                .filter { it.title.contains("supports antennes") }
                .maxByOrNull { it.lastModifiedDateTime }

            return if (referenceResource != null
                    && dataResource != null
                    && (lastDumpDate == null || lastDumpDate.before(dataResource.lastModifiedDate))) {

                RestApi.downloadResourceFile(referenceResource, getDumpFile(dumpReferencesArchiveFilename))
                RestApi.downloadResourceFile(dataResource, getDumpFile(dumpDataArchiveFilename))
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
        getDumpFile(dumpReferencesArchiveFilename).copyTo(getBackupFile("$formattedDate - REF.zip"), true)
        getDumpFile(dumpDataArchiveFilename).copyTo(getBackupFile("$formattedDate - DATA.zip"), true)
    }

    override fun prepareDump() {
        extractArchive(dumpReferencesArchiveFilename)
        extractArchive(dumpDataArchiveFilename)
    }

    override fun executePostImportActions(dbConnection: Connection) {
        // Nothing to do
    }

    private fun getLastDumpUpdate(): Date? = getBackupFile()
        .listFiles()
        ?.mapNotNull {
            try {
                dateFormat.parse(it.nameWithoutExtension.take(10))
            } catch (ex: ParseException) {
                null
            }
        }
        ?.maxOrNull()
}
