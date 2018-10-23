package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ModRadio : OceanDataFile() {
    override val fileName = "OCEAN_MODRADIO"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.MRAD_ID].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setString(++index, record[Header.MRAD_REF])
        stmt.setBoolean(++index, record[Header.MRAD_DEF].toBool())
        stmt.setInt(++index, record[Header.SYS_ID].toInt())
    }

    enum class Header {
        MRAD_ID,
        CTR_ID,
        MRAD_REF,
        MRAD_DEF,
        SYS_ID
    }
}