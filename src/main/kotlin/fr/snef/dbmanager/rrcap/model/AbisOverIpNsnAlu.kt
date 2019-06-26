package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class AbisOverIpNsnAlu(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "AbisoverIP-NSNALU"
    override val fileHeader = Header::class.java

    override val tableName = "ABIS_OVER_IP_NSNALU"
    override val tableHeader = listOf(
            "region_code",
            "abis",
            "subnet_bts"
    )

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE subnet_bts = subnet_bts"

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.ABIS])
            stmt.setString(++index, record[Header.SUBNET_BTS])
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
        SITE_BSC,
        BSC,
        PORT_BSC,
        SITE_BTS,
        BTS,
        PORT_BTS,
        STATUS,
        BANDWIDTH1,
        RESOLUTION,
        LIEN,
        SITE1,
        NODE1,
        PORT1,
        SITE2,
        NODE2,
        PORT2,
        TYPE,
        BANDWIDTH2,
        DEF_PDH,
        ETAT,
        SUPPLIER,
        DISTSFR,
        DISTFT,
        SUBLINKTYPE,
        COMMERCIALID,
        TECHNICALID,
        STATUSCHANGEDATE,
        ALIAS2_BSC,
        ALIAS2_BTS,
        ROUTE_SEQUENCE,
        ALIAS_3G_BTS,
        BSC_SUBNET_POOL,
        SUBNET_BTS,
        ADRESSE_OM_BTS,
        ADRESSE_GATEWAY,
        SUBNET_USER_PLANE,
        HSRP_VIRTUAL_GATEWAY,
        MDC1_PRIMARY_GATEWAY,
        MDC2_SECONDARY_GATEWAY,
        UP_ETPE0,
        UP_ETPE1,
        UP_ETPE2,
        UP_ETPE3,
        UP_ETPE4,
        UP_ETPE5,
        VLAN_TRXSIG,
        SUBNET_CONTROL_PLANE,
        VIRTUAL_GATEWAY,
        PRIMARY_GATEWAY,
        SECONDARY_GATEWAY,
        BCSU_0,
        BCSU_1,
        BCSU_2,
        BCSU_3,
        BCSU_4,
        BCSU_5,
        VLAN_OMUSIG,
        SUBNET_M_PLANE_OMU,
        VIRTUAL_GATEWAY_M_PLANE_OMU,
        PRIMARY_GATEWAY_M_PLANE_OMU,
        SECONDARY_GATEWAY_M_PLANE_OMU,
        BCSU_0_M_PLANE_OMU,
        BCSU_1_M_PLANE_OMU,
        BCSU_2_M_PLANE_OMU,
        BCSU_3_M_PLANE_OMU,
        BCSU_4_M_PLANE_OMU,
        BCSU_5_M_PLANE_OMU,
        SUBNET_INTERCO_CP_2,
        SUBNET_INTERCO_CP_1,
        SUBNET_INTERCO_OM_1,
        SUBNET_INTERCO_OM_2,
        TYPE_BSC,
        TYPE_BTS,
        RELATIVE_NAME,
        TEMPLATE
    }
}