package fr.snef.dbmanager

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

abstract class Config {
    private val formattedDate: String = SimpleDateFormat("yyyy-MM-dd_H'h'mm").format(Date())

    abstract val isLocal: Boolean
    abstract val useDevelopmentDb: Boolean

    abstract val dumpsRootPath: String
    abstract val archiveFolderPath: String

    abstract val shouldLogInFile: Boolean
    protected abstract val logFolderPath: String
    val logFolder get() = File(logFolderPath)
    val logFile get() = File("$logFolderPath$formattedDate.log")

    abstract val databaseUrl: String
    val databaseUser = "admin"
    val databasePassword = "_023HUdu6yQar8n4P_1f"

    /**
     * Local configuration, used for development from local machine
     */
    data class Local(override val useDevelopmentDb: Boolean) : Config() {
        override val isLocal = true

        override val dumpsRootPath = "/Users/sebastien/Downloads/snef/dump/"
        override val archiveFolderPath = dumpsRootPath + "archives/"

        override val shouldLogInFile = false
        override val logFolderPath = dumpsRootPath + "logs/"

        override val databaseUrl = "jdbc:mysql://mysql-admin.care-apps.fr:3306"
    }

    /**
     * Server configuration, used when run from production server
     */
    data class Server(override val useDevelopmentDb: Boolean) : Config() {
        override val isLocal = false

        override val dumpsRootPath = File.separator + "dump" + File.separator
        override val archiveFolderPath = File.separator + "dump_archives" + File.separator

        override val shouldLogInFile = true
        override val logFolderPath = dumpsRootPath + "tools" + File.separator + "log" + File.separator

        override val databaseUrl = "jdbc:mysql://mysql"
        //
    }
}
