import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.File
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipFile

abstract class Database {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    protected val formattedDate: String get() = dateFormat.format(Date())

    protected abstract val dumpFolderPath: String
    protected val dbUrl = "jdbc:mysql://v2068.phpnet.fr:3306"
    protected abstract val dbName: String
    protected abstract val dbUser: String
    protected abstract val dbPassword: String
    protected val batchSize = 1000

    fun update(): Boolean {
        if (!retrieveNewDump()) return false

        backupDump()
        prepareDump()
        importToDatabase()
        cleanDump()
        return true
    }

    protected abstract fun retrieveNewDump(): Boolean
    protected abstract fun backupDump()
    protected abstract fun prepareDump()
    protected abstract fun importToDatabase()
    protected abstract fun cleanDump()

    protected fun getLocalFile(filename: String = "") = File(dumpFolderPath + filename)

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

    protected fun createCsvParser(file: DataFile): CSVParser {
        val reader = Files.newBufferedReader(file.getFullPath(dumpFolderPath), file.fileCharset)

        val csvFormat = CSVFormat.newFormat(file.delimiter)
                .withHeader(file.fileHeader)
                .withRecordSeparator(file.lineSeparator)
                .withIgnoreEmptyLines(file.ignoreEmptyLines)
                .withSkipHeaderRecord(file.hasHeaderLine)

        return CSVParser(reader, csvFormat)
    }
}