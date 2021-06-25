package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ZoneService : OceanDataFile() {
    override val fileName = "OCEAN_ZONESERVICE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ZSE_ID].toInt())
        stmt.setString(++index, record[Header.ZSE_LIB])
        stmt.setBoolean(++index, record[Header.ZSE_DEF].toBool())
    }

    enum class Header {
        ZSE_ID,
        ZSE_LIB,
        ZSE_DEF
    }
}