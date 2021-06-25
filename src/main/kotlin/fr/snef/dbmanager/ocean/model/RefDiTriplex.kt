package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RefDiTriplex : OceanDataFile() {
    override val fileName = "OCEAN_REFDITRIPLEX"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.RFD_ID].toInt())
        stmt.setString(++index, record[Header.RFD_LIB])
        stmt.setBoolean(++index, record[Header.RFD_DEF].toBool())
        stmt.setBoolean(++index, record[Header.RFD_DEF_RS].toBool())
    }

    enum class Header {
        RFD_ID,
        RFD_LIB,
        RFD_DEF,
        RFD_DEF_RS
    }
}