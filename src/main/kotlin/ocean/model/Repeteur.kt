package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Repeteur : OceanDataFile() {
    override val fileName = "OCEAN_REPETEUR"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.REP_ID].toInt())
        stmt.setNullableInt(++index, record[Header.REP_SOURCEID].toIntOrNull())
        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setInt(++index, record[Header.CRE_ID].toInt())
        stmt.setInt(++index, record[Header.REP_NUM].toInt())
        stmt.setInt(++index, record[Header.REP_INDEX].toInt())
        stmt.setNullableString(++index, record[Header.REP_MEMO].takeIf { it.isNotBlank() })
        stmt.setFloat(++index, record[Header.REP_COOX].replace(',', '.').toFloat())
        stmt.setFloat(++index, record[Header.REP_COOY].replace(',', '.').toFloat())
        stmt.setInt(++index, record[Header.REP_COOZ].toInt())
        stmt.setBoolean(++index, record[Header.REP_SUPER].toBool())
        stmt.setNullableString(++index, record[Header.REP_MODEM].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.REP_MASTER].takeIf { it.isNotBlank() })
        stmt.setInt(++index, record[Header.REP_NONINST].toInt())
        stmt.setString(++index, record[Header.REP_COMMENT])
        stmt.setString(++index, record[Header.REP_QUI])
        stmt.setTimestamp(++index, record[Header.REP_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.REP_VERSION].toInt())
    }

    enum class Header {
        REP_ID,
        REP_SOURCEID,
        SIT_ID,
        CRE_ID,
        REP_NUM,
        REP_INDEX,
        REP_MEMO,
        REP_COOX,
        REP_COOY,
        REP_COOZ,
        REP_SUPER,
        REP_MODEM,
        REP_MASTER,
        REP_NONINST,
        REP_COMMENT,
        REP_QUI,
        REP_QUAND,
        REP_VERSION
    }
}