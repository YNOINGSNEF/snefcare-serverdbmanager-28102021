package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class OdtBai : OceanDataFile() {
    override val fileName = "OCEAN_ODT_BAI"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.OTB_ID].toInt())
        stmt.setInt(++index, record[Header.BAI_ID].toInt())
        stmt.setInt(++index, record[Header.ODT_ID].toInt())
        stmt.setInt(++index, record[Header.OTB_FLAG].toInt())
    }

    enum class Header {
        OTB_ID,
        BAI_ID,
        ODT_ID,
        OTB_FLAG
    }
}