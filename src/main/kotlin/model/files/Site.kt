package model.files

import model.DataFile
import model.Region
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.sql.Types

class Site : DataFile() {
    override val fileName = "Site"
    override val fileHeader = Header::class.java

    override val tableName = "SITE"
    override val tableHeader = listOf("region_code", "num_g2r", "name", "x_coord", "y_coord", "z_coord")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
        val siteName = record[Header.SITE].extractSiteName()
        if (siteName != null) {
            val numG2r = record[Header.ALIAS_SITE]
            val xCoord = record[Header.COORDX].replace(',', '.').toDoubleOrNull()
            val yCoord = record[Header.COORDY].replace(',', '.').toDoubleOrNull()
            val zCoord = record[Header.COORDZ].replace(',', '.').toDoubleOrNull()

            stmt.setString(1, region.name)
            stmt.setString(2, numG2r)
            stmt.setString(3, siteName)

            if (xCoord != null) {
                stmt.setDouble(4, xCoord)
            } else {
                stmt.setNull(4, Types.DOUBLE)
            }

            if (yCoord != null) {
                stmt.setDouble(5, yCoord)
            } else {
                stmt.setNull(5, Types.DOUBLE)
            }

            if (zCoord != null) {
                stmt.setDouble(6, zCoord)
            } else {
                stmt.setNull(6, Types.DOUBLE)
            }

            stmt.addBatch()
            return true
        }

        return false
    }

    private fun String.extractSiteName() = "\\d*\\s*-\\s*(.*)".toRegex().matchEntire(this)?.groups?.get(1)?.value

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