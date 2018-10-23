package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Voisinage : OceanDataFile() {
    override val fileName = "OCEAN_VOISINAGE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.VOG_ID].toInt())
        stmt.setInt(++index, record[Header.CELL_IDCIBLE].toInt())
        stmt.setInt(++index, record[Header.CELL_IDSOURCE].toInt())
        stmt.setInt(++index, record[Header.ESW_ID].toInt())
        stmt.setString(++index, record[Header.VOG_COMMENT])
        stmt.setBoolean(++index, record[Header.VOG_FLGSUP].toBool())
        stmt.setNullableInt(++index, record[Header.VOG_NUMDI].toIntOrNull())
        stmt.setString(++index, record[Header.VOG_QUI])
        stmt.setTimestamp(++index, record[Header.VOG_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.VOG_VERSION].toInt())
        stmt.setInt(++index, record[Header.MODE_ID].toInt())
        stmt.setBoolean(++index, record[Header.FANR].toBool())
    }

    enum class Header {
        VOG_ID,
        CELL_IDCIBLE,
        CELL_IDSOURCE,
        ESW_ID,
        VOG_COMMENT,
        VOG_FLGSUP,
        VOG_NUMDI,
        VOG_QUI,
        VOG_QUAND,
        VOG_VERSION,
        MODE_ID,
        FANR
    }
}