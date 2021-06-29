package fr.snef.dbmanager

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

abstract class Config {
    private val formattedDate: String = SimpleDateFormat("yyyy-MM-dd").format(Date())

    abstract val isDebug: Boolean

    abstract val dumpsRootPath: String
    abstract val archiveFolderPath: String

    abstract val shouldLogInFile: Boolean
    protected abstract val logFolderPath: String
    val logFolder get() = File(logFolderPath)
    val logFile get() = File("$logFolderPath$formattedDate.log")

    abstract val databaseUrl: String
    abstract val databaseUser: String
    abstract val databasePassword: String

    /**
     * Debug configuration, used for development from local machine
     */
    object Debug : Config() {
        override val isDebug = true

        override val dumpsRootPath = "/Users/sebastien/Downloads/snef/dump/"
        override val archiveFolderPath = dumpsRootPath + "archives/"

        override val shouldLogInFile = false
        override val logFolderPath = dumpsRootPath + "logs/"

        override val databaseUrl = "jdbc:mysql://mysql-admin.care-apps.fr:3306"
        override val databaseUser = Release.databaseUser
        override val databasePassword = Release.databasePassword
    }

    /**
     * Release configuration, used when run from production server
     */
    object Release : Config() {
        override val isDebug = false
        override val dumpsRootPath = File.separator + "dump" + File.separator
        override val archiveFolderPath = File.separator + "dump_archives" + File.separator

        override val shouldLogInFile = true
        override val logFolderPath = dumpsRootPath + "tools" + File.separator + "log" + File.separator

        override val databaseUrl = "jdbc:mysql://mysql"
        override val databaseUser = "admin"
        override val databasePassword = "_023HUdu6yQar8n4P_1f"
    }
}
