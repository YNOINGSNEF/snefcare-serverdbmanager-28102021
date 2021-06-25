package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import fr.snef.dbmanager.rrcap.Status
import fr.snef.dbmanager.rrcap.TypeLien
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class PdhLink(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "PdhLink"
    override val fileHeader = Header::class.java

    override val tableName = "PDH_LINK"
    override val tableHeader = listOf(
            "region_code",
            "lien",
            "site1",
            "g2r1",
            "node1",
            "port1",
            "site2",
            "g2r2",
            "node2",
            "port2",
            "type",
            "status",
            "supplier",
            "commercialId"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.LIEN])
            stmt.setString(++index, record[Header.SITE_1])
            stmt.setNullableString(++index, record[Header.SITE_1].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD_1])
            stmt.setString(++index, record[Header.PORT_1])
            stmt.setString(++index, record[Header.SITE_2])
            stmt.setNullableString(++index, record[Header.SITE_2].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD_2])
            stmt.setString(++index, record[Header.PORT_2])
            stmt.setString(++index, TypeLien.from(record[Header.TYPE]).label)
            stmt.setString(++index, Status.from(record[Header.ETAT]).label)
            stmt.setString(++index, record[Header.SUPPLIER])
            stmt.setString(++index, record[Header.COMMERCIAL_ID])
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
        LIEN,
        ALIAS_1,
        ALIAS_2,
        NOTES,
        CREATED_DATE,
        LAST_MODIFIED_DATE,
        CREATED_BY,
        LAST_MODIFIED_BY,
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
        DIST_SFR,
        DIST_FT,
        SUB_LINK_TYPE,
        COMMERCIAL_ID,
        TECHNICAL_ID,
        STATUS_CHANGE_DATE,
        NOM_DU_BEARER,
        ETAT_DU_BEARER,
        CANALISATION,
        DEBIT_GARANTI,
        DEBIT_MAX,
        MODULATION,
        SHAPER,
    }
}