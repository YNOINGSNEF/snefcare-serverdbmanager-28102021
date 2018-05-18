package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class BanSys : OceanDataFile() {
    override val fileName = "OCEAN_BAN_SYS"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.SYS_ID].toInt())
        stmt.setInt(++index, record[Header.BAN_ID].toInt())
    }

    enum class Header {
        SYS_ID,
        BAN_ID
    }
}