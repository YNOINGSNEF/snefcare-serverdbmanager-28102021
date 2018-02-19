package atoll

import Database

object AtollDatabase : Database() {

    override val dumpFolderPath = "C:\\Users\\DEV_SNEF4\\Desktop\\Atoll\\"
    override val dbName = "atoll"
    override val dbUser = "atoll"
    override val dbPassword = "Ye2sw49pxG"

    override fun importDump() {
        TODO()
    }
}