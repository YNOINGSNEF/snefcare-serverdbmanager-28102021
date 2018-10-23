package fr.snef.dbmanager.anfr.model

import fr.snef.dbmanager.DataFile
import fr.snef.dbmanager.anfr.AnfrDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Support : AnfrDataFile() {
    override val fileHeader = Header::class.java
    override val fileCharset = DataFile.CHARSET_ANSI
    override val ignoreInsertErrors = true

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.SUP_ID].toInt())
            stmt.setString(++index, record[Header.STA_NM_ANFR])
            stmt.setInt(++index, record[Header.NAT_ID].toInt())
            stmt.setInt(++index, record[Header.COR_NB_DG_LAT].toInt())
            stmt.setInt(++index, record[Header.COR_NB_MN_LAT].toInt())
            stmt.setInt(++index, record[Header.COR_NB_SC_LAT].toInt())
            stmt.setString(++index, record[Header.COR_CD_NS_LAT].take(1))
            stmt.setInt(++index, record[Header.COR_NB_DG_LON].toInt())
            stmt.setInt(++index, record[Header.COR_NB_MN_LON].toInt())
            stmt.setInt(++index, record[Header.COR_NB_SC_LON].toInt())
            stmt.setString(++index, record[Header.COR_CD_EW_LON].take(1))
            stmt.setNullableFloat(++index, record[Header.SUP_NM_HAUT].toFloatOrNull())
            stmt.setNullableInt(++index, record[Header.TPO_ID].toIntOrNull())
            stmt.setString(++index, record[Header.ADR_LB_LIEU])
            stmt.setString(++index, record[Header.ADR_LB_ADD1])
            stmt.setString(++index, record[Header.ADR_LB_ADD2])
            stmt.setString(++index, record[Header.ADR_LB_ADD3])
            stmt.setInt(++index, record[Header.ADR_NM_CP].toInt())
            stmt.setString(++index, record[Header.COM_CD_INSEE])

            stmt.addBatch()
            true
        } catch (ex: NumberFormatException) {
            stmt.clearParameters()
            false
        } catch (ex: TypeCastException) {
            stmt.clearParameters()
            false
        }
    }

    enum class Header {
        SUP_ID,
        STA_NM_ANFR,
        NAT_ID,
        COR_NB_DG_LAT,
        COR_NB_MN_LAT,
        COR_NB_SC_LAT,
        COR_CD_NS_LAT,
        COR_NB_DG_LON,
        COR_NB_MN_LON,
        COR_NB_SC_LON,
        COR_CD_EW_LON,
        SUP_NM_HAUT,
        TPO_ID,
        ADR_LB_LIEU,
        ADR_LB_ADD1,
        ADR_LB_ADD2,
        ADR_LB_ADD3,
        ADR_NM_CP,
        COM_CD_INSEE
    }
}
