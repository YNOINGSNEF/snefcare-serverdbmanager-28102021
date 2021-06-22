package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class TypCell : OceanDataFile() {
    override val fileName = "OCEAN_TYPCELL"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.TPC_ID].toInt())
        stmt.setString(++index, record[Header.TPC_LIB])
        stmt.setBoolean(++index, record[Header.TPC_DEF].toBool())
        stmt.setBoolean(++index, record[Header.TPC_DEF_RS].toBool())
    }

    enum class Header {
        TPC_ID,
        TPC_LIB,
        TPC_DEF,
        TPC_DEF_RS
    }
}