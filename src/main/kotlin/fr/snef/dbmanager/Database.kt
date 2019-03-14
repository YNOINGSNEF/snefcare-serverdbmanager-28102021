package fr.snef.dbmanager

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.z.ZCompressorInputStream
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedInputStream
import java.io.File
import java.nio.file.Files
import java.sql.BatchUpdateException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipFile
import kotlin.system.measureTimeMillis

abstract class Database {
    protected val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    protected val formattedDate: String get() = dateFormat.format(Date())

    private val rootPath
        get() = if (isDebugEnabled) "D:" + File.separator + "dump" + File.separator
        else File.separator + "dump" + File.separator
    protected abstract val dumpFolder: String

    private val dumpFolderPath get() = rootPath + dumpFolder
    private val archiveFolderPath
        get() = if (isDebugEnabled) rootPath + "archive" + File.separator + dumpFolder
        else File.separator + "dump_archives" + File.separator + dumpFolder

    private val dbUrl
        get() = if (isDebugEnabled) "jdbc:mysql://mysql-admin.care-apps.fr:3306"
        else "jdbc:mysql://mysql"
    protected abstract val dbName: String
    open val dbUser = "admin"
    open val dbPassword = "_023HUdu6yQar8n4P_1f"
    private val batchSize = 10_000
    private val maxRowCountBetweenBatchLog = 100_000

    protected abstract val filesToProcess: List<DataFile>

    fun update(): Boolean {
        if (!retrieveNewDump()) return false

        println("  > New dump available, backing it up")
        archiveDump()
        println("  > Preparing dump (unzipping, etc.)")
        prepareDump()

        getDatabaseConnection().use { dbConnection ->
            println("  > Starting import - ${Date()}")
            importFilesToDatabase(dbConnection)
            println("  > Running post import actions")
            executePostImportActions(dbConnection)
        }

        println("  > Cleaning dump")
        cleanDump()
        return true
    }

    protected abstract fun retrieveNewDump(): Boolean
    protected abstract fun archiveDump()
    protected abstract fun prepareDump()

    protected open fun importFilesToDatabase(dbConnection: Connection) {
        dbConnection.setUniqueChecksEnabled(false)
        dbConnection.setForeignKeyChecksEnabled(false)

        dbConnection.createStatement().use { stmt ->
            filesToProcess.asReversed().forEach { file -> stmt.addBatch(file.emptyTableSql) }
            stmt.executeBatch()
            println("    > Cleared all tables")
        }

        dbConnection.autoCommit = false

        filesToProcess.forEach { file ->
            println("    > \"${file.fileName}\" - Starting import")

            val timeMillis = measureTimeMillis {
                dbConnection.prepareStatement(file.insertSql).use { stmt ->
                    var batchCount = 0
                    var index = 0
                    var batchStartIndex = index
                    createCsvParser(file).use { csvParser ->
                        csvParser.forEach { record ->
                            index++

                            if (file.addBatch(stmt, record)) {
                                batchCount++
                            } else {
                                println("      > Ignored line : " + record.toList())
                            }

                            if (batchCount > 0 && batchCount % batchSize == 0) {
                                executeBatch(stmt, dbConnection, batchStartIndex, index, batchStartIndex % maxRowCountBetweenBatchLog == 0)
                                batchStartIndex = index
                            }
                        }

                        executeBatch(stmt, dbConnection, batchStartIndex, index, true)
                    }
                }
            }

            println("    > \"${file.fileName}\" - Import completed in ${timeMillis.toFormattedElapsedTime()}")
        }

        dbConnection.autoCommit = true
        dbConnection.setUniqueChecksEnabled(true)
        dbConnection.setForeignKeyChecksEnabled(true)
    }

