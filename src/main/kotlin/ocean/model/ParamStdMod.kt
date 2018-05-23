package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ParamStdMod : OceanDataFile() {
    override val fileName = "OCEAN_PARAMSTDMOD"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.PSM_ID].toInt())
        stmt.setInt(++index, record[Header.MOD_ID].toInt())
        stmt.setInt(++index, record[Header.PAR_ID].toInt())
        stmt.setString(++index, record[Header.PSM_VALSTD])
        stmt.setNullableString(++index, record[Header.PSM_NUMDI].takeIf { it.isNotBlank() })
        stmt.setBoolean(++index, record[Header.PSM_FLGPRINC].toBool())
    }

    enum class Header {
        PSM_ID,
        MOD_ID,
        PAR_ID,
        PSM_VALSTD,
        PSM_NUMDI,
        PSM_FLGPRINC
    }
}