package fr.snef.dbmanager.orange

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.File
import java.nio.file.Files
import java.text.Normalizer

abstract class OrangeImportDataFile(
    protected val fileNames: List<String>,
    private val dumpFolderPath: String
) : OrangeDataFile() {

    companion object {
        const val prevSuffix = "PREV_RADIO"
        const val complementSuffix = "COMPLEMENT"
    }

    val dropTemporaryTableQuery get() = "DROP TABLE IF EXISTS $tableName"

    abstract val createTemporaryTableQuery: String
    open val createIndexesQueries = emptyList<String>()
    abstract val defaultFileHeaderColumns: List<String>
    abstract fun makePopulateTemporaryTableQueries(): List<Pair<String, String>> // List<(filename, query)>

    protected fun fullPath(fileName: String) = dumpFolderPath + File.separatorChar + fileName + "." + fileExtension

    protected fun retrieveHeaderColumns(): String {
        val fileName = fileNames.first()
        val reader = Files.newBufferedReader(getFullPath(dumpFolderPath, fileName), fileCharset)

        val csvFormat = CSVFormat.newFormat(delimiter)
            .withRecordSeparator(lineSeparator)
            .withIgnoreEmptyLines(ignoreEmptyLines)
            .withQuote(quoteChar)

        val fileHeaderColumns: Iterable<String>?
        CSVParser(reader, csvFormat).use { csvParser ->
            fileHeaderColumns = csvParser.firstOrNull()?.map { column ->
                column
                    .replace(' ', '_')
                    .replace("-", "_")
                    .replace("/", "_")
                    .replace("'", "_")
                    .replace(".", "")
                    .replace("%", "")
                    .replace("°", "")
                    .replace("(", "")
                    .replace(")", "")
                    .dropLastWhile { it == '_' }
                    .removeNonSpacingMarks()
                    .uppercase()
            }
        }

        return (fileHeaderColumns ?: defaultFileHeaderColumns).joinToString(",") { "@$it" }
    }

    private fun String.removeNonSpacingMarks() =
        Normalizer.normalize(this, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
}
