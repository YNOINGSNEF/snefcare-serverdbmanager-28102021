package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class EtatDm : OceanDataFile() {
    override val fileName = "OCEAN_ETATDM"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.EDM_ID].toInt())
        stmt.setString(++index, record[Header.EDM_CODE])
        stmt.setString(++index, record[Header.EDM_LIB])
        stmt.setBoolean(++index, record[Header.EDM_DEF].toBool())
    }

    enum class Header {
        EDM_ID,
        EDM_CODE,
        EDM_LIB,
        EDM_DEF
    }
}