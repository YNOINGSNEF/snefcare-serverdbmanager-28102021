package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Antenne : OceanDataFile() {
    override val fileName = "OCEAN_ANTENNE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.ANT_ID].toInt())
        stmt.setNullableInt(++index, record[Header.ANT_SOURCEID].toIntOrNull())
        stmt.setInt(++index, record[Header.CTA_ID].toInt())
        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setInt(++index, record[Header.ANT_NUM].toInt())
        stmt.setInt(++index, record[Header.ANT_INDEX].toInt())
        stmt.setInt(++index, record[Header.ANT_NUMSECT].toInt())
        stmt.setInt(++index, record[Header.ANT_AZIMUT].toInt())
        stmt.setFloat(++index, record[Header.ANT_HBASE].toFloat())
        stmt.setFloat(++index, record[Header.ANT_HBSIM].toFloat())
        stmt.setInt(++index, record[Header.ANT_TILTMQ].toInt())
        stmt.setFloat(++index, record[Header.ANT_COOXREEL].toFloat())
        stmt.setFloat(++index, record[Header.ANT_COOYREEL].toFloat())
        stmt.setInt(++index, record[Header.ANT_COOZREEL].toInt())
        stmt.setFloat(++index, record[Header.ANT_COOXSIMUL].toFloat())
        stmt.setFloat(++index, record[Header.ANT_COOYSIMUL].toFloat())
        stmt.setInt(++index, record[Header.ANT_COOZSIMUL].toInt())
        stmt.setInt(++index, record[Header.ANT_NONINST].toInt())
        stmt.setString(++index, record[Header.ANT_COMMENT])
        stmt.setString(++index, record[Header.ANT_QUI])
        stmt.setTimestamp(++index, record[Header.ANT_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.ANT_VERSION].toInt())
        stmt.setString(++index, record[Header.ANT_ZPID])
        stmt.setNullableInt(++index, record[Header.ANT_SANTE].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.ANT_RANG].toIntOrNull())
    }

    enum class Header {
        ANT_ID,
        ANT_SOURCEID,
        CTA_ID,
        SIT_ID,
        ANT_NUM,
        ANT_INDEX,
        ANT_NUMSECT,
        ANT_AZIMUT,
        ANT_HBASE,
        ANT_HBSIM,
        ANT_TILTMQ,
        ANT_COOXREEL,
        ANT_COOYREEL,
        ANT_COOZREEL,
        ANT_COOXSIMUL,
        ANT_COOYSIMUL,
        ANT_COOZSIMUL,
        ANT_NONINST,
        ANT_COMMENT,
        ANT_QUI,
        ANT_QUAND,
        ANT_VERSION,
        ANT_ZPID,
        ANT_SANTE,
        ANT_RANG
    }
}