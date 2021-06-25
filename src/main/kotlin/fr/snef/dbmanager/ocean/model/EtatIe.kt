package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class EtatIe : OceanDataFile() {
    override val fileName = "OCEAN_ETATIE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.EIE_ID].toInt())
        stmt.setString(++index, record[Header.EIE_LIB])
        stmt.setString(++index, record[Header.EIE_CODE])
    }

    enum class Header {
        EIE_ID,
        EIE_LIB,
        EIE_CODE
    }
}