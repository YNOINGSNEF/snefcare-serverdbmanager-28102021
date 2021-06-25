package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Dpt3gIpHuawei(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "DPT_3G_IP_Huawei"
    override val fileHeader = Header::class.java

    override val tableName = "DPT_3G_IP_HUAWEI"
    override val tableHeader = listOf(
            "region_code",
            "circuit_name",
            "subnet_ip_om"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.CIRCUIT_NAME])
            stmt.setString(++index, record[Header.SUBNET_IP_OM_BTS])
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
        COMBI_BSC_RNC,
        PLAGE_NODEBID,
        BTS_NODEB,
        NODEBID,
        SUBNET_IP_OM_BTS,
        IP_O_M_BTS,
        IP_O_M_NODEB,
        SUBNET_OMU_COMBI_BSC_RNC,
        HSRP_ROUTEURS_1_2_DCN,
        INT_ROUTEUR_1_DCN,
        INT_ROUTEUR_2_DCN,
        INTERFACE_VIRTUELLE,
        OMU_A_PORT0,
        OMU_B_PORT0,
        SAU
    }
}