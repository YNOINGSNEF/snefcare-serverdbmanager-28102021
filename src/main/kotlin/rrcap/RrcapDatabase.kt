package rrcap

import Database
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import rrcap.model.*
import java.nio.file.Files
import java.sql.BatchUpdateException
import java.sql.DriverManager

object RrcapDatabase : Database() {

    override val dumpFolderPath = "C:\\Users\\DEV_SNEF4\\Desktop\\RRCAP_2018.02.12\\"
    override val dbName = "rrcap"
    override val dbUser = "rrcap"
    override val dbPassword = "9WBpJuDhRZ"

    private val filesToProcess = listOf(
            Site(),
            NodeB(),
            Bts(),
            CelluleGsmDcs(),
            CelluleUmts(),
            CelluleLte(),
            S1Bearer(),
            S1BearerRoutes(),
            Dpt(),
            DptMlppp(),
            DptVlan()
    )

    override fun importDump() {
        DriverManager.getConnection("$dbUrl/$dbName?rewriteBatchedStatements=true", dbUser, dbPassword).use { dbConnection ->
            dbConnection.autoCommit = false

            dbConnection.createStatement().use { stmt ->
                stmt.addBatch("SET FOREIGN_KEY_CHECKS = 0")
                filesToProcess.asReversed().forEach { file ->
                    stmt.addBatch(file.emptyTableSql)
                }
                stmt.addBatch("SET FOREIGN_KEY_CHECKS = 1")
                stmt.executeBatch()
                println("--> Cleared all tables")
            }

            filesToProcess.forEach { file ->
                println("--> Starting import of \"${file.fileName}\"")
                val startTimeMillis = System.currentTimeMillis()
                dbConnection.prepareStatement(file.insertSql).use { stmt ->
                    Region.values().forEach { region ->
                        println("--> Importing data from ${region.name}")
                        createCsvParser(file, region).use { csvParser ->
                            val records = csvParser.records
                            var batchCount = 0
                            records.forEachIndexed({ index, record ->
                                if (file.addBatch(stmt, record, region)) batchCount++
                                else println("Ignored line : ${region.name}-${file.fileName} -> " + record.toList())

                                if ((batchCount > 0 && batchCount % batchSize == 0) || index == records.size - 1) {
                                    try {
                                        stmt.executeBatch()
                                    } catch (ex: BatchUpdateException) {
                                        println("Error ${ex.errorCode}: ${ex.message} -> ${record.toList()}")
                                    } finally {
                                        dbConnection.commit()
                                    }
                                }
                            })
                        }
                    }
                }

                val diff = System.currentTimeMillis() - startTimeMillis
                println("--> Completed import of \"${file.fileName}\" in $diff milliseconds")
            }
        }
    }

    private fun createCsvParser(file: DataFile, region: Region): CSVParser {
        val reader = Files.newBufferedReader(file.getFullPath(dumpFolderPath, region), file.charset)

        val csvFormat = CSVFormat.newFormat(file.delimiter)
                .withHeader(file.fileHeader)
                .withRecordSeparator(file.lineSeparator)
                .withIgnoreEmptyLines(file.ignoreEmptyLines)
                .withSkipHeaderRecord()

        return CSVParser(reader, csvFormat)
    }
}