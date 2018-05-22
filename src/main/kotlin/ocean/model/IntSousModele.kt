package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class IntSousModele : OceanDataFile() {
    override val fileName = "OCEAN_INTSSMODELE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ISS_ID].toInt())
        stmt.setInt(++index, record[Header.INT_ID].toInt())
        stmt.setInt(++index, record[Header.JSS_ID].toInt())
        stmt.setInt(++index, record[Header.ISS_NUMINT].toInt())
        stmt.setNullableInt(++index, record[Header.ISS_NUMDI].toIntOrNull())
    }

    enum class Header {
        ISS_ID,
        INT_ID,
        JSS_ID,
        ISS_NUMINT,
        ISS_NUMDI
    }
}