package rrcap.model

import org.apache.commons.csv.CSVRecord
import rrcap.Region
import rrcap.RrcapDatafile
import java.sql.PreparedStatement

class S1Bearer(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "S1Bearer"
    override val fileHeader = Header::class.java

    override val tableName = "S1BEARER"
    override val tableHeader = listOf("region_code", "s1_name", "site_g2r", "eNodeB")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.S1_NAME])
            stmt.setString(++index, record[Header.SITE_ENODEB].extractSiteG2R() ?: throw TypeCastException("Invalid G2R"))
            stmt.setString(++index, record[Header.ENODEB_NAME])
            stmt.addBatch()
            true
        } catch (ex: TypeCastException) {
            stmt.clearParameters()
            false
        }
    }

    enum class Header {
        REGION,
        S1_NAME,
        SITE_MME,
        MME_NAME,
        MME_PORT,
        SITE_ENODEB,
        ENODEB_NAME,
        ENODEB_PORT,
        ENODEB_ALIAS_4G,
        SYSTEM,
        CREATED_DATE,
        CREATED_BY,
        LAST_MODIFIED_DATE,
        LAST_MODIFIED_BY,
        PROVISION_STATUS,
        RESOLUTION_STATUS,
        BANDWIDTH
    }
}