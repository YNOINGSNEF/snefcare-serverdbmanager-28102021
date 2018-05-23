package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CellVoisOptiers : OceanDataFile() {
    override val fileName = "OCEAN_CELL_VOIS_OPTIERS"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CELL_ID].toInt())
        stmt.setInt(++index, record[Header.MCC].toInt())
        stmt.setInt(++index, record[Header.MNC].toInt())
        stmt.setNullableInt(++index, record[Header.NCC].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.BCC].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.BCCH].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CIO].toIntOrNull())
        stmt.setNullableString(++index, record[Header.CELL_TX].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.RBC_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.SYS_ID].toInt())
        stmt.setNullableInt(++index, record[Header.ENV_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.TPC_ID].toInt())
        stmt.setNullableInt(++index, record[Header.ZSE_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZVI_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZRE_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZSP_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZFR_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ZDE_ID].toIntOrNull())
        stmt.setInt(++index, record[Header.CELL_NUMSECT].toIntOrNull() ?: 0)
        stmt.setNullableInt(++index, record[Header.CELL_OBJMARK].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_COMF2G].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_NUMDI].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.F3G_FREQ].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_NUM].toIntOrNull())
        stmt.setInt(++index, record[Header.CELL_CI].toInt())
        stmt.setInt(++index, record[Header.CELL_LAC].toInt())
        stmt.setNullableInt(++index, record[Header.CELL_RAC].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_SAC].toIntOrNull())
        stmt.setInt(++index, record[Header.CELL_FSERV].toInt())
        stmt.setNullableInt(++index, record[Header.CELL_TCELL].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_AUTINT].toIntOrNull())
        stmt.setNullableTimestamp(++index, record[Header.CELL_DATINT].toTimestampOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_PEM].toIntOrNull())
        stmt.setNullableTimestamp(++index, record[Header.CELL_DATMES].toTimestampOrNull())
        stmt.setNullableTimestamp(++index, record[Header.CELL_DATMESPREV].toTimestampOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_AUTCHGSTAT].toIntOrNull())
        stmt.setNullableTimestamp(++index, record[Header.CELL_DATCHGSTAT].toTimestampOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_SCODE].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_PLAN].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_SSPLAN].toIntOrNull())
        stmt.setString(++index, record[Header.CELL_COMMENT])
        stmt.setString(++index, record[Header.CELL_QUI])
        stmt.setTimestamp(++index, record[Header.CELL_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.CELL_VERSION].toInt())
        stmt.setInt(++index, record[Header.CTR_ID].toInt())
        stmt.setInt(++index, record[Header.ESW_ID].toInt())
        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setNullableInt(++index, record[Header.PLMNVT].toIntOrNull())
        stmt.setInt(++index, record[Header.ZONE_ID].toInt())
        stmt.setNullableString(++index, record[Header.CELL_ZPID].takeIf { it.isNotBlank() })
        stmt.setInt(++index, record[Header.OPP_ID].toInt())
        stmt.setInt(++index, record[Header.GAB_ID].toInt())
        stmt.setNullableInt(++index, record[Header.RBC_ID2].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.UARFCN_UL].toIntOrNull())
        stmt.setNullableString(++index, record[Header.ENODEB_ID].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.CELL_PCI].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CELL_TAC].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.MSRXLEVMIN].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.F4G_FREQ].toIntOrNull())
        stmt.setInt(++index, record[Header.CELL_MNC].toInt())
        stmt.setInt(++index, record[Header.CELL_MCC].toInt())
    }

    enum class Header {
        CELL_ID,
        MCC,
        MNC,
        NCC,
        BCC,
        BCCH,
        CIO,
        CELL_TX,
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
        F3G_FREQ,
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
        CELL_ZPID,
        OPP_ID,
        GAB_ID,
        RBC_ID2,
        UARFCN_UL,
        ENODEB_ID,
        CELL_PCI,
        CELL_TAC,
        MSRXLEVMIN,
        F4G_FREQ,
        CELL_MNC,
        CELL_MCC
    }
}