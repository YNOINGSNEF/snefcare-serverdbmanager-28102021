package model.files.rrcap

import model.DataFile
import model.Region
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class DptVlan : DataFile() {
    override val fileName = "DPT-VLAN"
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

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
//        stmt.setString(1, region.name)
//        stmt.setString(2, record[Header.S1_NAME])
//        stmt.setString(3, record[Header.ENODEB_NAME])
//        stmt.addBatch()
        return true
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