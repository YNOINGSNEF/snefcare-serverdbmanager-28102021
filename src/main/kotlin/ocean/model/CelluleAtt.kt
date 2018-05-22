package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CelluleAtt : OceanDataFile() {
    override val fileName = "OCEAN_CELLULE_ATT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CATT_ID].toInt())
        stmt.setInt(++index, record[Header.CELL_ID].toInt())
        stmt.setInt(++index, record[Header.ATT_ID].toInt())
        stmt.setNullableString(++index, record[Header.ATT_VALUE].takeIf { it.isNotBlank() })
    }

    enum class Header {
        CATT_ID,
        CELL_ID,
        ATT_ID,
        ATT_VALUE
    }
}