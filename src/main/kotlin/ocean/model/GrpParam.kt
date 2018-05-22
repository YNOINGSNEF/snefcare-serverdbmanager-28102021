package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class GrpParam : OceanDataFile() {
    override val fileName = "OCEAN_GRPPARAM"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.GPR_ID].toInt())
        stmt.setString(++index, record[Header.GPR_LIB])
        stmt.setBoolean(++index, record[Header.GPR_DEF].toBool())
    }

    enum class Header {
        GPR_ID,
        GPR_LIB,
        GPR_DEF
    }
}