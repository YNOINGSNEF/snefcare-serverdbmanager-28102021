package ocean

import DataFile

abstract class OceanDataFile : DataFile() {
    override val fileCharset = DataFile.CHARSET_ANSI
    override val fileExtension = "csv"

    override val tableName get() = fileName
    override val tableHeader get() = fileHeader.enumConstants.map { it.name }

    override val quoteChar = '"'
}