package atoll

import org.apache.commons.csv.CSVRecord
import java.io.File
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
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
    val emptyTableSql get() = "TRUNCATE $tableName"

    open val delimiter = ';'
    open val lineSeparator = "\r\n"
    open val ignoreEmptyLines = true
    open val charset: Charset = StandardCharsets.UTF_8

    abstract fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean

    fun getFullPath(rootPath: String): Path = Paths.get(rootPath + File.separatorChar + fileName + ".csv")

    protected fun PreparedStatement.setNullableString(parameterIndex: Int, x: String?) {
        if (x != null) setString(parameterIndex, x)
        else setNull(parameterIndex, Types.VARCHAR)
    }
}