package rrcap

import Database
import rrcap.model.*
import java.io.File
import java.sql.Connection

object RrcapDatabase : Database() {
    override val dumpFolder = "sfr" + File.separator + "rrcap" + File.separator
    override val dbName = "rrcap_tmp"
    override val dbUser = "admin"
    override val dbPassword = "_023HUdu6yQar8n4P_1f"

    override val filesToProcess: List<RrcapDatafile> = mutableListOf<RrcapDatafile>()
            .plus(Region.values().map { Site(it) })
            .plus(Region.values().map { NodeB(it) })
            .plus(Region.values().map { Bts(it) })
            .plus(Region.values().map { S1Bearer(it) })
            .plus(Region.values().map { S1BearerRoutes(it) })
            .plus(Region.values().map { Dpt(it) })
            .plus(Region.values().map { DptMlppp(it) })
            .plus(Region.values().map { DptVlan(it) })

    private val dumpFileNames = Region.values().map { it.name + ".taz" }

    override fun retrieveNewDump(): Boolean = dumpFileNames.map { getLocalFile(it).isFile }.all { it }

    override fun archiveDump() {
        dumpFileNames.forEach {
            getLocalFile(it).copyTo(getArchiveFile("$formattedDate - $it"), true)
        }
    }

    override fun prepareDump() {
        dumpFileNames.forEach { extractArchive(it) }
    }

    override fun executePostImportActions(dbConnection: Connection) {
        // Nothing to do
    }
}