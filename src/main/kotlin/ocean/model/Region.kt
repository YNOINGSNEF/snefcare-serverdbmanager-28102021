package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Region : OceanDataFile() {
    override val fileName = "OCEAN_REGION"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.REG_NUM].toInt())
        stmt.setString(++index, record[Header.REG_LIB])
        stmt.setString(++index, record[Header.REG_CODE])
        stmt.setBoolean(++index, record[Header.REG_VIRT].toBool())
    }

    enum class Header {
        REG_NUM,
        REG_LIB,
        REG_CODE,
        REG_VIRT
    }
}