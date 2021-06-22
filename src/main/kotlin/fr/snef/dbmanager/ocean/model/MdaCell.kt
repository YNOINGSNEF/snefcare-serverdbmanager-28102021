package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class MdaCell : OceanDataFile() {
    override val fileName = "OCEAN_MDA_CELL"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.MCE_ID].toInt())
        stmt.setInt(++index, record[Header.CELL_ID].toInt())
        stmt.setInt(++index, record[Header.MDA_ID].toInt())
        stmt.setTimestamp(++index, record[Header.MCE_QUAND].toTimestamp())
    }

    enum class Header {
        MCE_ID,
        CELL_ID,
        MDA_ID,
        MCE_QUAND
    }
}