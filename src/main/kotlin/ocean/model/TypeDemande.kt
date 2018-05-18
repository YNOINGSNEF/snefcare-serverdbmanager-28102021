package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class TypeDemande : OceanDataFile() {
    override val fileName = "OCEAN_TYPEDEMANDE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.TDM_ID].toInt())
        stmt.setString(++index, record[Header.TDM_LIB])
        stmt.setBoolean(++index, record[Header.TDM_DEF].toBool())
    }

    enum class Header {
        TDM_ID,
        TDM_LIB,
        TDM_DEF
    }
}