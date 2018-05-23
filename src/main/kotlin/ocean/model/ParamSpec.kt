package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ParamSpec : OceanDataFile() {
    override val fileName = "OCEAN_PARAMSPEC"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.PSC_ID].toInt())
        stmt.setInt(++index, record[Header.JPA_ID].toInt())
        stmt.setInt(++index, record[Header.PAR_ID].toInt())
        stmt.setInt(++index, record[Header.JUS_ID].toInt())
        stmt.setString(++index, record[Header.PSC_VALSPEC])
        stmt.setNullableString(++index, record[Header.PSC_NUMDI].takeIf { it.isNotBlank() })
        stmt.setString(++index, record[Header.PSC_QUI])
        stmt.setTimestamp(++index, record[Header.PSC_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.PSC_VERSION].toInt())
        stmt.setString(++index, record[Header.PSC_COMMENT])
    }

    enum class Header {
        PSC_ID,
        JPA_ID,
        PAR_ID,
        JUS_ID,
        PSC_VALSPEC,
        PSC_NUMDI,
        PSC_QUI,
        PSC_QUAND,
        PSC_VERSION,
        PSC_COMMENT
    }
}