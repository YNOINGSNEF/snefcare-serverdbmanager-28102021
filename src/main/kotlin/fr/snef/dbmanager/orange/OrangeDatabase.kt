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

    private val dumpFileNames = getDumpFile()
        .takeIf { it.isDirectory }
        ?.listFiles { _, name -> name.endsWith(".csv", true) }
        ?.map { it.nameWithoutExtension }
        ?: emptyList()

    private val importFiles
        get() = listOf(
            TmpSites.from(dumpFileNames, dumpFolderPath),
            TmpNetworkElements.from(dumpFileNames, dumpFolderPath),
            TmpEquipments.from(dumpFileNames, dumpFolderPath),
            TmpCells.from(dumpFileNames, dumpFolderPath),
            TmpCellComplements.from(dumpFileNames, dumpFolderPath)
        )

    override val filesToProcess = listOf(
        ProcedureSites,
        ProcedureAntennas,
        ProcedureCells2G,
        ProcedureCells3G,
        ProcedureCells4G,
        ProcedureAntennaTilts
    )

    override fun retrieveNewDump(): Boolean = dumpFileNames.isNotEmpty()

    override fun archiveDump() {
        // TODO Implement dump archive
    }

    override fun prepareDump() {
        // TODO Implement zip file extraction
    }

    override fun importFilesToDatabase(dbConnection: Connection) {
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
        }
        println(" done!")

        // Populate TMP tables
        importFiles.forEach { file ->
            file.populateTemporaryTableQueries.forEach { query ->
                print("    > Populating table ${file.tableName}...")
                var updateCount = -1
                val timeMillis = measureTimeMillis {
                    dbConnection.execute(query) { stmt -> updateCount = stmt.updateCount }
                }
                println(" $updateCount lines updated in ${timeMillis.toFormattedElapsedTime()}")
            }
        }

        // Truncate ORF tables
        filesToProcess.asReversed().forEach { file ->
            print("    > Truncating table ${file.tableName}...")
            dbConnection.execute(file.emptyTableSql)
            println(" done!")
        }

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