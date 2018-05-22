package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Palier : OceanDataFile() {
    override val fileName = "OCEAN_PALIER"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.PAL_ID].toInt())
        stmt.setInt(++index, record[Header.TEC_ID].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setString(++index, record[Header.PAL_LIB])
        stmt.setBoolean(++index, record[Header.PAL_DEF].toBool())
    }

    enum class Header {
        PAL_ID,
        TEC_ID,
        CTR_ID,
        PAL_LIB,
        PAL_DEF
    }
}