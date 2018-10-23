package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Combiner : OceanDataFile() {
    override val fileName = "OCEAN_COMBINER"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.COM_ID].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setString(++index, record[Header.COM_LIB])
        stmt.setBoolean(++index, record[Header.COM_DEF].toBool())
    }

    enum class Header {
        COM_ID,
        CTR_ID,
        COM_LIB,
        COM_DEF
    }
}