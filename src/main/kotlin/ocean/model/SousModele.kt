package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class SousModele : OceanDataFile() {
    override val fileName = "OCEAN_SOUSMODELE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.SMO_ID].toInt())
        stmt.setInt(++index, record[Header.MOD_ID].toInt())
        stmt.setInt(++index, record[Header.SMO_RANG].toInt())
        stmt.setString(++index, record[Header.SMO_QUI])
        stmt.setTimestamp(++index, record[Header.SMO_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.SMO_VERSION].toInt())
    }

    enum class Header {
        SMO_ID,
        MOD_ID,
        SMO_RANG,
        SMO_QUI,
        SMO_QUAND,
        SMO_VERSION
    }
}