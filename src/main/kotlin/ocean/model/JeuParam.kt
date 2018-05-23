package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class JeuParam : OceanDataFile() {
    override val fileName = "OCEAN_JEUPARAM"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.JPA_ID].toInt())
        stmt.setInt(++index, record[Header.PPA_ID].toInt())
        stmt.setInt(++index, record[Header.JPA_IDOBJ].toInt())
        stmt.setNullableInt(++index, record[Header.JPA_NUMDI].toIntOrNull())
        stmt.setInt(++index, record[Header.MOD_ID].toInt())
        stmt.setInt(++index, record[Header.ESW_ID].toInt())
        stmt.setString(++index, record[Header.JPA_QUI])
        stmt.setTimestamp(++index, record[Header.JPA_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.JPA_VERSION].toInt())
    }

    enum class Header {
        JPA_ID,
        PPA_ID,
        JPA_IDOBJ,
        JPA_NUMDI,
        MOD_ID,
        ESW_ID,
        JPA_QUI,
        JPA_QUAND,
        JPA_VERSION
    }
}