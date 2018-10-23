package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ConfChannelElem : OceanDataFile() {
    override val fileName = "OCEAN_CONFCHANNELELEM"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CHE_ID].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setString(++index, record[Header.CHE_CONF])
        stmt.setString(++index, record[Header.CHE_REPART])
        stmt.setInt(++index, record[Header.CHE_NBCE].toInt())
        stmt.setString(++index, record[Header.CHE_NBCECART])
        stmt.setString(++index, record[Header.CHE_INFO])
        stmt.setBoolean(++index, record[Header.CHE_DEF].toBool())
    }

    enum class Header {
        CHE_ID,
        CTR_ID,
        CHE_CONF,
        CHE_REPART,
        CHE_NBCE,
        CHE_NBCECART,
        CHE_INFO,
        CHE_DEF
    }
}