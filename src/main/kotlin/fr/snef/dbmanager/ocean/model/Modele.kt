package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Modele : OceanDataFile() {
    override val fileName = "OCEAN_MODELE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.MOD_ID].toInt())
        stmt.setInt(++index, record[Header.PPA_ID].toInt())
        stmt.setInt(++index, record[Header.PAL_ID].toInt())
        stmt.setInt(++index, record[Header.REG_NUM].toInt())
        stmt.setString(++index, record[Header.MOD_LIB])
        stmt.setNullableInt(++index, record[Header.MOD_IDOMC].toIntOrNull())
        stmt.setBoolean(++index, record[Header.MOD_FLGDEF].toBool())
        stmt.setString(++index, record[Header.MOD_QUI])
        stmt.setTimestamp(++index, record[Header.MOD_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.MOD_VERSION].toInt())
        stmt.setNullableInt(++index, record[Header.MOD_EXTRA].toIntOrNull())
    }

    enum class Header {
        MOD_ID,
        PPA_ID,
        PAL_ID,
        REG_NUM,
        MOD_LIB,
        MOD_IDOMC,
        MOD_FLGDEF,
        MOD_QUI,
        MOD_QUAND,
        MOD_VERSION,
        MOD_EXTRA
    }
}