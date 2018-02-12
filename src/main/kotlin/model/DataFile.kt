package model

import org.apache.commons.csv.CSVRecord
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.PreparedStatement
import java.sql.Types

abstract class DataFile {

    abstract val fileName: String
    abstract val fileHeader: Class<out Enum<*>>

    protected abstract val tableName: String
    protected abstract val tableHeader: List<String>
    protected open val onDuplicateKeySql = ""

    val insertSql
        get() = "INSERT INTO $tableName" +
                tableHeader.joinToString(
                        separator = ",",
                        prefix = "(",
                        postfix = ")"
                ) +
                "VALUES" +
                tableHeader.joinToString(
                        separator = ",",
                        prefix = "(",
                        postfix = ")",
                        transform = { _ -> "?" }
                ) +
                onDuplicateKeySql
    val deleteSql get() = "DELETE FROM $tableName"

    open val delimiter = ';'
    open val lineSeparator = "\r\n"
    open val ignoreEmptyLines = true
    open val charset: Charset = CHARSET_ANSI

    abstract fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean

    fun getFullPath(rootPath: String, region: Region): Path =
            Paths.get(rootPath + region.name + File.separatorChar + region.name + "-" + fileName + ".csv")

    protected fun String.extractSiteName() = "(\\d*)\\s*-\\s*(.*)".toRegex().matchEntire(this)?.groupValues?.get(2)?.takeIf { it.isNotBlank() }
    protected fun String.extractSiteG2R() = "(\\d*)\\s*-\\s*(.*)".toRegex().matchEntire(this)?.groupValues?.get(1)?.takeIf { it.isNotBlank() }
    protected fun String.extractBandwidth() = takeIf { it != "-" && it != "Not Specified" && !it.isBlank() }
    protected fun PreparedStatement.setNullableString(parameterIndex: Int, x: String?) {
        if (x != null) setString(parameterIndex, x)
        else setNull(parameterIndex, Types.VARCHAR)
    }

    companion object {
        private val CHARSET_ANSI = Charset.forName("Cp1252")
    }
}