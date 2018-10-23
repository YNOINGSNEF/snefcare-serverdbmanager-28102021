package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class JeuParSousMod : OceanDataFile() {
    override val fileName = "OCEAN_JEUPARSSMOD"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.JSS_ID].toInt())
        stmt.setInt(++index, record[Header.SMO_ID].toInt())
        stmt.setInt(++index, record[Header.JSS_RANG].toInt())
    }

    enum class Header {
        JSS_ID,
        SMO_ID,
        JSS_RANG
    }
}