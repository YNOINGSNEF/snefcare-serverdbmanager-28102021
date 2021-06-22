package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class SautFrq : OceanDataFile() {
    override val fileName = "OCEAN_SAUTFRQ"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.SFQ_ID].toInt())
        stmt.setString(++index, record[Header.SFQ_LIB])
        stmt.setBoolean(++index, record[Header.SFQ_DEF].toBool())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
    }

    enum class Header {
        SFQ_ID,
        SFQ_LIB,
        SFQ_DEF,
        CTR_ID
    }
}