import org.apache.commons.csv.CSVRecord
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.PreparedStatement
import java.sql.Types

abstract class DataFile {

    /**
     * Name of the file (without extension)
     */
    abstract val fileName: String
    abstract val fileHeader: Class<out Enum<*>>
    abstract val fileCharset: Charset
    protected open val fileExtension = ".csv"

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
    open val hasHeaderLine = true

    abstract fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean

    fun getFullPath(rootPath: String): Path = Paths.get(rootPath + File.separatorChar + fileName + fileExtension)

    protected fun PreparedStatement.setNullableString(parameterIndex: Int, x: String?) {
        if (x != null) setString(parameterIndex, x)
        else setNull(parameterIndex, Types.VARCHAR)
    }

    protected fun PreparedStatement.setNullableInt(parameterIndex: Int, x: Int?) {
        if (x != null) setInt(parameterIndex, x)
        else setNull(parameterIndex, Types.INTEGER)
    }

    protected fun PreparedStatement.setNullableDouble(parameterIndex: Int, x: Double?) {
        if (x != null) setDouble(parameterIndex, x)
        else setNull(parameterIndex, Types.DOUBLE)
    }

    protected fun PreparedStatement.setNullableBoolean(parameterIndex: Int, x: Boolean?) {
        if (x != null) setBoolean(parameterIndex, x)
        else setNull(parameterIndex, Types.BOOLEAN)
    }

    companion object {
        val CHARSET_ANSI: Charset = Charset.forName("Cp1252")
    }
}