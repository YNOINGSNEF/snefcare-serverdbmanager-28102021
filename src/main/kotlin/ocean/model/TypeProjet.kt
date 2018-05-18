package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class TypeProjet : OceanDataFile() {
    override val fileName = "OCEAN_TYPEPROJET"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.TYPEPROJET_ID].toInt())
        stmt.setString(++index, record[Header.TYPEPROJET_LIB])
    }

    enum class Header {
        TYPEPROJET_ID,
        TYPEPROJET_LIB
    }
}