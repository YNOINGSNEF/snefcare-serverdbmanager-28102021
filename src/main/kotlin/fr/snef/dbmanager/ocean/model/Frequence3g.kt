package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Frequence3g : OceanDataFile() {
    override val fileName = "OCEAN_FREQUENCE3G"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.F3G_ID].toInt())
        stmt.setInt(++index, record[Header.F3G_FREQ].toInt())
        stmt.setBoolean(++index, record[Header.F3G_FLGDEF].toBool())
        stmt.setInt(++index, record[Header.F3G_PORTEUSE].toInt())
        stmt.setInt(++index, record[Header.SYS_ID].toInt())
    }

    enum class Header {
        F3G_ID,
        F3G_FREQ,
        F3G_FLGDEF,
        F3G_PORTEUSE,
        SYS_ID
    }
}