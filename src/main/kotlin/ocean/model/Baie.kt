package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Baie : OceanDataFile() {
    override val fileName = "OCEAN_BAIE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.BAI_ID].toInt())
        stmt.setNullableInt(++index, record[Header.BAI_SOURCEID].toIntOrNull())
        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setInt(++index, record[Header.CTB_ID].toInt())
        stmt.setNullableInt(++index, record[Header.COM_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.REL_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.NAT_ID].toInt())
        stmt.setInt(++index, record[Header.BAI_NUM].toInt())
        stmt.setInt(++index, record[Header.BAI_INDEX].toInt())
        stmt.setInt(++index, record[Header.BAI_NONINST].toInt())
        stmt.setInt(++index, record[Header.BAI_FLGOK].toInt())
        stmt.setInt(++index, record[Header.BAI_NBEXTEN].toInt())
        stmt.setString(++index, record[Header.BAI_COMMENT])
        stmt.setString(++index, record[Header.BAI_QUI])
        stmt.setTimestamp(++index, record[Header.BAI_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.BAI_VERSION].toInt())
        stmt.setNullableInt(++index, record[Header.RSL_ID].toIntOrNull())
        stmt.setNullableString(++index, record[Header.BAI_NE_2G].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.BAI_NE_3G].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.BBU2G_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.BBU3G_ID].toIntOrNull())
        stmt.setNullableString(++index, record[Header.ENODEB_ID].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.BAI_NE_4G].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.BBU4G_ID].toIntOrNull())
        stmt.setNullableString(++index, record[Header.CFG_ID].takeIf { it.isNotBlank() })
    }

    enum class Header {
        BAI_ID,
        BAI_SOURCEID,
        SIT_ID,
        CTB_ID,
        COM_ID,
        REL_ID,
        NAT_ID,
        BAI_NUM,
        BAI_INDEX,
        BAI_NONINST,
        BAI_FLGOK,
        BAI_NBEXTEN,
        BAI_COMMENT,
        BAI_QUI,
        BAI_QUAND,
        BAI_VERSION,
        RSL_ID,
        BAI_NE_2G,
        BAI_NE_3G,
        BBU2G_ID,
        BBU3G_ID,
        ENODEB_ID,
        BAI_NE_4G,
        BBU4G_ID,
        CFG_ID
    }
}