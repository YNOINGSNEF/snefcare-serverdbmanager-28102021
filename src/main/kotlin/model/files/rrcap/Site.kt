package model.files.rrcap

import model.DataFile
import model.Region
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.NumberFormat
import java.util.*

class Site : DataFile() {
    override val fileName = "Site"
    override val fileHeader = Header::class.java

    override val tableName = "SITE"
    override val tableHeader = listOf(
            "region_code",
            "num_g2r",
            "name",
            "x_coord",
            "y_coord",
            "z_coord"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
        var index = 0
        val siteName = record[Header.SITE].extractSiteName()
        if (siteName != null) {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.ALIAS_SITE])
            stmt.setString(++index, siteName)
            stmt.setNullableDouble(++index, record[Header.COORDX].replace(',', '.').toDoubleOrNull())
            stmt.setNullableDouble(++index, record[Header.COORDY].replace(',', '.').toDoubleOrNull())
            stmt.setNullableDouble(++index, record[Header.COORDZ].replace(',', '.').toDoubleOrNull())
            stmt.addBatch()
            return true
        }

        return false
    }

    enum class Header {
        REGION,
        ALIAS_REGION,
        PROVINCE,
        SOUS_REGION,
        ALIAS_SOUS_REGION,
        DEPARTEMENT,
        ALIAS_DEPARTEMENT,
        SITE,
        ALIAS_SITE,
        TOWNCITY,
        ZIP,
        RESPONSIBLE,
        TELEPHONE,
        FAX,
        NOTES,
        CREATEDDATE,
        LASTMODIFIEDDATE,
        CREATEDBY,
        LASTMODIFIEDBY,
        COORDX,
        COORDY,
        COORDZ,
        BUILDINGHEIGHT,
        SITEOWNER,
        BUILDINGTYPE,
        ANTENNAHEIGHT,
        ANTENNAHEIGHTSEALEVEL,
        PYLONTYPE,
        TRANSSITETYPE,
        ADRINTL,
        ADRCPLT,
        LIEUDIT,
        STATUSCHANGEDATE,
        STATUS,
        ZP_SITE_CODE
    }
}