package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class PorteeParam : OceanDataFile() {
    override val fileName = "OCEAN_PORTEEPARAM"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.PPA_ID].toInt())
        stmt.setString(++index, record[Header.PPA_LIB])
        stmt.setString(++index, record[Header.PPA_TYPOBJ])
        stmt.setString(++index, record[Header.PPA_CAT])
    }

    enum class Header {
        PPA_ID,
        PPA_LIB,
        PPA_TYPOBJ,
        PPA_CAT
    }
}