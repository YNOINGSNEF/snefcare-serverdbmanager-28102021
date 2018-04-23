package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Antenne : OceanDataFile() {
    override val fileName = "OCEAN_ANTENNE"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.ANT_ID].toInt())
            stmt.setString(++index, record[Header.ANT_SOURCEID])
            stmt.setInt(++index, record[Header.CTA_ID].toInt())
            stmt.setInt(++index, record[Header.SIT_ID].toInt())
            stmt.setInt(++index, record[Header.ANT_NUM].toInt())
            stmt.setInt(++index, record[Header.ANT_INDEX].toInt())
            stmt.setInt(++index, record[Header.ANT_NUMSECT].toInt())
            stmt.setInt(++index, record[Header.ANT_AZIMUT].toInt())
            stmt.setString(++index, record[Header.ANT_HBASE])
            stmt.setString(++index, record[Header.ANT_HBSIM])
            stmt.setInt(++index, record[Header.ANT_TILTMQ].toInt())
            stmt.setString(++index, record[Header.ANT_COOXREEL])
            stmt.setString(++index, record[Header.ANT_COOYREEL])
            stmt.setString(++index, record[Header.ANT_COOZREEL])
            stmt.setString(++index, record[Header.ANT_COOXSIMUL])
            stmt.setString(++index, record[Header.ANT_COOYSIMUL])
            stmt.setString(++index, record[Header.ANT_COOZSIMUL])
            stmt.setInt(++index, record[Header.ANT_NONINST].toInt())
            stmt.setString(++index, record[Header.ANT_COMMENT])
            stmt.setString(++index, record[Header.ANT_QUI])
            stmt.setString(++index, record[Header.ANT_QUAND])
            stmt.setString(++index, record[Header.ANT_VERSION])
            stmt.setString(++index, record[Header.ANT_ZPID])
            stmt.setNullableInt(++index, record[Header.ANT_SANTE].toIntOrNull())
            stmt.setNullableInt(++index, record[Header.ANT_RANG].toIntOrNull())
            stmt.addBatch()
            true
        } catch (ex: NumberFormatException) {
            stmt.clearParameters()
            false
        }
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