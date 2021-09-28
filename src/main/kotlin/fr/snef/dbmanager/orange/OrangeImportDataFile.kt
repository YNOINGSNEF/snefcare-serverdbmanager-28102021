package fr.snef.dbmanager.orange

import java.io.File

abstract class OrangeImportDataFile(private val dumpFolderPath: String) : OrangeDataFile() {

    companion object {
        const val prevSuffix = "PREV_RADIO"
        const val complementSuffix = "COMPLEMENT"
    }

    val dropTemporaryTableQuery get() = "DROP TABLE IF EXISTS $tableName"

    abstract val createTemporaryTableQuery: String
    open val createIndexesQueries = emptyList<String>()
    abstract val populateTemporaryTableQueries: List<String>

    protected fun fullPath(fileName: String) = dumpFolderPath + File.separatorChar + fileName + "." + fileExtension
}
