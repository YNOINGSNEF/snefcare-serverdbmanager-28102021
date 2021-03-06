package fr.snef.dbmanager

import org.apache.commons.csv.CSVRecord
import java.io.File
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types

abstract class DataFile {
    protected enum class QueryType {
        INSERT, UPDATE
    }

    /**
     * Name of the file (without extension)
     */
    abstract val fileName: String
    abstract val fileHeader: Class<out Enum<*>>
    abstract val fileCharset: Charset
    abstract val fileExtension: String

    abstract val tableName: String
    protected abstract val tableHeader: List<String>
    protected open val onDuplicateKeySql = ""
    protected open val insertSelectSql: String? = null
    protected open val ignoreInsertErrors = false

    protected open val queryType = QueryType.INSERT
    val sqlQuery
        get() = when (queryType) {
            QueryType.INSERT -> insertSql
            QueryType.UPDATE -> updateSql
        }

    private val insertSql
        get() = "INSERT " + (if (ignoreInsertErrors) "IGNORE " else "") + "INTO $tableName " +
                tableHeader.joinToString(
                        separator = ",",
                        prefix = "(",
                        postfix = ")"
                ) + (
                if (insertSelectSql != null) {
                    insertSelectSql
                } else {
                    " VALUES " +
                            tableHeader.joinToString(
                                    separator = ",",
                                    prefix = "(",
                                    postfix = ")",
                                    transform = { "?" }
                            )
                }
                ) + onDuplicateKeySql

    protected open val tableUpdateFields = listOf<String>()
    protected open val tableUpdateWhereFields = listOf<String>()

    protected open val updateSql
        get() = "UPDATE $tableName SET " +
                tableUpdateFields.joinToString(separator = ",", transform = { "$it=?" }) +
                " WHERE " +
                tableUpdateWhereFields.joinToString(separator = ",", transform = { "$it=?" })

    val emptyTableSql get() = "TRUNCATE $tableName"

    open val delimiter = ';'
    open val lineSeparator = "\r\n"
    open val quoteChar: Char? = null
    open val ignoreEmptyLines = true
    open val hasHeaderLine = true

    abstract fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean

    fun getFullPath(rootPath: String, fileName: String? = null): Path =
        Paths.get(rootPath + File.separatorChar + (fileName ?: this.fileName) + "." + fileExtension)

    protected fun PreparedStatement.setNullableString(parameterIndex: Int, x: String?) {
        if (x != null) setString(parameterIndex, x)
        else setNull(parameterIndex, Types.VARCHAR)
    }

    protected fun PreparedStatement.setNullableInt(parameterIndex: Int, x: Int?) {
        if (x != null) setInt(parameterIndex, x)
        else setNull(parameterIndex, Types.INTEGER)
    }

    protected fun PreparedStatement.setNullableFloat(parameterIndex: Int, x: Float?) {
        if (x != null) setFloat(parameterIndex, x)
        else setNull(parameterIndex, Types.FLOAT)
    }

    protected fun PreparedStatement.setNullableDouble(parameterIndex: Int, x: Double?) {
        if (x != null) setDouble(parameterIndex, x)
        else setNull(parameterIndex, Types.DOUBLE)
    }

    protected fun PreparedStatement.setNullableBoolean(parameterIndex: Int, x: Boolean?) {
        if (x != null) setBoolean(parameterIndex, x)
        else setNull(parameterIndex, Types.BOOLEAN)
    }

    protected fun PreparedStatement.setNullableDate(parameterIndex: Int, x: Date?) {
        if (x != null) setDate(parameterIndex, x)
        else setNull(parameterIndex, Types.DATE)
    }

    protected fun PreparedStatement.setNullableTimestamp(parameterIndex: Int, x: Timestamp?) {
        if (x != null) setTimestamp(parameterIndex, x)
        else setNull(parameterIndex, Types.TIMESTAMP)
    }

    companion object {
        val CHARSET_ANSI: Charset = Charset.forName("Cp1252")
        val CHARSET_UTF_8: Charset = StandardCharsets.UTF_8
    }
}
