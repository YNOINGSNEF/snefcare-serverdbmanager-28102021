package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CatalImpExp : OceanDataFile() {
    override val fileName = "OCEAN_CATALIMPEXP"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CIE_ID].toInt())
        stmt.setString(++index, record[Header.CIE_NOM])
        stmt.setString(++index, record[Header.CIE_DESCRIPTION])
        stmt.setString(++index, record[Header.CIE_PACKAGE])
        stmt.setInt(++index, record[Header.CIE_TYPE].toInt())
        stmt.setInt(++index, record[Header.CIE_VALID].toInt())
    }

    enum class Header {
        CIE_ID,
        CIE_NOM,
        CIE_DESCRIPTION,
        CIE_PACKAGE,
        CIE_TYPE,
        CIE_VALID
    }
}