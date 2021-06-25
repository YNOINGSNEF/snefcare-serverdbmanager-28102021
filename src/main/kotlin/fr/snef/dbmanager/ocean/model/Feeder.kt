package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Feeder : OceanDataFile() {
    override val fileName = "OCEAN_FEEDER"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.FEE_ID].toInt())
        stmt.setNullableInt(++index, record[Header.FEE_SOURCEID].toIntOrNull())
        stmt.setInt(++index, record[Header.CTF_ID].toInt())
        stmt.setNullableInt(++index, record[Header.TSC_IDFA].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.TSC_IDFB].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.FCF_ID].toIntOrNull())
        stmt.setFloat(++index, record[Header.FEE_LONG].replace(',', '.').toFloat())
        stmt.setNullableFloat(++index, record[Header.FEE_PRTCBL].replace(',', '.').toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.FEE_LGBTFA].replace(',', '.').toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.FEE_LGBTFB].replace(',', '.').toFloatOrNull())
        stmt.setString(++index, record[Header.FEE_COMMENT])
        stmt.setBoolean(++index, record[Header.FEE_NONINST].toBool())
        stmt.setString(++index, record[Header.FEE_QUI])
        stmt.setTimestamp(++index, record[Header.FEE_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.FEE_VERSION].toInt())
        stmt.setInt(++index, record[Header.MDA_ID].toInt())
    }

    enum class Header {
        FEE_ID,
        FEE_SOURCEID,
        CTF_ID,
        TSC_IDFA,
        TSC_IDFB,
        FCF_ID,
        FEE_LONG,
        FEE_PRTCBL,
        FEE_LGBTFA,
        FEE_LGBTFB,
        FEE_COMMENT,
        FEE_NONINST,
        FEE_QUI,
        FEE_QUAND,
        FEE_VERSION,
        MDA_ID
    }
}