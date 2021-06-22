package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class OdtAnt : OceanDataFile() {
    override val fileName = "OCEAN_ODT_ANT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.OTA_ID].toInt())
        stmt.setInt(++index, record[Header.ODT_ID].toInt())
        stmt.setInt(++index, record[Header.ANT_ID].toInt())
        stmt.setInt(++index, record[Header.OTA_FLAG].toInt())
    }

    enum class Header {
        OTA_ID,
        ODT_ID,
        ANT_ID,
        OTA_FLAG
    }
}