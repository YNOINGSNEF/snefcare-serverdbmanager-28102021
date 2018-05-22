package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.math.BigInteger
import java.sql.PreparedStatement

class ModAnt : OceanDataFile() {
    override val fileName = "OCEAN_MODANT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.MDA_ID].toInt())
        stmt.setNullableInt(++index, record[Header.MDA_SOURCEID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.RFT_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.RFD_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.FCA_ID].toInt())
        stmt.setInt(++index, record[Header.ANT_ID].toInt())
        stmt.setNullableInt(++index, record[Header.REP_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.BTE_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.SYS_ID].toInt())
        stmt.setNullableFloat(++index, record[Header.MDA_ROS].toFloatOrNull())
        stmt.setInt(++index, record[Header.MDA_REPART].toInt())
        stmt.setFloat(++index, record[Header.MDA_PERTEUL].toFloat())
        stmt.setFloat(++index, record[Header.MDA_PERTEDL].toFloat())
        stmt.setNullableString(++index, record[Header.MDA_G2RPILOT].takeIf { it.isNotBlank() && it.length > 4 }) // pour enlever les valeurs = -999
        stmt.setNullableInt(++index, record[Header.MDA_CIPILOT].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.MDA_NBBTE].toIntOrNull())
        stmt.setBoolean(++index, record[Header.MDA_TMA].toBool())
        stmt.setBoolean(++index, record[Header.MDA_DUPLEX].toBool())
        stmt.setBoolean(++index, record[Header.MDA_DIPLEX].toBool())
        stmt.setInt(++index, record[Header.MDA_NONINST].toInt())
        stmt.setString(++index, record[Header.MDA_COMMENT])
        stmt.setString(++index, record[Header.MDA_QUI])
        stmt.setTimestamp(++index, record[Header.MDA_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.MDA_VERSION].toInt())
        stmt.setNullableInt(++index, record[Header.TLT_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.TPC_ID].toIntOrNull())
        stmt.setBoolean(++index, record[Header.MDA_FEED].toBool())
        stmt.setBoolean(++index, record[Header.MDA_FIPRO].toBool())
        stmt.setNullableInt(++index, record[Header.RET_ID].toIntOrNull())
        stmt.setBoolean(++index, record[Header.MDA_RET].toBool())
        stmt.setNullableInt(++index, record[Header.REP_ID_1].toIntOrNull())
        stmt.setNullableString(++index, record[Header.MDA_G2RPILOT_1].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.MDA_CIPILOT_1].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.TPC_ID_1].toIntOrNull())
        stmt.setBoolean(++index, record[Header.MDA_MIMO].toBool())
        stmt.setNullableBoolean(++index, record[Header.MDA_SANTE].toBoolOrNull())
        stmt.setNullableBoolean(++index, record[Header.MDA_ATTENUATEUR].toBoolOrNull())
        stmt.setNullableInt(++index, record[Header.MDA_ATTENUATION].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.RMM_ID].toIntOrNull())
    }

    enum class Header {
        MDA_ID,
        MDA_SOURCEID,
        RFT_ID,
        RFD_ID,
        FCA_ID,
        ANT_ID,
        REP_ID,
        BTE_ID,
        SYS_ID,
        MDA_ROS,
        MDA_REPART,
        MDA_PERTEUL,
        MDA_PERTEDL,
        MDA_G2RPILOT,
        MDA_CIPILOT,
        MDA_NBBTE,
        MDA_TMA,
        MDA_DUPLEX,
        MDA_DIPLEX,
        MDA_NONINST,
        MDA_COMMENT,
        MDA_QUI,
        MDA_QUAND,
        MDA_VERSION,
        TLT_ID,
        TPC_ID,
        MDA_FEED,
        MDA_FIPRO,
        RET_ID,
        MDA_RET,
        REP_ID_1,
        MDA_G2RPILOT_1,
        MDA_CIPILOT_1,
        TPC_ID_1,
        MDA_MIMO,
        MDA_SANTE,
        MDA_ATTENUATEUR,
        MDA_ATTENUATION,
        RMM_ID
    }
}