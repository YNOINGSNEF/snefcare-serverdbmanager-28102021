package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ZoneReseaux : OceanDataFile() {
    override val fileName = "OCEAN_ZONERESEAUX"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ZRE_ID].toInt())
        stmt.setString(++index, record[Header.ZRE_LIB])
        stmt.setBoolean(++index, record[Header.ZRE_DEF].toBool())
    }

    enum class Header {
        ZRE_ID,
        ZRE_LIB,
        ZRE_DEF
    }
}