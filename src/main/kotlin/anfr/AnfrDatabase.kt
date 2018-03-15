package anfr

import DataFile
import Database

object AnfrDatabase : Database() {
    override val dumpFolderPath = "D:\\dump\\anfr\\"
    override val dbName = "atoll"
    override val dbUser = "atoll"
    override val dbPassword = "Ye2sw49pxG"

    private val filesToProcess = listOf<DataFile>(
    )

    private const val dumpArchiveFilename = "20180228_Export_Etalab_Ref.zip"
    private const val dumpSubArchiveFilename = "20180228_Export_Etalab_Data.zip"

    override fun retrieveNewDump(): Boolean {
        return getLocalFile(dumpArchiveFilename).isFile
    }

    override fun backupDump() {
        getLocalFile(dumpArchiveFilename).copyTo(getLocalFile("$formattedDate.zip"), true)
    }

    override fun prepareDump() {
        extractArchive(dumpArchiveFilename)
        extractArchive(dumpSubArchiveFilename)
    }

    override fun importToDatabase() {
//        DriverManager.getConnection("$dbUrl/$dbName?rewriteBatchedStatements=true", dbUser, dbPassword).use { dbConnection ->
//            dbConnection.autoCommit = false
//
//            dbConnection.createStatement().use { stmt ->
//                stmt.addBatch("SET FOREIGN_KEY_CHECKS = 0")
//                filesToProcess.asReversed().forEach { file -> stmt.addBatch(file.emptyTableSql) }
//                stmt.addBatch("SET FOREIGN_KEY_CHECKS = 1")
//                stmt.executeBatch()
//                println("--> Cleared all tables")
//            }
//
//            filesToProcess.forEach { file ->
//                println("--> Starting import of \"${file.fileName}\"")
//                val startTimeMillis = System.currentTimeMillis()
//                dbConnection.prepareStatement(file.insertSql).use { stmt ->
//                    createCsvParser(file).use { csvParser ->
//                        val records = csvParser.records
//                        var batchCount = 0
//                        records.forEachIndexed({ index, record ->
//                            if (file.addBatch(stmt, record)) {
//                                batchCount++
//                            } else {
//                                println("Ignored line : ${file.fileName} -> " + record.toList())
//                            }
//
//                            if ((batchCount > 0 && batchCount % batchSize == 0) || index == records.size - 1) {
//                                try {
//                                    stmt.executeBatch()
//                                } catch (ex: BatchUpdateException) {
//                                    println("Error ${ex.errorCode}: ${ex.message} -> ${record.toList()}")
//                                } finally {
//                                    dbConnection.commit()
//                                }
//                            }
//                        })
//                    }
//                }
//
//                val diff = System.currentTimeMillis() - startTimeMillis
//                println("--> Completed import of \"${file.fileName}\" in $diff milliseconds")
//            }
//        }
    }

    override fun cleanDump() {
        getLocalFile(dumpArchiveFilename).delete()
        getLocalFile(dumpSubArchiveFilename).delete()
        getLocalFile().listFiles { _, name -> name.endsWith(".txt", true) }.forEach { it.delete() }
    }
}