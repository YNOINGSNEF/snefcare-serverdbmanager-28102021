package ocean

import DataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.text.ParseException

abstract class OceanDataFile : DataFile() {
    override val fileCharset = DataFile.CHARSET_ANSI
    override val fileExtension = "csv"

    override val tableName get() = fileName
    override val tableHeader get() = fileHeader.enumConstants.map { it.name }

    override val quoteChar = '"'

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        return try {
            populateStatement(stmt, record)
            stmt.addBatch()
            true
        } catch (ex: NumberFormatException) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            false
        } catch (ex: ParseException) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            false
        } catch (ex: IllegalArgumentException) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            false
        } catch (ex: Exception) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            false
        }
    }

    abstract fun populateStatement(stmt: PreparedStatement, record: CSVRecord)

    protected fun String.toBool(): Boolean = when (toInt()) {
        1 -> true
        0 -> false
        else -> throw IllegalArgumentException("Content is not a boolean ($this)")
    }

    protected fun String.toBoolOrNull(): Boolean? = try {
        toBool()
    } catch (ex: Exception) {
        null
    }

    protected fun String.toTimestamp(): Timestamp = Timestamp.valueOf(this)

    protected fun String.toTimestampOrNull() = try {
        toTimestamp()
    } catch (ex: IllegalArgumentException) {
        null
    }
}