package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class LacBaie : OceanDataFile() {
    override val fileName = "OCEAN_LAC_BAIE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.LAC_BAIE_ID].toInt())
        stmt.setInt(++index, record[Header.LAC_ID].toInt())
        stmt.setInt(++index, record[Header.RBB_ID].toInt())
        stmt.setTimestamp(++index, record[Header.LAC_BAIE_QUAND].toTimestamp())
    }

    enum class Header {
        LAC_BAIE_ID,
        LAC_ID,
        RBB_ID,
        LAC_BAIE_QUAND
    }
}