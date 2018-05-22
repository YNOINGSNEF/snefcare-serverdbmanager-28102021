package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Frequence4g : OceanDataFile() {
    override val fileName = "OCEAN_FREQUENCE4G"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.F4G_ID].toInt())
        stmt.setInt(++index, record[Header.F4G_FREQ].toInt())
        stmt.setInt(++index, record[Header.SYS_ID].toInt())
        stmt.setBoolean(++index, record[Header.F4G_FLGDEF].toBool())
    }

    enum class Header {
        F4G_ID,
        F4G_FREQ,
        SYS_ID,
        F4G_FLGDEF
    }
}