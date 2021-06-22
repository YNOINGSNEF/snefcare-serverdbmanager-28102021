package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RefRet : OceanDataFile() {
    override val fileName = "OCEAN_REFRET"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.RET_ID].toInt())
        stmt.setString(++index, record[Header.RET_LIB])
        stmt.setBoolean(++index, record[Header.RET_DEF].toBool())
    }

    enum class Header {
        RET_ID,
        RET_LIB,
        RET_DEF
    }
}