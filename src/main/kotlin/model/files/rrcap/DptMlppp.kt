package model.files.rrcap

import model.DataFile
import model.Region
import model.Status
import model.TypeLien
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class DptMlppp : DataFile() {
    override val fileName = "DPT-MLPPP"
    override val fileHeader = Header::class.java

    override val tableName = "DPT_MLPPP"
    override val tableHeader = listOf(
            "region_code",
            "mlppp",
            "route_number",
            "route_sequence",
            "lien",
            "train_fh",
            "site1",
            "node1",
            "port1",
            "site2",
            "node2",
            "port2",
            "bandwidth",
            "type",
            "status"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
        try {
            val routeGroups = "R(\\d*)T(\\d*)".toRegex()
                    .matchEntire(record[Header.SEQUENCE_NUMBER])?.groupValues
                    ?: throw NumberFormatException()

            stmt.setString(1, region.name)
            stmt.setString(2, record[Header.MLPPP])
            stmt.setInt(3, routeGroups[1].toInt())
            stmt.setInt(4, routeGroups[2].toInt())
            stmt.setString(5, record[Header.LIEN])
            stmt.setString(6, record[Header.TRAINFH])
            stmt.setString(7, record[Header.SITE_1].extractSiteG2R())
            stmt.setString(8, record[Header.NOEUD_1])
            stmt.setNullableString(9, record[Header.PORT_1].takeIf { it.isNotBlank() })
            stmt.setString(10, record[Header.SITE_2].extractSiteG2R())
            stmt.setString(11, record[Header.NOEUD_2])
            stmt.setNullableString(12, record[Header.PORT_2].takeIf { it.isNotBlank() })
            stmt.setNullableString(13, record[Header.BANDWIDTH].extractBandwidth())
            stmt.setString(14, TypeLien.from(record[Header.TYPE]).label)
            stmt.setString(15, Status.from(record[Header.ETAT]).label)
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
        MLPPP,
        SITE_MLPPP_1,
        NODE_MLPPP_1,
        SITE_MLPPP_2,
        NODE_MLPPP_2,
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
        NOTES,
        TRAINFH,
        RELATIVE_NAME,
        SEQUENCE_NUMBER
    }
}