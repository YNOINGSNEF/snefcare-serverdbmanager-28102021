package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class OdtCfb : OceanDataFile() {
    override val fileName = "OCEAN_ODT_CFB"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.OTC_ID].toInt())
        stmt.setInt(++index, record[Header.CFB_ID].toInt())
        stmt.setInt(++index, record[Header.ODT_ID].toInt())
        stmt.setInt(++index, record[Header.OTC_FLAG].toInt())
    }

    enum class Header {
        OTC_ID,
        CFB_ID,
        ODT_ID,
        OTC_FLAG
    }
}