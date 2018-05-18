package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ParamCi : OceanDataFile() {
    override val fileName = "OCEAN_PARAMCI"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setString(++index, record[Header.PCI_NOM])
        stmt.setInt(++index, record[Header.PCI_VAL].toInt())
        stmt.setInt(++index, record[Header.PCI_TYPE].toInt())
    }

    enum class Header {
        PCI_NOM,
        PCI_VAL,
        PCI_TYPE
    }
}