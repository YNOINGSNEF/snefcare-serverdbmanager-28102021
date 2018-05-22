package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Nature : OceanDataFile() {
    override val fileName = "OCEAN_NATURE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.NAT_ID].toInt())
        stmt.setInt(++index, record[Header.CTB_ID].toInt())
        stmt.setString(++index, record[Header.NAT_NOM])
        stmt.setBoolean(++index, record[Header.NAT_DEF].toBool())
    }

    enum class Header {
        NAT_ID,
        CTB_ID,
        NAT_NOM,
        NAT_DEF
    }
}