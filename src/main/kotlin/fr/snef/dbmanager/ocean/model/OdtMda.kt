package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class OdtMda : OceanDataFile() {
    override val fileName = "OCEAN_ODT_MDA"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.OMA_ID].toInt())
        stmt.setInt(++index, record[Header.ODT_ID].toInt())
        stmt.setInt(++index, record[Header.MDA_ID].toInt())
        stmt.setInt(++index, record[Header.OMA_FLAG].toInt())
    }

    enum class Header {
        OMA_ID,
        ODT_ID,
        MDA_ID,
        OMA_FLAG
    }
}