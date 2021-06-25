package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class OdtRbc : OceanDataFile() {
    override val fileName = "OCEAN_ODT_RBC"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.OTR_ID].toInt())
        stmt.setInt(++index, record[Header.RBB_ID].toInt())
        stmt.setInt(++index, record[Header.ODT_ID].toInt())
        stmt.setInt(++index, record[Header.OTR_FLAG].toInt())
    }

    enum class Header {
        OTR_ID,
        RBB_ID,
        ODT_ID,
        OTR_FLAG
    }
}