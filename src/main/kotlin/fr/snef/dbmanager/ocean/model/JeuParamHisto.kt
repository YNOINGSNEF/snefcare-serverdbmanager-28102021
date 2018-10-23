package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class JeuParamHisto : OceanDataFile() {
    override val fileName = "OCEAN_JEUPARAMHISTO"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.JPH_ID].toInt())
        stmt.setString(++index, record[Header.JPH_TYPE])
        stmt.setString(++index, record[Header.JPH_MODAVT])
        stmt.setString(++index, record[Header.JPH_MODAPR])
    }

    enum class Header {
        JPH_ID,
        JPH_TYPE,
        JPH_MODAVT,
        JPH_MODAPR
    }
}