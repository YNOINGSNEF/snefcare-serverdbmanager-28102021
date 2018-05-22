package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Parametre : OceanDataFile() {
    override val fileName = "OCEAN_PARAMETRE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.PAR_ID].toInt())
        stmt.setInt(++index, record[Header.PAL_ID].toInt())
        stmt.setInt(++index, record[Header.TPA_ID].toInt())
        stmt.setInt(++index, record[Header.GPR_ID].toInt())
        stmt.setNullableInt(++index, record[Header.PNA_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.PPA_ID].toInt())
        stmt.setNullableInt(++index, record[Header.POP_ID].toIntOrNull())
        stmt.setString(++index, record[Header.PAR_LIB])
        stmt.setNullableInt(++index, record[Header.PAR_LONG].toIntOrNull())
        stmt.setString(++index, record[Header.PAR_VALDEF])
        stmt.setNullableString(++index, record[Header.PAR_MIN].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PAR_MAX].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PAR_PAS].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PAR_UNITE].takeIf { it.isNotBlank() })
        stmt.setString(++index, record[Header.PAR_QUI])
        stmt.setTimestamp(++index, record[Header.PAR_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.PAR_VERSION].toInt())
        stmt.setNullableString(++index, record[Header.PAR_EXTRA].takeIf { it.isNotBlank() })
        stmt.setBoolean(++index, record[Header.CCLB].toBool())
        stmt.setNullableString(++index, record[Header.PAR_LIB_OMC].takeIf { it.isNotBlank() })
    }

    enum class Header {
        PAR_ID,
        PAL_ID,
        TPA_ID,
        GPR_ID,
        PNA_ID,
        PPA_ID,
        POP_ID,
        PAR_LIB,
        PAR_LONG,
        PAR_VALDEF,
        PAR_MIN,
        PAR_MAX,
        PAR_PAS,
        PAR_UNITE,
        PAR_QUI,
        PAR_QUAND,
        PAR_VERSION,
        PAR_EXTRA,
        CCLB,
        PAR_LIB_OMC
    }
}