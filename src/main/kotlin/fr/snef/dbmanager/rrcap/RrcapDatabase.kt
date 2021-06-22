package fr.snef.dbmanager.rrcap

import fr.snef.dbmanager.Database
import fr.snef.dbmanager.rrcap.model.*
import java.io.File
import java.sql.Connection

object RrcapDatabase : Database() {
    override val dumpFolder = "sfr" + File.separator + "rrcap" + File.separator
    override val dbName = "rrcap_prod"

    override val filesToProcess: List<RrcapDatafile> = mutableListOf<RrcapDatafile>()
            .asSequence()
            .plus(Region.values().map { Site(it) })
            .plus(Region.values().map { NodeB(it) })
            .plus(Region.values().map { Bts(it) })
            .plus(Region.values().map { ENodeB(it) })
            .plus(Region.values().map { S1Bearer(it) })
            .plus(Region.values().map { S1BearerRoutes(it) })
            .plus(Region.values().map { Dpt(it) })
            .plus(Region.values().map { DptMlppp(it) })
            .plus(Region.values().map { DptVlan(it) })
            .plus(Region.values().map { DptIma(it) })
            .plus(Region.values().map { Fh(it) })
            .plus(Region.values().map { DummyVlan(it) })
            .plus(Region.values().map { PdhLink(it) })
            .plus(Region.values().map { AbisOverIpNsnAlu(it) })
            .plus(Region.values().map { AbisIpAddresses(it) })
            .plus(Region.values().map { Dpt3gIpHuawei(it) })
            .plus(Region.values().map { Dpt3gIpNokia(it) })
            .plus(Region.values().map { S1IpAddresses(it) })
            .toList()

    private val dumpFileNames = Region.values().map { it.name + ".taz" }

    override fun retrieveNewDump(): Boolean = dumpFileNames.map { getDumpFile(it).isFile }.all { it }

    override fun archiveDump() {
        dumpFileNames.forEach {
            getDumpFile(it).copyTo(getBackupFile("$formattedDate - $it"), true)
        }
    }

    override fun prepareDump() {
        dumpFileNames.forEach { extractArchive(it) }
    }

    override fun executePostImportActions(dbConnection: Connection) {
        // Nothing to do
    }
}