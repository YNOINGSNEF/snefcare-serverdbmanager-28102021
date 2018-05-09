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

    override fun executePostImportActions(dbConnection: Connection) {
        val truncateBtsCarrierSysTable = "TRUNCATE TABLE BTS_CARRIER_SYS"
        val populateBtsCarrierSysTable = "INSERT INTO BTS_CARRIER_SYS (bts, carrier, system)\n" +
                "SELECT BTS.bts, CELL_2G.carrier, CELL_2G.system\n" +
                "FROM BTS\n" +
                "JOIN CELL_2G ON CELL_2G.bts_nodeB = BTS.bts\n" +
                "GROUP BY BTS.bts, CELL_2G.carrier, CELL_2G.system"

        val truncateNodeBCarrierSysTable = "TRUNCATE TABLE NODEB_CARRIER_SYS"
        val populateNodeBCarrierSysTable = "INSERT INTO NODEB_CARRIER_SYS (nodeB, carrier, system)\n" +
                "SELECT NODEB.nodeB, CELL_2G.carrier, CELL_2G.system\n" +
                "FROM NODEB\n" +
                "JOIN CELL_2G ON CELL_2G.bts_nodeB = NODEB.nodeB\n" +
                "GROUP BY NODEB.nodeB, CELL_2G.carrier, CELL_2G.system\n" +
                "UNION DISTINCT\n" +
                "SELECT NODEB.nodeB, CELL_3G.carrier, CELL_3G.system\n" +
                "FROM NODEB\n" +
                "JOIN CELL_3G ON CELL_3G.nodeB = NODEB.nodeB\n" +
                "GROUP BY NODEB.nodeB, CELL_3G.carrier, CELL_3G.system\n" +
                "UNION DISTINCT\n" +
                "SELECT NODEB.nodeB, CELL_4G.carrier, CELL_4G.system\n" +
                "FROM NODEB\n" +
                "JOIN CELL_4G ON CELL_4G.eNodeB = NODEB.nodeB\n" +
                "GROUP BY NODEB.nodeB, CELL_4G.carrier, CELL_4G.system"

        dbConnection.createStatement().use { stmt ->
            stmt.executeUpdate(truncateBtsCarrierSysTable)
            stmt.executeUpdate(populateBtsCarrierSysTable)

            stmt.executeUpdate(truncateNodeBCarrierSysTable)
            stmt.executeUpdate(populateNodeBCarrierSysTable)
        }
    }
}