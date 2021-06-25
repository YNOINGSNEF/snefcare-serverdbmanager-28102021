package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class FctAnt : OceanDataFile() {
    override val fileName = "OCEAN_FCTANT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.FCA_ID].toInt())
        stmt.setString(++index, record[Header.FCA_LIB])
        stmt.setBoolean(++index, record[Header.FCA_DEF].toBool())
        stmt.setBoolean(++index, record[Header.FCA_DEF_RS].toBool())
    }

    enum class Header {
        FCA_ID,
        FCA_LIB,
        FCA_DEF,
        FCA_DEF_RS
    }
}