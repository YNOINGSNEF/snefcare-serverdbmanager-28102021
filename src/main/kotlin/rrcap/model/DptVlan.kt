package rrcap.model

import org.apache.commons.csv.CSVRecord
import rrcap.Region
import rrcap.RrcapDatafile
import rrcap.Status
import rrcap.TypeLien
import java.sql.PreparedStatement

class DptVlan(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "DPT-VLAN"
    override val fileHeader = Header::class.java

    override val tableName = "DPT_VLAN"
    override val tableHeader = listOf(
            "region_code",
            "vlan",
            "route_sequence",
            "lien",
            "site1",
            "node1",
            "port1",
            "site2",
            "node2",
            "port2",
            "type",
            "status"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            val routeSequence = "T(\\d*)".toRegex()
                    .matchEntire(record[Header.SEQUENCE_NUMBER])?.groupValues?.getOrNull(1)
                    ?: throw NumberFormatException()

            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.VLAN].replace("Pré", "Prv"))
            stmt.setInt(++index, routeSequence.toInt())
            stmt.setString(++index, record[Header.LIEN])
            stmt.setString(++index, record[Header.SITE_1].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD_1])
            stmt.setString(++index, record[Header.PORT_1])
            stmt.setString(++index, record[Header.SITE_2].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD_2])
            stmt.setString(++index, record[Header.PORT_2])
            stmt.setString(++index, TypeLien.from(record[Header.TYPE]).label)
            stmt.setString(++index, Status.from(record[Header.STATUS]).label)
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
        VLAN,
        SITE_VLAN_1,
        NODE_VLAN_1,
        SITE_VLAN_2,
        NODE_VLAN_2,
        STATUS,
        BANDWIDTH_TH,
        RESOLUTION,
        LIEN,
        SITE_1,
        NOEUD_1,
        PORT_1,
        SITE_2,
        NOEUD_2,
        PORT_2,
        TYPE,
        BANDWIDTH,
        DEF_ETHERNET,
        ETAT,
        SUPPLIER,
        DISTSFR,
        DISTFT,
        SUBLINKTYPE,
        COMMERCIALID,
        TECHNICALID,
        STATUSCHANGEDATE,
        NOEUD_1_TRANS,
        PORT_1_TRANS,
        NOEUD_2_TRANS,
        PORT_2_TRANS,
        ALIAS2_RNC1,
        ALIAS2_RNC2,
        NOTES,
        TRAINFH,
        RELATIVE_NAME,
        SEQUENCE_NUMBER
    }
}