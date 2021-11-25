package fr.snef.dbmanager.anfr

import fr.snef.dbmanager.DataFile

abstract class AnfrDataFile : DataFile() {
    override val fileName = "SUP_" + javaClass.simpleName.toUpperCaseUnderscore()
    override val fileCharset = CHARSET_UTF_8
    override val fileExtension = "txt"

    override val tableName get() = fileName
    override val tableHeader get() = fileHeader.enumConstants.map { it.name }

    private fun String.toUpperCaseUnderscore() =
        replace(Regex("(?<=[a-z])[A-Z]")) { match -> "_" + match.value }.uppercase()
}
