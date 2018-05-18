package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class EtatSw : OceanDataFile() {
    override val fileName = "OCEAN_ETATSW"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ESW_ID].toInt())
        stmt.setString(++index, record[Header.ESW_CODE])
        stmt.setString(++index, record[Header.ESW_LIB])
        stmt.setString(++index, record[Header.ESW_CODECELL])
        stmt.setString(++index, record[Header.ESW_LIBCELL])
        stmt.setBoolean(++index, record[Header.ESW_DEF].toBool())
    }

    enum class Header {
        ESW_ID,
        ESW_CODE,
        ESW_LIB,
        ESW_CODECELL,
        ESW_LIBCELL,
        ESW_DEF
    }
}