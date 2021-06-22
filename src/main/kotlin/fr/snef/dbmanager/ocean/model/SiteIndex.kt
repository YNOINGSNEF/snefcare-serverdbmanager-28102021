package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class SiteIndex : OceanDataFile() {
    override val fileName = "OCEAN_SITE_INDEX"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setInt(++index, record[Header.ANT_INDEX].toInt())
        stmt.setInt(++index, record[Header.BAI_INDEX].toInt())
        stmt.setInt(++index, record[Header.REP_INDEX].toInt())
    }

    enum class Header {
        SIT_ID,
        ANT_INDEX,
        BAI_INDEX,
        REP_INDEX
    }
}