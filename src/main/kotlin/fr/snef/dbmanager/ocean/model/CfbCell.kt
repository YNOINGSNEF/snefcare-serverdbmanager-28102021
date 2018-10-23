package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CfbCell : OceanDataFile() {
    override val fileName = "OCEAN_CFB_CELL"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CCE_ID].toInt())
        stmt.setInt(++index, record[Header.CELL_ID].toInt())
        stmt.setInt(++index, record[Header.CFB_ID].toInt())
        stmt.setTimestamp(++index, record[Header.CCE_QUAND].toTimestamp())
    }

    enum class Header {
        CCE_ID,
        CELL_ID,
        CFB_ID,
        CCE_QUAND
    }
}