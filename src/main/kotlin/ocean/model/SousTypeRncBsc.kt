package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class SousTypeRncBsc : OceanDataFile() {
    override val fileName = "OCEAN_SOUSTYPE_RNCBSC"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.SOUSTYPE_ID].toInt())
        stmt.setString(++index, record[Header.SOUSTYPE_LIB])
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setInt(++index, record[Header.TEC_ID].toInt())
    }

    enum class Header {
        SOUSTYPE_ID,
        SOUSTYPE_LIB,
        CTR_ID,
        TEC_ID
    }
}