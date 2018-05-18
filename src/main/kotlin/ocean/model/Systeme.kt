package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Systeme : OceanDataFile() {
    override val fileName = "OCEAN_SYSTEME"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.SYS_ID].toInt())
        stmt.setInt(++index, record[Header.TEC_ID].toInt())
        stmt.setString(++index, record[Header.SYS_LIB])
        stmt.setString(++index, record[Header.SYS_DESC])
        stmt.setString(++index, record[Header.SYS_CODE])
    }

    enum class Header {
        SYS_ID,
        TEC_ID,
        SYS_LIB,
        SYS_DESC,
        SYS_CODE
    }
}