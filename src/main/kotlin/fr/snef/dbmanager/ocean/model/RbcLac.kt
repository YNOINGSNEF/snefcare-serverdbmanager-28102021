package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RbcLac : OceanDataFile() {
    override val fileName = "OCEAN_RBC_LAC"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.LAC_ID].toInt())
        stmt.setInt(++index, record[Header.LAC_VALUE].toInt())
        stmt.setInt(++index, record[Header.OPP_ID].toInt())
        stmt.setInt(++index, record[Header.RBC_ID].toInt())
        stmt.setNullableString(++index, record[Header.TYPEPROJET_ID].takeIf { it.isNotBlank() })
    }

    enum class Header {
        LAC_ID,
        LAC_VALUE,
        OPP_ID,
        RBC_ID,
        TYPEPROJET_ID
    }
}