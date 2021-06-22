package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class AttCellDm : OceanDataFile() {
    override val fileName = "OCEAN_ATTCELLDM"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CDM_ID].toInt())
        stmt.setString(++index, record[Header.CDM_NOM])
    }

    enum class Header {
        CDM_ID,
        CDM_NOM
    }
}