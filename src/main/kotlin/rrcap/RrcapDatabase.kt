package rrcap

import Database
import rrcap.model.*
import java.io.File

object RrcapDatabase : Database() {

    override val dumpFolder = "sfr" + File.separator + "rrcap" + File.separator
    override val dbName = "rrcap"
    override val dbUser = "rrcap"
    override val dbPassword = "9WBpJuDhRZ"

    override val filesToProcess: List<RrcapDatafile> = mutableListOf<RrcapDatafile>()
            .plus(Region.values().map { Site(it) })
            .plus(Region.values().map { NodeB(it) })
            .plus(Region.values().map { Bts(it) })
            .plus(Region.values().map { CelluleGsmDcs(it) })
            .plus(Region.values().map { CelluleUmts(it) })
            .plus(Region.values().map { CelluleLte(it) })
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
}