package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Environmnt : OceanDataFile() {
    override val fileName = "OCEAN_ENVIRONMNT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ENV_ID].toInt())
        stmt.setString(++index, record[Header.ENV_LIB])
        stmt.setBoolean(++index, record[Header.ENV_DEF].toBool())
    }

    enum class Header {
        ENV_ID,
        ENV_LIB,
        ENV_DEF
    }
}