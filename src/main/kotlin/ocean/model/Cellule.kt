package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Cellule : OceanDataFile() {
    override val fileName = "OCEAN_CELLULE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CELL_ID].toInt())
        stmt.setNullableInt(++index, record[Header.RBC_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.SYS_ID].toInt())
        stmt.setInt(++index, record[Header.ENV_ID].toInt())
        stmt.setInt(++index, record[Header.TPC_ID].toInt())
        stmt.setNullableInt(++index, record[Header.ZSE_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZVI_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.ZRE_ID].toInt())
        stmt.setNullableInt(++index, record[Header.ZSP_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZFR_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZDE_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.CELL_NUMSECT].toInt())
        stmt.setNullableString(++index, record[Header.CELL_OBJMARK].takeIf { it.isNotBlank() })
        stmt.setString(++index, record[Header.CELL_COMF2G])
        stmt.setNullableInt(++index, record[Header.CELL_NUMDI].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.F3G_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.CELL_NUM].toInt())
        stmt.setNullableInt(++index, record[Header.CELL_CI].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_LAC].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_RAC].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_SAC].toIntOrNull())
        stmt.setBoolean(++index, record[Header.CELL_FSERV].toBool())
        stmt.setNullableInt(++index, record[Header.CELL_TCELL].toIntOrNull())
        stmt.setNullableString(++index, record[Header.CELL_AUTINT].takeIf { it.isNotBlank() })
        stmt.setNullableTimestamp(++index, record[Header.CELL_DATINT].toTimestampOrNull())
        stmt.setNullableFloat(++index, record[Header.CELL_PEM].toFloatOrNull())
        stmt.setNullableTimestamp(++index, record[Header.CELL_DATMES].toTimestampOrNull())
        stmt.setNullableString(++index, record[Header.CELL_DATMESPREV].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CELL_AUTCHGSTAT].takeIf { it.isNotBlank() })
        stmt.setNullableTimestamp(++index, record[Header.CELL_DATCHGSTAT].toTimestampOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_SCODE].toIntOrNull())
        stmt.setNullableString(++index, record[Header.CELL_PLAN].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CELL_SSPLAN].takeIf { it.isNotBlank() })
        stmt.setString(++index, record[Header.CELL_COMMENT])
        stmt.setString(++index, record[Header.CELL_QUI])
        stmt.setTimestamp(++index, record[Header.CELL_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.CELL_VERSION].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setInt(++index, record[Header.ESW_ID].toInt())
        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setNullableInt(++index, record[Header.PLMNVT].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZONE_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.OPP_ID].toInt())
        stmt.setNullableString(++index, record[Header.CELL_ZPID].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.ENODEB_ID].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.CELL_ECI].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.TPCL_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_TAC].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_PCI].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.F4G_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.ANR].toInt())
        stmt.setInt(++index, record[Header.CCLB].toInt())
        stmt.setInt(++index, record[Header.SERVICE_SUSPENDU].toInt())
        stmt.setNullableInt(++index, record[Header.ID_RSCELL].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ID_MAXPOWER].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ID_BANDWIDTH].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_MOCN].toIntOrNull())
        stmt.setInt(++index, record[Header.CELL_MNC].toInt())
        stmt.setInt(++index, record[Header.CELL_MCC].toInt())
        stmt.setInt(++index, record[Header.CCLB_SUSPENDU].toInt())
        stmt.setString(++index, record[Header.CELL_NAME])
        stmt.setNullableString(++index, record[Header.CELL_MONACO].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.FREQ_ROLE].toIntOrNull())
    }

    enum class Header {
        CELL_ID,
        RBC_ID,
        SYS_ID,
        ENV_ID,
        TPC_ID,
        ZSE_ID,
        ZVI_ID,
        ZRE_ID,
        ZSP_ID,
        ZFR_ID,
        ZDE_ID,
        CELL_NUMSECT,
        CELL_OBJMARK,
        CELL_COMF2G,
        CELL_NUMDI,
        F3G_ID,
        CELL_NUM,
        CELL_CI,
        CELL_LAC,
        CELL_RAC,
        CELL_SAC,
        CELL_FSERV,
        CELL_TCELL,
        CELL_AUTINT,
        CELL_DATINT,
        CELL_PEM,
        CELL_DATMES,
        CELL_DATMESPREV,
        CELL_AUTCHGSTAT,
        CELL_DATCHGSTAT,
        CELL_SCODE,
        CELL_PLAN,
        CELL_SSPLAN,
        CELL_COMMENT,
        CELL_QUI,
        CELL_QUAND,
        CELL_VERSION,
        CTR_ID,
        ESW_ID,
        SIT_ID,
        PLMNVT,
        ZONE_ID,
        OPP_ID,
        CELL_ZPID,
        ENODEB_ID,
        CELL_ECI,
        TPCL_ID,
        CELL_TAC,
        CELL_PCI,
        F4G_ID,
        ANR,
        CCLB,
        SERVICE_SUSPENDU,
        ID_RSCELL,
        ID_MAXPOWER,
        ID_BANDWIDTH,
        CELL_MOCN,
        CELL_MNC,
        CELL_MCC,
        CCLB_SUSPENDU,
        CELL_NAME,
        CELL_MONACO,
        FREQ_ROLE
    }
}