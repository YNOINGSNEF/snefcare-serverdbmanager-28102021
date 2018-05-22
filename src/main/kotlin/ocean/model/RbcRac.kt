package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RbcRac : OceanDataFile() {
    override val fileName = "OCEAN_RBC_RAC"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.RAC_ID].toInt())
        stmt.setInt(++index, record[Header.RAC_VALUE].toInt())
        stmt.setInt(++index, record[Header.RBC_ID].toInt())
    }

    enum class Header {
        RAC_ID,
        RAC_VALUE,
        RBC_ID
    }
}