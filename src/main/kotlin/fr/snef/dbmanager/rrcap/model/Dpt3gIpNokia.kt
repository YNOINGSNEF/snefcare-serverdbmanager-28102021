package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Dpt3gIpNokia(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "DPT_3G_IP_Nokia"
    override val fileHeader = Header::class.java

    override val tableName = "DPT_3G_IP_NOKIA"
    override val tableHeader = listOf(
            "region_code",
            "circuit_name",
            "gateway_address",
            "subnet_ip_om"
    )

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE circuit_name = circuit_name"

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            val ipRegex = "^([0-9]*.[0-9]*.[0-9]*.)([0-9]*)(/[0-9]*)$".toRegex()
            val subnetIp = record[Header.ADRESSE_GATEWAY]
                    .replace(ipRegex) { res ->
                        res.groupValues[1] +
                                (res.groupValues[2].toInt() - 1) +
                                res.groupValues[3]
                    }

            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.CIRCUIT_NAME])
            stmt.setString(++index, record[Header.ADRESSE_GATEWAY])
            stmt.setString(++index, subnetIp)
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
        CIRCUIT_NAME,
        RNC,
        PLAGE_NODE_BID,
        NODEB,
        NODEBID,
        PLAGE_IP_NODEB,
        O_M_SUBNET,
        ADRESSE_AXC_O_M1,
        ADRESSE_LMP,
        ADRESSE_MWAM,
        ADRESSE_O_M_BTS_O_M2,
        ADRESSE_GATEWAY,
        PLAGE_RNC,
        ADRESSE_OMU1,
        ADRESSE_NEMU
    }
}