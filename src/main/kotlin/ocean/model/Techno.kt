package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Techno : OceanDataFile() {
    override val fileName = "OCEAN_TECHNO"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.TEC_ID].toInt())
        stmt.setString(++index, record[Header.TEC_LIB])
    }

    enum class Header {
        TEC_ID,
        TEC_LIB
    }
}