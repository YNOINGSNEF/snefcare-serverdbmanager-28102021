package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class DemandModi : OceanDataFile() {
    override val fileName = "OCEAN_DEMANDMODI"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.DDM_ID].toInt())
        stmt.setInt(++index, record[Header.REG_NUM].toInt())
        stmt.setInt(++index, record[Header.TDM_ID].toInt())
        stmt.setNullableInt(++index, record[Header.ODT_NUM].toIntOrNull())
        stmt.setInt(++index, record[Header.EDM_ID].toInt())
        stmt.setString(++index, record[Header.DDM_COMMENT])
        stmt.setString(++index, record[Header.DDM_COMREJET])
        stmt.setNullableInt(++index, record[Header.DDM_IDELEM].toIntOrNull())
        stmt.setInt(++index, record[Header.DDM_TYPELEM].toInt())
        stmt.setNullableTimestamp(++index, record[Header.DDM_DATPREV].toTimestampOrNull())
        stmt.setString(++index, record[Header.DDM_AUTGEN])
        stmt.setTimestamp(++index, record[Header.DDM_DATGEN].toTimestamp())
        stmt.setString(++index, record[Header.DDM_QUI])
        stmt.setTimestamp(++index, record[Header.DDM_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.DDM_VERSION].toInt())
        stmt.setNullableInt(++index, record[Header.CHP_ID].toIntOrNull())
    }

    enum class Header {
        DDM_ID,
        REG_NUM,
        TDM_ID,
        ODT_NUM,
        EDM_ID,
        DDM_COMMENT,
        DDM_COMREJET,
        DDM_IDELEM,
        DDM_TYPELEM,
        DDM_DATPREV,
        DDM_AUTGEN,
        DDM_DATGEN,
        DDM_QUI,
        DDM_QUAND,
        DDM_VERSION,
        CHP_ID
    }
}