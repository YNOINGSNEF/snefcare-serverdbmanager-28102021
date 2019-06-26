package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class S1IpAddresses(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "S1-IP-Addresses"
    override val fileHeader = Header::class.java

    override val tableName = "S1_IP_ADRESSES"
    override val tableHeader = listOf(
            "region_code",
            "s1_name",
            "ip_om_enodeb",
            "subnet_interco",
            "ip_control_plan",
            "ip_user_plan"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.S1])
            stmt.setString(++index, record[Header.IP_O_M_ENODEB])
            stmt.setString(++index, record[Header.SUBNET_INTERCO])
            stmt.setString(++index, record[Header.IP_S1_X2_CP])
            stmt.setString(++index, record[Header.IP_S1_X2_UP])
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
        S1,
        ALIAS1,
        POOL_MME,
        ENODEB,
        ENODEB_HOSTNAME,
        SEGW,
        SEGW_HOSTNAME,
        SUBNET_INTERCO,
        IP_ENODEB,
        IP_CPE,
        IP_O_M_ENODEB,
        IP_SEGW,
        IP_S1_X2_CP,
        IP_S1_X2_UP,
        IP_PERAN,
        IP_S_P_GW,
        TEMPLATE_S1IP
    }
}