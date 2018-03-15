package comsis

import DataFile
import Database
import comsis.model.LastComsis
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.sql.BatchUpdateException
import java.sql.DriverManager

object ComsisDatabase : Database() {
    override val dumpFolderPath = "D:\\dump\\sfr\\comsis\\"
    override val dbName = "atoll"
    override val dbUser = "atoll"
    override val dbPassword = "Ye2sw49pxG"

    private val filesToProcess = listOf<DataFile>(
            LastComsis()
    )

    override fun importDump() {
        DriverManager.getConnection("$dbUrl/$dbName?rewriteBatchedStatements=true", dbUser, dbPassword).use { dbConnection ->
            dbConnection.autoCommit = false

            dbConnection.createStatement().use { stmt ->
                stmt.addBatch("SET FOREIGN_KEY_CHECKS = 0")
                filesToProcess.asReversed().forEach { file -> stmt.addBatch(file.emptyTableSql) }
                stmt.addBatch("SET FOREIGN_KEY_CHECKS = 1")
                stmt.executeBatch()
                println("--> Cleared all tables")
            }

            filesToProcess.forEach { file ->
                println("--> Starting import of \"${file.fileName}\"")
                val startTimeMillis = System.currentTimeMillis()
                dbConnection.prepareStatement(file.insertSql).use { stmt ->
                    createCsvParser(file).use { csvParser ->
                        val records = csvParser.records
                        var batchCount = 0
                        records.forEachIndexed({ index, record ->
                            if (file.addBatch(stmt, record)) {
                                batchCount++
                            } else {
                                println("Ignored line : ${file.fileName} -> " + record.toList())
                            }

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

                val diff = System.currentTimeMillis() - startTimeMillis
                println("--> Completed import of \"${file.fileName}\" in $diff milliseconds")
            }
        }
    }

    private fun createCsvParser(file: DataFile): CSVParser {
        val reader = Files.newBufferedReader(file.getFullPath(dumpFolderPath), file.fileCharset)

        val csvFormat = CSVFormat.newFormat(file.delimiter)
                .withHeader(file.fileHeader)
                .withRecordSeparator(file.lineSeparator)
                .withIgnoreEmptyLines(file.ignoreEmptyLines)
                .withSkipHeaderRecord(file.hasHeaderLine)

        return CSVParser(reader, csvFormat)
    }
}