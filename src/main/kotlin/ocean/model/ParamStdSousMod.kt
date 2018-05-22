package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ParamStdSousMod : OceanDataFile() {
    override val fileName = "OCEAN_PARAMSTDSSMOD"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.PSS_ID].toInt())
        stmt.setInt(++index, record[Header.PAR_ID].toInt())
        stmt.setInt(++index, record[Header.JSS_ID].toInt())
        stmt.setString(++index, record[Header.PSS_VALSTD])
        stmt.setNullableString(++index, record[Header.PSS_NUMDI].takeIf { it.isNotBlank() })
    }

    enum class Header {
        PSS_ID,
        PAR_ID,
        JSS_ID,
        PSS_VALSTD,
        PSS_NUMDI
    }
}