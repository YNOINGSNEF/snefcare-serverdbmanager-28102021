package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Frequence2g : OceanDataFile() {
    override val fileName = "OCEAN_FREQUENCE2G"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.F2G_ID].toInt())
        stmt.setNullableInt(++index, record[Header.PLN_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.CELL_ID].toInt())
        stmt.setInt(++index, record[Header.ESW_ID].toInt())
        stmt.setInt(++index, record[Header.SFQ_ID].toInt())
        stmt.setInt(++index, record[Header.F2G_NCC].toInt())
        stmt.setInt(++index, record[Header.F2G_BCC].toInt())
        stmt.setNullableInt(++index, record[Header.F2G_HSN].toIntOrNull())
        stmt.setInt(++index, record[Header.F2G_RANG].toInt())
        stmt.setInt(++index, record[Header.F2G_CANAL].toInt())
        stmt.setBoolean(++index, record[Header.F2G_FLGSUP].toBool())
        stmt.setNullableInt(++index, record[Header.F2G_NUMDI].toIntOrNull())
        stmt.setString(++index, record[Header.F2G_QUI])
        stmt.setTimestamp(++index, record[Header.F2G_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.F2G_VERSION].toInt())
        stmt.setNullableInt(++index, record[Header.F2G_MAIO].toIntOrNull())
    }

    enum class Header {
        F2G_ID,
        PLN_ID,
        CELL_ID,
        ESW_ID,
        SFQ_ID,
        F2G_NCC,
        F2G_BCC,
        F2G_HSN,
        F2G_RANG,
        F2G_CANAL,
        F2G_FLGSUP,
        F2G_NUMDI,
        F2G_QUI,
        F2G_QUAND,
        F2G_VERSION,
        F2G_MAIO
    }
}