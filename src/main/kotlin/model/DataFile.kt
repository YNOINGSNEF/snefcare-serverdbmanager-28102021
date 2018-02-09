package model

import org.apache.commons.csv.CSVRecord
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.PreparedStatement

abstract class DataFile {

    abstract val fileName: String
    abstract val fileHeader: Class<out Enum<*>>

    protected abstract val tableName: String
    protected abstract val tableHeader: List<String>

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
                )
    val deleteSql get() = "DELETE FROM $tableName"

    open val delimiter = ';'
    open val lineSeparator = "\r\n"
    open val ignoreEmptyLines = true
    open val charset: Charset = CHARSET_ANSI

    open fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean = false // TODO Make it abstract

    fun getFullPath(rootPath: String, region: Region): Path =
            Paths.get(rootPath + region.name + File.separatorChar + region.name + "-" + fileName + ".csv")

    companion object {
        private val CHARSET_ANSI = Charset.forName("Cp1252")
    }
}