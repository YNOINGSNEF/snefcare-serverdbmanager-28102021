import model.DataFile
import model.Region
import model.files.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.sql.BatchUpdateException
import java.sql.DriverManager
import java.util.concurrent.TimeUnit

private const val ROOT_PATH = "C:\\Users\\DEV_SNEF4\\Desktop\\Trans\\"

private const val MYSQL_URL = "jdbc:mysql://v2068.phpnet.fr:3306"
private const val MYSQL_DB = "rrcap"
private const val MYSQL_USER = "rrcap"
private const val MYSQL_PASSWORD = "9WBpJuDhRZ"

private const val BATCH_SIZE = 100

private val filesToProcess = listOf(
        Site(),
        NodeB(),
        Bts(),
        CelluleGsmDcs(),
        CelluleUmts(),
        CelluleLte()
)

fun main(args: Array<String>) {
    val globalStartTimeMillis = System.currentTimeMillis()

    DriverManager.getConnection("$MYSQL_URL/$MYSQL_DB?rewriteBatchedStatements=true", MYSQL_USER, MYSQL_PASSWORD).use { dbConnection ->
        dbConnection.autoCommit = false

        filesToProcess.asReversed().forEach { file ->
            val rows = dbConnection.createStatement().executeUpdate(file.deleteSql)
            println("--> Cleared table for \"${file.fileName}\" ($rows rows)")
        }

        println("--> Starting imports")

        filesToProcess.forEach { file ->
            val startTimeMillis = System.currentTimeMillis()

            dbConnection.prepareStatement(file.insertSql).use { stmt ->
                Region.values().forEach { region ->
                    createCsvParser(file, region).use { csvParser ->
                        val records = csvParser.records
                        records.forEachIndexed { index, record ->
                            val isValid = file.addBatch(stmt, record, region)
                            if (!isValid) println("Ignored line : ${region.name}-${file.fileName} -> " + record.toList())

                            if ((index > 0 && index % BATCH_SIZE == 0) || index == records.size - 1) {
                                try {
                                    stmt.executeBatch()
                                } catch (ex: BatchUpdateException) {
                                    println("Error ${ex.errorCode}: ${region.name} -> ${ex.message}")
                                }
                                dbConnection.commit()
                            }
                        }
                    }
                }
            }

            val diff = System.currentTimeMillis() - startTimeMillis
            println("--> Completed import of \"${file.fileName}\" in $diff milliseconds")
        }
    }

    val diff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - globalStartTimeMillis)
    println("--> Completed all tasks in $diff seconds")
}

private fun createCsvParser(file: DataFile, region: Region): CSVParser {
    val reader = Files.newBufferedReader(file.getFullPath(ROOT_PATH, region), file.charset)

    val csvFormat = CSVFormat.newFormat(file.delimiter)
            .withHeader(file.fileHeader)
            .withRecordSeparator(file.lineSeparator)
            .withIgnoreEmptyLines(file.ignoreEmptyLines)
            .withSkipHeaderRecord()

    return CSVParser(reader, csvFormat)
}