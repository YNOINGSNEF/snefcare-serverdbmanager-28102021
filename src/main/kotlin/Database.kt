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

    private val rootPath = "D:" + File.separator + "dump" + File.separator
    protected abstract val dumpFolder: String

    private val dumpFolderPath get() = rootPath + dumpFolder
    private val archiveFolderPath get() = rootPath + "archive" + File.separator + dumpFolder

    private val dbUrl = "jdbc:mysql://db-admin.care-apps.fr:3306"
    protected abstract val dbName: String
    protected abstract val dbUser: String
    protected abstract val dbPassword: String
    private val batchSize = 10_000

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
        dbConnection.autoCommit = false

        dbConnection.createStatement().use { stmt ->
            stmt.addBatch("SET FOREIGN_KEY_CHECKS = 0")
            filesToProcess.asReversed().forEach { file -> stmt.addBatch(file.emptyTableSql) }
            stmt.addBatch("SET FOREIGN_KEY_CHECKS = 1")
            stmt.executeBatch()
            println("    > Cleared all tables")
        }

        filesToProcess.forEach { file ->
            val timeMillis = measureTimeMillis {
                dbConnection.prepareStatement(file.insertSql).use { stmt ->
                    var batchCount = 0
                    var index = 0
                    var batchStartIndex = index
                    createCsvParser(file).use { csvParser ->
                        csvParser.forEach({ record ->
                            index++

                            if (file.addBatch(stmt, record)) {
                                batchCount++
                            } else {
                                println("      > Ignored line : " + record.toList())
                            }

                            if (batchCount > 0 && batchCount % batchSize == 0) {
                                executeBatch(stmt, dbConnection, batchStartIndex, index)
                                batchStartIndex = index
                            }
                        })

                        executeBatch(stmt, dbConnection, batchStartIndex, index)
                    }
                }
            }

            println("    > \"${file.fileName}\" - Import completed in ${timeMillis.toFormattedElapsedTime()}")
        }

        dbConnection.autoCommit = true
    }

    private fun executeBatch(stmt: PreparedStatement, dbConnection: Connection, startIndex: Int, currIndex: Int) {
        try {
            stmt.executeBatch()
            dbConnection.commit()
            println("      > ${SimpleDateFormat("HH:mm:ss.SSS").format(Date())} Successfully inserted lines between index $startIndex and $currIndex")
        } catch (ex: BatchUpdateException) {
            dbConnection.rollback()
            println("      > Error ${ex.errorCode} on batch between index $startIndex and $currIndex : ${ex.message}")
        }
    }

    protected abstract fun executePostImportActions(dbConnection: Connection)

    protected fun cleanDump() {
        getLocalFile().listFiles().forEach { it.deleteRecursively() }
    }

    protected fun getLocalFile(filename: String = "") = File(dumpFolderPath + filename)
    protected fun getArchiveFile(filename: String = "") = File(archiveFolderPath + filename)

    protected fun extractArchive(archiveFilename: String) {
        when (archiveFilename.substringAfterLast(".").toLowerCase()) {
            "zip" -> extractZip(archiveFilename)
            "tar" -> extractTar(archiveFilename)
            "taz" -> extractTaz(archiveFilename)
            else -> throw UnsupportedOperationException("Unsupported archive format : $archiveFilename")
        }
    }

    private fun extractZip(archiveFilename: String) {
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

    private fun extractTar(archiveFilename: String) {
        val archiveFile = getLocalFile(archiveFilename)
        TarArchiveInputStream(archiveFile.inputStream()).use { tarArchiveInputStream ->
            while (true) {
                val tarEntry = tarArchiveInputStream.nextTarEntry ?: break
                if (tarEntry.isDirectory) continue

                val outputFile = getLocalFile(archiveFile.nameWithoutExtension + File.separator + tarEntry.name)
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
        val inputFile = getLocalFile(archiveFilename)
        val outputFile = getLocalFile(archiveFilename.substringBeforeLast(".") + ".tar")

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
            "&requireSSL=true",
            dbUser, dbPassword)
}