package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RegDep : OceanDataFile() {
    override val fileName = "OCEAN_REG_DEP"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.REG_NUM].toInt())
        stmt.setInt(++index, record[Header.DEP_NUM].toInt())
    }

    enum class Header {
        REG_NUM,
        DEP_NUM
    }
}