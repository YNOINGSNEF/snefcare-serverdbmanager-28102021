package ocean

import DataFile

abstract class OceanDataFile : DataFile() {
    override val fileCharset = DataFile.CHARSET_UTF_8
    override val fileExtension = "txt"

    override val tableName get() = fileName
    override val tableHeader get() = fileHeader.enumConstants.map { it.name }
}