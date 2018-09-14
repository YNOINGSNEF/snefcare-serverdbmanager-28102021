package rrcap.model

import org.apache.commons.csv.CSVRecord
import rrcap.Region
import rrcap.RrcapDatafile
import rrcap.Status
import rrcap.TypeLien
import java.sql.PreparedStatement

class DptIma(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "DPT-IMA"
    override val fileHeader = Header::class.java

    override val tableName = "DPT_IMA"
    override val tableHeader = listOf(
            "region_code",
            "ib",
            "status_ib",
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
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.IMA])
            stmt.setString(++index, Status.from(record[Header.STATUS]).label)
            stmt.setString(++index, record[Header.LIEN])
            stmt.setString(++index, record[Header.SITE_1].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD_1])
            stmt.setString(++index, record[Header.PORT_1])
            stmt.setString(++index, record[Header.SITE_2].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD_2])
            stmt.setString(++index, record[Header.PORT_2])
            stmt.setString(++index, TypeLien.from(record[Header.TYPE]).label)
            stmt.setString(++index, Status.from(record[Header.ETAT]).label)
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
        IMA,
        SITE_IMA_1,
        NODE_IMA_1,
        SITE_IMA_2,
        NODE_IMA_2,
        STATUS,
        BANDWIDTH,
        RESOLUTION,
        LIEN,
        SITE_1,
        NOEUD_1,
        PORT_1,
        SITE_2,
        NOEUD_2,
        PORT_2,
        TYPE,
        BANDWIDTH_LIEN,
        DEF_PDH,
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
        RELATIVE_NAME
    }
}