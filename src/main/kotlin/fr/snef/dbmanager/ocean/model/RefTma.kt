package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RefTma : OceanDataFile() {
    override val fileName = "OCEAN_REFTMA"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.RFT_ID].toInt())
        stmt.setString(++index, record[Header.RFT_LIB])
        stmt.setBoolean(++index, record[Header.RFT_DEF].toBool())
        stmt.setBoolean(++index, record[Header.RFT_DEF_RS].toBool())
    }

    enum class Header {
        RFT_ID,
        RFT_LIB,
        RFT_DEF,
        RFT_DEF_RS
    }
}