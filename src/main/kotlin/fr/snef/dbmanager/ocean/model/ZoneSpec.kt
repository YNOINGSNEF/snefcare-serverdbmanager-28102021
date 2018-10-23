package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ZoneSpec : OceanDataFile() {
    override val fileName = "OCEAN_ZONESPEC"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ZSP_ID].toInt())
        stmt.setInt(++index, record[Header.REG_NUM].toInt())
        stmt.setString(++index, record[Header.ZSP_LIB])
        stmt.setString(++index, record[Header.ZSP_CODE])
        stmt.setBoolean(++index, record[Header.ZSP_DEF].toBool())
    }

    enum class Header {
        ZSP_ID,
        REG_NUM,
        ZSP_LIB,
        ZSP_CODE,
        ZSP_DEF
    }
}