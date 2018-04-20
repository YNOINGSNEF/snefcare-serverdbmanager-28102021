import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.File
import java.nio.file.Files
import java.sql.BatchUpdateException
import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipFile

abstract class Database {
    protected val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    protected val formattedDate: String get() = dateFormat.format(Date())

    private val rootPath = "D:\\dump\\"
    protected abstract val dumpFolder: String

    private val dumpFolderPath get() = rootPath + dumpFolder
    private val archiveFolderPath get() = rootPath + "archive\\" + dumpFolder

    private val dbUrl = "jdbc:mysql://v2068.phpnet.fr:3306"
    protected abstract val dbName: String
    protected abstract val dbUser: String
    protected abstract val dbPassword: String
    private val batchSize = 1000

    protected abstract val filesToProcess: List<DataFile>

    fun update(): Boolean {
        if (!retrieveNewDump()) return false

        archiveDump()
        prepareDump()
        importFilesToDatabase()
        cleanDump()
        return true
    }

    protected abstract fun retrieveNewDump(): Boolean
    protected abstract fun archiveDump()
    protected abstract fun prepareDump()

    protected open fun importFilesToDatabase() {
        DriverManager.getConnection("$dbUrl/$dbName?rewriteBatchedStatements=true", dbUser, dbPassword).use { dbConnection ->
            dbConnection.autoCommit = false

            dbConnection.createStatement().use { stmt ->
                stmt.addBatch("SET FOREIGN_KEY_CHECKS = 0")
                filesToProcess.asReversed().forEach { file -> stmt.addBatch(file.emptyTableSql) }
                stmt.addBatch("SET FOREIGN_KEY_CHECKS = 1")
                stmt.executeBatch()
                println("  > Cleared all tables")
            }

            filesToProcess.forEach { file ->
                val startTimeMillis = System.currentTimeMillis()
                dbConnection.prepareStatement(file.insertSql).use { stmt ->
                    createCsvParser(file).use { csvParser ->
                        val records = csvParser.records
                        var batchCount = 0
                        records.forEachIndexed({ index, record ->
                            if (file.addBatch(stmt, record)) {
                                batchCount++
                            } else {
                                println("    > Ignored line : " + record.toList())
                            }

                            if ((batchCount > 0 && batchCount % batchSize == 0) || index == records.size - 1) {
                                try {
                                    stmt.executeBatch()
                                    dbConnection.commit()
                                } catch (ex: BatchUpdateException) {
                                    dbConnection.rollback()
                                    println("    > Error ${ex.errorCode} on batch between index ${index - batchSize} and $index : ${ex.message}")
                                }
                            }
                        })
                    }
                }

                val diff = System.currentTimeMillis() - startTimeMillis
                println("  > \"${file.fileName}\" - Import completed in ${diff.toFormattedElapsedTime()}")
            }
        }
    }

    protected abstract fun cleanDump()

    protected fun getLocalFile(filename: String = "") = File(dumpFolderPath + filename)
    protected fun getArchiveFile(filename: String = "") = File(archiveFolderPath + filename)

    protected fun extractArchive(archiveFilename: String) {
        ZipFile(getLocalFile(archiveFilename)).use { zipFile ->
            zipFile.entries().asSequence().forEach { zipEntry ->
                zipFile.getInputStream(zipEntry).use { inputStream ->
                    getLocalFile(zipEntry.name).outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
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