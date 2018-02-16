
abstract class Database {
    protected abstract val dumpFolderPath: String
    protected val dbUrl = "jdbc:mysql://v2068.phpnet.fr:3306"
    protected abstract val dbName: String
    protected abstract val dbUser: String
    protected abstract val dbPassword: String
    protected val batchSize = 1000

    abstract fun importDump()
}