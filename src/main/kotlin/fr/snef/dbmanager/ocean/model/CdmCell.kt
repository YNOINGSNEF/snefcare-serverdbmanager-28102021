package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CdmCell : OceanDataFile() {
    override val fileName = "OCEAN_CDM_CELL"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ACD_ID].toInt())
        stmt.setInt(++index, record[Header.CDM_ID].toInt())
        stmt.setInt(++index, record[Header.CELL_ID].toInt())
        stmt.setFloat(++index, record[Header.ACD_NOUVVAL].replace(',', '.').toFloat())
        stmt.setInt(++index, record[Header.ACD_VERSION].toInt())
        stmt.setString(++index, record[Header.ACD_QUI])
        stmt.setTimestamp(++index, record[Header.ACD_QUAND].toTimestamp())
    }

    enum class Header {
        ACD_ID,
        CDM_ID,
        CELL_ID,
        ACD_NOUVVAL,
        ACD_VERSION,
        ACD_QUI,
        ACD_QUAND
    }
}