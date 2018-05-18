package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ConfPuissance : OceanDataFile() {
    override val fileName = "OCEAN_CONFPUISSANCE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.PUI_ID].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setString(++index, record[Header.PUI_LIB])
        stmt.setBoolean(++index, record[Header.PUI_DEF].toBool())
    }

    enum class Header {
        PUI_ID,
        CTR_ID,
        PUI_LIB,
        PUI_DEF
    }
}