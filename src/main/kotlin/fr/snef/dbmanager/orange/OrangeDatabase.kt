package fr.snef.dbmanager.orange

import fr.snef.dbmanager.Database
import fr.snef.dbmanager.config
import fr.snef.dbmanager.orange.import.*
import fr.snef.dbmanager.orange.procedures.*
import fr.snef.dbmanager.toFormattedElapsedTime
import java.io.File
import java.sql.Connection
import java.sql.Statement
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.measureTimeMillis

object OrangeDatabase : Database() {
    override val dumpFolder = "orange" + File.separator + "quartz" + File.separator
    override val dbName get() = if (config.useDevelopmentDb) "dump_orf_dev" else "dump_orf"

    // Populated during `retrieveNewDump` step
    private var dumpFileName = "--"

    override val filesToProcess = listOf(
        ProcedureSites,
        ProcedureCells2G,
        ProcedureCells3G,
        ProcedureCells4G,
        ProcedureCells5G,
        ProcedureAntennas,
        ProcedureAntenna.Cell2G,
        ProcedureAntenna.Cell3G,
        ProcedureAntenna.Cell4G,
        ProcedureAntenna.Cell5G,
        ProcedureAntennaTilts,
        ProcedureAntennaState
    )

    override fun retrieveNewDump(): Boolean {
        val dumpNameRegex = "FLUX_GENERIQUE_NORIA_([0-9]{8}).*".toRegex()
        val dumpDateFormat = SimpleDateFormat("ddMMyyyy")
        val files = getDumpFile().listFiles { _, name -> name.matches(dumpNameRegex) } ?: emptyArray()

        println("    > Found ${files.count()} new dump file(s)")

        val latestDumpFile = files
            .mapNotNull { file ->
                println("      > $file")

                val rawDate = dumpNameRegex.find(file.name)?.groups?.get(1)?.value ?: return@mapNotNull null
                val date = dumpDateFormat.parse(rawDate)

                file to date
            }
            .maxByOrNull { (_, date) -> date }
            ?.first
            ?: files.firstOrNull() // In case ORF changes the file name pattern...
            ?: return false

        dumpFileName = latestDumpFile.name
        println("    > Using latest dump file: $dumpFileName")

        return true
    }

    override fun archiveDump() {
        getDumpFile(dumpFileName).copyTo(getBackupFile("ORF $formattedDate.zip"), true)
    }

    override fun prepareDump() {
        extractArchive(dumpFileName)
    }

    override fun importFilesToDatabase(dbConnection: Connection) {
        val extractedDumpFolder = dumpFileName.substringBeforeLast(".")

        // Setup import files
        val dumpFileNames = getDumpFile(extractedDumpFolder)
            .takeIf { it.isDirectory }
            ?.listFiles { _, name -> name.endsWith(".csv", true) }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()

        val dumpFolder = dumpFolderPath + extractedDumpFolder + File.separator
        println("    > Looking for files in following dump folder: '$dumpFolder'")

        val importFiles = listOf(
            TmpSites.from(dumpFileNames, dumpFolder),
            TmpNetworkElements.from(dumpFileNames, dumpFolder),
            TmpEquipments.from(dumpFileNames, dumpFolder),
            TmpCells.from(dumpFileNames, dumpFolder),
            TmpCellComplements.from(dumpFileNames, dumpFolder)
        ).mapNotNull { it }

        if (importFiles.isEmpty()) {
            print("    > WARNING: Skipping database update as no dump file is available!")
            return
        }

        // Setup DB
        dbConnection.setUniqueChecksEnabled(false)
        dbConnection.setForeignKeyChecksEnabled(false)

        // Drop TMP tables
        print("    > Dropping temporary tables...")
        importFiles.asReversed().forEach { file ->
            dbConnection.execute(file.dropTemporaryTableQuery)
        }
        println(" done!")

        // Create TMP tables
        print("    > (Re)creating temporary tables...")
        importFiles.forEach { file ->
            dbConnection.execute(file.createTemporaryTableQuery)
            file.createIndexesQueries.forEach { query -> dbConnection.execute(query) }
        }
        println(" done!")
        println()

        // Populate TMP tables
        importFiles.forEach { file ->
            val fileQueries = file.makePopulateTemporaryTableQueries()
            fileQueries.forEachIndexed { index, (fileName, query) ->
                print("    > Populating table ${file.tableName} (${index + 1}/${fileQueries.count()}, $fileName)...")
                var updateCount = -1
                val timeMillis = measureTimeMillis {
                    dbConnection.execute(query) { stmt -> updateCount = stmt.updateCount }
                }
                println(" $updateCount lines updated in ${timeMillis.toFormattedElapsedTime()} (ended on ${Date()})")
            }
        }

        println()

        // Truncate ORF tables
        filesToProcess.asReversed().filter { it.shouldTruncate }.forEach { file ->
            print("    > Truncating table ${file.tableName}...")
            dbConnection.execute(file.emptyTableSql)
            println(" done!")
        }

        println()

        // Populate ORF tables
        filesToProcess.forEach { file ->
            print("    > Populating table ${file.tableName}...")
            var updateCount = -1
            val timeMillis = measureTimeMillis {
                dbConnection.execute(file.procedureQuery) { stmt -> updateCount = stmt.updateCount }
            }
            println(" $updateCount lines updated in ${timeMillis.toFormattedElapsedTime()} (ended on ${Date()})")
        }

        dbConnection.setUniqueChecksEnabled(true)
        dbConnection.setForeignKeyChecksEnabled(true)
    }

    override fun executePostImportActions(dbConnection: Connection) {
        // Nothing to do
    }

    private fun Connection.execute(query: String, postAction: (Statement) -> Unit = {}) {
        createStatement().use { stmt ->
            stmt.execute(query.cleanup())
            postAction(stmt)
        }
    }

    private fun String.cleanup() = trim().replace(Regex("(\\s)+"), " ")
}