    private fun executeBatch(stmt: PreparedStatement, dbConnection: Connection, startIndex: Int, currIndex: Int, logInsert: Boolean) {
        try {
            stmt.executeBatch()
            dbConnection.commit()
            if (logInsert) {
                println("      > ${SimpleDateFormat("HH:mm:ss.SSS").format(Date())} Successfully inserted lines between index $startIndex and $currIndex")
            }
        } catch (ex: BatchUpdateException) {
            dbConnection.rollback()
            println("      > Error ${ex.errorCode} on batch between index $startIndex and $currIndex : ${ex.message}")
        }
    }

    protected abstract fun executePostImportActions(dbConnection: Connection)

    protected fun cleanDump() {
        getDumpFile().listFiles().forEach { it.deleteRecursively() }
    }

    protected fun getDumpFile(filename: String = "") = File(dumpFolderPath + filename)
    protected fun getBackupFile(filename: String = "") = File(archiveFolderPath + filename)

    protected fun extractArchive(archiveFilename: String) {
        when (archiveFilename.substringAfterLast(".").toLowerCase()) {
            "zip" -> extractZip(archiveFilename)
            "tar" -> extractTar(archiveFilename)
            "taz" -> extractTaz(archiveFilename)
            else -> throw UnsupportedOperationException("Unsupported archive format : $archiveFilename")
        }
    }

    private fun extractZip(archiveFilename: String) {
        ZipFile(getDumpFile(archiveFilename)).use { zipFile ->
            zipFile.entries().asSequence().forEach { zipEntry ->
                zipFile.getInputStream(zipEntry).use { inputStream ->
                    getDumpFile(zipEntry.name).outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
        }
    }

    private fun extractTar(archiveFilename: String) {
        val archiveFile = getDumpFile(archiveFilename)
        TarArchiveInputStream(archiveFile.inputStream()).use { tarArchiveInputStream ->
            while (true) {
                val tarEntry = tarArchiveInputStream.nextTarEntry ?: break
                if (tarEntry.isDirectory) continue

                val outputFile = getDumpFile(archiveFile.nameWithoutExtension + File.separator + tarEntry.name)
                outputFile.parentFile?.let { parentFile ->
                    if (!parentFile.exists()) parentFile.mkdirs()
                }

                outputFile.outputStream().use { outputStream ->
                    tarArchiveInputStream.copyTo(outputStream)
                }
            }
        }
    }

    private fun extractTaz(archiveFilename: String) {
        val inputFile = getDumpFile(archiveFilename)
        val outputFile = getDumpFile(archiveFilename.substringBeforeLast(".") + ".tar")

        ZCompressorInputStream(BufferedInputStream(inputFile.inputStream())).use { inputStream ->
            outputFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        extractTar(outputFile.name)
    }

    private fun createCsvParser(file: DataFile): CSVParser {
        val reader = Files.newBufferedReader(file.getFullPath(dumpFolderPath), file.fileCharset)

        val csvFormat = CSVFormat.newFormat(file.delimiter)
                .withHeader(file.fileHeader)
                .withRecordSeparator(file.lineSeparator)
                .withIgnoreEmptyLines(file.ignoreEmptyLines)
                .withSkipHeaderRecord(file.hasHeaderLine)
                .withQuote(file.quoteChar)

        return CSVParser(reader, csvFormat)
    }

    private fun getDatabaseConnection() = DriverManager.getConnection("$dbUrl/$dbName?" +
            "rewriteBatchedStatements=true" +
            "&verifyServerCertificate=false" +
            "&useSSL=true" +
            "&requireSSL=true" +
            "&serverTimezone=Europe/Paris",
            dbUser, dbPassword)

    private fun Connection.setUniqueChecksEnabled(enable: Boolean) {
        createStatement().use { it.executeUpdate("SET UNIQUE_CHECKS = " + (if (enable) "1" else "0")) }
    }

    private fun Connection.setForeignKeyChecksEnabled(enable: Boolean) {
        createStatement().use { it.executeUpdate("SET FOREIGN_KEY_CHECKS = " + (if (enable) "1" else "0")) }
    }
}