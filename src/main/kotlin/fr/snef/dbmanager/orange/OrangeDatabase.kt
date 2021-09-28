package fr.snef.dbmanager.orange

import fr.snef.dbmanager.Database
import fr.snef.dbmanager.config
import fr.snef.dbmanager.orange.import.*
import fr.snef.dbmanager.orange.procedures.*
import fr.snef.dbmanager.toFormattedElapsedTime
import java.io.File
import java.sql.Connection
import java.sql.Statement
import kotlin.system.measureTimeMillis

object OrangeDatabase : Database() {
    override val dumpFolder = "orange" + File.separator + "radio" + File.separator

    override val dbName get() = if (config.isDebug) "dump_orf_dev" else "dump_orf"

    private const val dumpFileName = "dump.zip"

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
        ProcedureAntennaTilts
    )

    override fun retrieveNewDump(): Boolean = getDumpFile(dumpFileName).isFile

    override fun archiveDump() {
        getDumpFile(dumpFileName).copyTo(getBackupFile("ORF $formattedDate.zip"), true)
    }

    override fun prepareDump() {
        extractArchive(dumpFileName)
    }

    override fun importFilesToDatabase(dbConnection: Connection) {
        // Setup import files
        val dumpFileNames = getDumpFile()
            .takeIf { it.isDirectory }
            ?.listFiles { _, name -> name.endsWith(".csv", true) }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()

        val importFiles = listOf(
            TmpSites.from(dumpFileNames, dumpFolderPath),
            TmpNetworkElements.from(dumpFileNames, dumpFolderPath),
            TmpEquipments.from(dumpFileNames, dumpFolderPath),
            TmpCells.from(dumpFileNames, dumpFolderPath),
            TmpCellComplements.from(dumpFileNames, dumpFolderPath)
        )

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
            val count = file.populateTemporaryTableQueries.count()
            file.populateTemporaryTableQueries.forEachIndexed { index, query ->
                print("    > Populating table ${file.tableName} (${index + 1}/$count)...")
                var updateCount = -1
                val timeMillis = measureTimeMillis {
                    dbConnection.execute(query) { stmt -> updateCount = stmt.updateCount }
                }
                println(" $updateCount lines updated in ${timeMillis.toFormattedElapsedTime()}")
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
            println(" $updateCount lines updated in ${timeMillis.toFormattedElapsedTime()}")
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
