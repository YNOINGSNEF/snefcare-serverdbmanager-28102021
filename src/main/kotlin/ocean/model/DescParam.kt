package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class DescParam : OceanDataFile() {
    override val fileName = "OCEAN_DESCPARAM"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.DSC_ID].toInt())
        stmt.setString(++index, record[Header.DSC_LIB])
        stmt.setBoolean(++index, record[Header.DSC_DEF].toBool())
    }

    enum class Header {
        DSC_ID,
        DSC_LIB,
        DSC_DEF
    }
}