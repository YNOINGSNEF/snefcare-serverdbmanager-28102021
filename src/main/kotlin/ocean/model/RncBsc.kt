package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RncBsc : OceanDataFile() {
    override val fileName = "OCEAN_RNCBSC"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.RBC_ID].toInt())
        stmt.setInt(++index, record[Header.TEC_ID].toInt())
        stmt.setInt(++index, record[Header.REG_NUM].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setNullableInt(++index, record[Header.RBC_IDMET].toIntOrNull())
        stmt.setString(++index, record[Header.RBC_NOM])
        stmt.setInt(++index, record[Header.RBC_SITE].toInt())
        stmt.setNullableInt(++index, record[Header.RBC_PTSEM].toIntOrNull())
        stmt.setString(++index, record[Header.RBC_COMMENT])
        stmt.setString(++index, record[Header.RBC_QUI])
        stmt.setTimestamp(++index, record[Header.RBC_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.RBC_VERSION].toInt())
        stmt.setNullableInt(++index, record[Header.SOUSTYPE].toIntOrNull())
    }

    enum class Header {
        RBC_ID,
        TEC_ID,
        REG_NUM,
        CTR_ID,
        RBC_IDMET,
        RBC_NOM,
        RBC_SITE,
        RBC_PTSEM,
        RBC_COMMENT,
        RBC_QUI,
        RBC_QUAND,
        RBC_VERSION,
        SOUSTYPE
    }
}