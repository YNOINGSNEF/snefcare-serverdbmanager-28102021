package fr.snef.dbmanager.rrcap.model

import org.apache.commons.csv.CSVRecord
import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import fr.snef.dbmanager.rrcap.Status
import fr.snef.dbmanager.rrcap.TypeLien
import java.sql.PreparedStatement

class DptMlppp(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "DPT-MLPPP"
    override val fileHeader = Header::class.java

    override val tableName = "DPT_MLPPP"
    override val tableHeader = listOf(
            "region_code",
            "mlppp",
            "status_mlppp",
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

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            val routeGroups = "R(\\d*)T(\\d*)".toRegex()
                    .matchEntire(record[Header.SEQUENCE_NUMBER])?.groupValues
                    ?: throw NumberFormatException()

            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.MLPPP])
            stmt.setString(++index, Status.from(record[Header.STATUS]).label)
            stmt.setInt(++index, routeGroups[1].toInt())
            stmt.setInt(++index, routeGroups[2].toInt())
            stmt.setString(++index, record[Header.LIEN])
            stmt.setString(++index, record[Header.TRAINFH])
            stmt.setString(++index, record[Header.SITE_1].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD_1])
            stmt.setNullableString(++index, record[Header.PORT_1].takeIf { it.isNotBlank() })
            stmt.setString(++index, record[Header.SITE_2].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD_2])
            stmt.setNullableString(++index, record[Header.PORT_2].takeIf { it.isNotBlank() })
            stmt.setNullableString(++index, record[Header.BANDWIDTH].extractBandwidth())
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