package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ModBbu : OceanDataFile() {
    override val fileName = "OCEAN_MODBBU"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.BBU_ID].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setString(++index, record[Header.BBU_REF])
        stmt.setBoolean(++index, record[Header.BBU_DEF].toBool())
        stmt.setInt(++index, record[Header.TEC_ID].toInt())
        stmt.setBoolean(++index, record[Header.BBU_DEF_RS].toBool())
        stmt.setString(++index, record[Header.TRANS])
    }

    enum class Header {
        BBU_ID,
        CTR_ID,
        BBU_REF,
        BBU_DEF,
        TEC_ID,
        BBU_DEF_RS,
        TRANS
    }
}