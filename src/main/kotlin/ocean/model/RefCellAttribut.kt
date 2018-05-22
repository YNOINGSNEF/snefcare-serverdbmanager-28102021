package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class RefCellAttribut : OceanDataFile() {
    override val fileName = "OCEAN_REF_CELL_ATTRIBUT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ATT_ID].toInt())
        stmt.setString(++index, record[Header.ATT_LIB])
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setInt(++index, record[Header.TEC_ID].toInt())
        stmt.setString(++index, record[Header.ATT_TYPE])
        stmt.setString(++index, record[Header.DEF_VALUE])
        stmt.setInt(++index, record[Header.ATT_RANG].toInt())
        stmt.setBoolean(++index, record[Header.ATT_ACTIF].toBool())
        stmt.setBoolean(++index, record[Header.DM_IMPACT].toBool())
    }

    enum class Header {
        ATT_ID,
        ATT_LIB,
        CTR_ID,
        TEC_ID,
        ATT_TYPE,
        DEF_VALUE,
        ATT_RANG,
        ATT_ACTIF,
        DM_IMPACT
    }
}