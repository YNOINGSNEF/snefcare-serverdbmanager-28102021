package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Bande : OceanDataFile() {
    override val fileName = "OCEAN_BANDE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.BAN_ID].toInt())
        stmt.setString(++index, record[Header.BAN_LIB])
    }

    enum class Header {
        BAN_ID,
        BAN_LIB
    }
}