package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Fh(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "Fh"
    override val fileHeader = Header::class.java

    override val tableName = "FH"
    override val tableHeader = listOf(
            "region_code",
            "site_g2r",
            "zpt",
            "fh",
            "fh_alias_1",
            "fh_alias_2",
            "status",
            "frequency_band",
            "supplier",
            "model"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.SITE].extractSiteG2R() ?: throw TypeCastException("Invalid G2R"))
            stmt.setString(++index, record[Header.ZPT])
            stmt.setString(++index, record[Header.FH])
            stmt.setString(++index, record[Header.FH_ALIAS_1])
            stmt.setString(++index, record[Header.FH_ALIAS_2])
            stmt.setString(++index, record[Header.STATUS])
            stmt.setNullableInt(++index, record[Header.FREQUENCY_BAND]?.takeIf { it != "-" }?.toInt())
            stmt.setNullableString(++index, record[Header.SUPPLIER]?.takeIf { it != "-" })
            stmt.setNullableString(++index, record[Header.MODELE]?.takeIf { it != "-" })
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
        SOUS_REGION,
        DEPARTEMENT,
        SITE,
        ZPT,
        FH,
        FH_ALIAS_1,
        FH_ALIAS_2,
        CREATED_DATE,
        LAST_MODIFIED_DATE,
        CREATED_BY,
        LAST_MODIFIED_BY,
        STATUS,
        CHANNEL,
        FREQUENCY,
        FREQUENCY_BAND,
        POLARISATION,
        PROTECTION,
        STATUS_CHANGE_DATE,
        SUPPLIER,
        SUP_SERVEUR,
        MODELE,
        IP
    }
}