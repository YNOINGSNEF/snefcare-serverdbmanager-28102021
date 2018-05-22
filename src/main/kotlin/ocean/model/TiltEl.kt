package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class TiltEl : OceanDataFile() {
    override val fileName = "OCEAN_TILTEL"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.TLT_ID].toInt())
        stmt.setInt(++index, record[Header.CTA_ID].toInt())
        stmt.setInt(++index, record[Header.TLT_LIB].toInt())
        stmt.setBoolean(++index, record[Header.TLT_DEF].toBool())
        stmt.setInt(++index, record[Header.BAN_ID].toInt())
    }

    enum class Header {
        TLT_ID,
        CTA_ID,
        TLT_LIB,
        TLT_DEF,
        BAN_ID
    }
}