package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CatalBaie : OceanDataFile() {
    override val fileName = "OCEAN_CATALBAIE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CTB_ID].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setInt(++index, record[Header.BAN_ID].toInt())
        stmt.setString(++index, record[Header.CTB_REF])
        stmt.setString(++index, record[Header.CTB_POWER])
        stmt.setBoolean(++index, record[Header.CTB_DEF].toBool())
        stmt.setInt(++index, record[Header.RS_FLAG].toInt())
        stmt.setNullableInt(++index, record[Header.MONO].toIntOrNull())
    }

    enum class Header {
        CTB_ID,
        CTR_ID,
        BAN_ID,
        CTB_REF,
        CTB_POWER,
        CTB_DEF,
        RS_FLAG,
        MONO
    }
}