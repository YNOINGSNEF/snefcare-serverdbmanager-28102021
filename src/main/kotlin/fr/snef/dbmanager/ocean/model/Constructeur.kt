package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Constructeur : OceanDataFile() {
    override val fileName = "OCEAN_CONSTRUCTEUR"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setString(++index, record[Header.CTR_LIB])
        stmt.setBoolean(++index, record[Header.CTR_DEF].toBool())
    }

    enum class Header {
        CTR_ID,
        CTR_LIB,
        CTR_DEF
    }
}