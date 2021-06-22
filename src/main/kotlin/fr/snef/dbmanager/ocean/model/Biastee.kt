package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Biastee : OceanDataFile() {
    override val fileName = "OCEAN_BIASTEE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.BTE_ID].toInt())
        stmt.setString(++index, record[Header.BTE_LIB])
        stmt.setBoolean(++index, record[Header.BTE_DEF].toBool())
        stmt.setBoolean(++index, record[Header.BTE_DEF_RS].toBool())
    }

    enum class Header {
        BTE_ID,
        BTE_LIB,
        BTE_DEF,
        BTE_DEF_RS
    }
}