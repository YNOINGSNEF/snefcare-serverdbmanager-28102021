package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RacBaie : OceanDataFile() {
    override val fileName = "OCEAN_RAC_BAIE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.RAC_BAIE_ID].toInt())
        stmt.setInt(++index, record[Header.RAC_ID].toInt())
        stmt.setInt(++index, record[Header.RBB_ID].toInt())
        stmt.setTimestamp(++index, record[Header.RAC_BAIE_QUAND].toTimestamp())
    }

    enum class Header {
        RAC_BAIE_ID,
        RAC_ID,
        RBB_ID,
        RAC_BAIE_QUAND
    }
}