package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RbcBaie : OceanDataFile() {
    override val fileName = "OCEAN_RBC_BAIE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.RBB_ID].toInt())
        stmt.setInt(++index, record[Header.BAI_ID].toInt())
        stmt.setTimestamp(++index, record[Header.RBB_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.RBB_ETAT].toInt())
        stmt.setInt(++index, record[Header.TEC_ID].toInt())
        stmt.setInt(++index, record[Header.RBC_ID].toInt())
        stmt.setString(++index, record[Header.RBB_QUI])
    }

    enum class Header {
        RBB_ID,
        BAI_ID,
        RBB_QUAND,
        RBB_ETAT,
        TEC_ID,
        RBC_ID,
        RBB_QUI
    }
}