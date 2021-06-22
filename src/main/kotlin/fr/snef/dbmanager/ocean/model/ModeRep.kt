package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ModeRep : OceanDataFile() {
    override val fileName = "OCEAN_MODEREP"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.MDR_ID].toInt())
        stmt.setNullableInt(++index, record[Header.MDR_SOURCEID].toIntOrNull())
        stmt.setInt(++index, record[Header.REP_ID].toInt())
        stmt.setInt(++index, record[Header.SYS_ID].toInt())
        stmt.setNullableInt(++index, record[Header.MDR_POWER].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.MDR_ATTUP].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.MDR_ATTDOWN].toIntOrNull())
        stmt.setNullableFloat(++index, record[Header.MDR_GAIN].replace(',', '.').toFloatOrNull())
        stmt.setInt(++index, record[Header.MDR_NONINST].toInt())
        stmt.setString(++index, record[Header.MDR_COMMENT])
        stmt.setString(++index, record[Header.MDR_QUI])
        stmt.setTimestamp(++index, record[Header.MDR_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.MDR_VERSION].toInt())
    }

    enum class Header {
        MDR_ID,
        MDR_SOURCEID,
        REP_ID,
        SYS_ID,
        MDR_POWER,
        MDR_ATTUP,
        MDR_ATTDOWN,
        MDR_GAIN,
        MDR_NONINST,
        MDR_COMMENT,
        MDR_QUI,
        MDR_QUAND,
        MDR_VERSION
    }
}