package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ZoneVie : OceanDataFile() {
    override val fileName = "OCEAN_ZONEVIE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ZVI_ID].toInt())
        stmt.setString(++index, record[Header.ZVI_LIB])
        stmt.setBoolean(++index, record[Header.ZVI_DEF].toBool())
    }

    enum class Header {
        ZVI_ID,
        ZVI_LIB,
        ZVI_DEF
    }
}