package ocean

import DataFile
import java.sql.Timestamp

abstract class OceanDataFile : DataFile() {
    override val fileCharset = DataFile.CHARSET_ANSI
    override val fileExtension = "csv"

    override val tableName get() = fileName
    override val tableHeader get() = fileHeader.enumConstants.map { it.name }

    override val quoteChar = '"'

    protected fun String.toTimestamp(): Timestamp = Timestamp.valueOf(this)

    protected fun String.toTimestampOrNull() = try {
        toTimestamp()
    } catch (ex: IllegalArgumentException) {
        null
    }
}