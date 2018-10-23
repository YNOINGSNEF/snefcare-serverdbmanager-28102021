package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Ot : OceanDataFile() {
    override val fileName = "OCEAN_OT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ODT_ID].toInt())
        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setString(++index, record[Header.ODT_NOM])
        stmt.setNullableInt(++index, record[Header.ODT_NUM].toIntOrNull())
        stmt.setString(++index, record[Header.ODT_COMMENT])
        stmt.setString(++index, record[Header.ODT_QUI])
        stmt.setTimestamp(++index, record[Header.ODT_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.ODT_VERSION].toInt())
    }

    enum class Header {
        ODT_ID,
        SIT_ID,
        ODT_NOM,
        ODT_NUM,
        ODT_COMMENT,
        ODT_QUI,
        ODT_QUAND,
        ODT_VERSION
    }
}