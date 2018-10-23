package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class ConfBaie : OceanDataFile() {
    override val fileName = "OCEAN_CONFBAIE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CFB_ID].toInt())
        stmt.setNullableInt(++index, record[Header.CFB_SOURCEID].toIntOrNull())
        stmt.setInt(++index, record[Header.BAI_ID].toInt())
        stmt.setNullableInt(++index, record[Header.CHE_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CHE_MAXID].toIntOrNull())
        stmt.setInt(++index, record[Header.PUI_ID].toInt())
        stmt.setInt(++index, record[Header.SYS_ID].toInt())
        stmt.setNullableString(++index, record[Header.CFB_CONFTRX].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CFB_CONFTRXEDGE].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CFB_CONFTRXHR].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CFB_CONFMAXTRX].takeIf { it.isNotBlank() })
        stmt.setInt(++index, record[Header.CFB_NONINST].toInt())
        stmt.setString(++index, record[Header.CFB_COMMENT])
        stmt.setString(++index, record[Header.CFB_QUI])
        stmt.setTimestamp(++index, record[Header.CFB_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.CFB_VERSION].toInt())
        stmt.setNullableString(++index, record[Header.CFB_CONFTWINTRX].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CFB_CONFTRXEDGE_INST].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CFB_CONFMCTRX].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.MRAD_ID].toIntOrNull())
        stmt.setNullableBoolean(++index, record[Header.MRAD_GSM900].toBoolOrNull())
        stmt.setBoolean(++index, record[Header.MRAD_LTE1800].toBool())
        stmt.setBoolean(++index, record[Header.MRAD_GSM1800].toBool())
        stmt.setBoolean(++index, record[Header.MRAD_OPP].toBool())
        stmt.setNullableInt(++index, record[Header.CFB_TAC].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CFB_TAC_IOT].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CFB_TAC_BYT].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CFB_TAC_IOT_BYT].toIntOrNull())
    }

    enum class Header {
        CFB_ID,
        CFB_SOURCEID,
        BAI_ID,
        CHE_ID,
        CHE_MAXID,
        PUI_ID,
        SYS_ID,
        CFB_CONFTRX,
        CFB_CONFTRXEDGE,
        CFB_CONFTRXHR,
        CFB_CONFMAXTRX,
        CFB_NONINST,
        CFB_COMMENT,
        CFB_QUI,
        CFB_QUAND,
        CFB_VERSION,
        CFB_CONFTWINTRX,
        CFB_CONFTRXEDGE_INST,
        CFB_CONFMCTRX,
        MRAD_ID,
        MRAD_GSM900,
        MRAD_LTE1800,
        MRAD_GSM1800,
        MRAD_OPP,
        CFB_TAC,
        CFB_TAC_IOT,
        CFB_TAC_BYT,
        CFB_TAC_IOT_BYT
    }
}