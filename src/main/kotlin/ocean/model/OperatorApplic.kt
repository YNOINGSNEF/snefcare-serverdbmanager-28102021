package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class OperatorApplic : OceanDataFile() {
    override val fileName = "OCEAN_OPERATORAPPLIC"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.OPP_ID].toInt())
        stmt.setString(++index, record[Header.OPP_LIB])
        stmt.setBoolean(++index, record[Header.OPP_DEF].toBool())
        stmt.setInt(++index, record[Header.ZP_LEADER].toInt())
        stmt.setString(++index, record[Header.OPP_CODE])
    }

    enum class Header {
        OPP_ID,
        OPP_LIB,
        OPP_DEF,
        ZP_LEADER,
        OPP_CODE
    }
}