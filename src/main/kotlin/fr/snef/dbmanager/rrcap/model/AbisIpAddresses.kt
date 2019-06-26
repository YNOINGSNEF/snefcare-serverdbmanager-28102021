package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class AbisIpAddresses(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "ABIS-IP-Addresses"
    override val fileHeader = Header::class.java

    override val tableName = "ABIS_IP_ADDRESSES"
    override val tableHeader = listOf(
            "region_code",
            "abis",
            "subnet_interco_abis_bts"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0

        try {
            if (record.size() < Header.SUBNET_INTERCO_ABIS_BTS.ordinal) return true

            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.ABIS])
            stmt.setString(++index, record[Header.SUBNET_INTERCO_ABIS_BTS])
            stmt.addBatch()
            return true
        } catch (ex: NumberFormatException) {
            stmt.clearParameters()
            return false
        } catch (ex: TypeCastException) {
            stmt.clearParameters()
            return false
        }
    }

    enum class Header {
        REGION,
        ABIS,
        ALIAS,
        SITE_RNC,
        RNC,
        PORT_RNC,
        SITE_NODEB,
        NODEB,
        PORT_NODEB,
        SUBNET_INTERCO_ABIS_BTS,
        IP_BTS_GTMU,
        CONTROL_PLANE_ADDRESS,
        USER_PLANE_ADDRESS,
        GTMU_ETH_IP,
        OTHER_SIDE_ETH_IP,
        SUBNET_INTERCO_ABIS_BSC,
        IP_ABIS_HSRP_MDC,
        IP_ABIS_MDC_ROUTER1,
        IP_ABIS_MDC_ROUTER2,
        IP_ABIS_BSC,
        SUBNET_DEV_IP_ABIS,
        DEV_IP_ABIS
    }
}