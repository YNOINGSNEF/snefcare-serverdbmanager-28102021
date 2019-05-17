package fr.snef.dbmanager.anfr.model

import fr.snef.dbmanager.anfr.AnfrDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Antenne : AnfrDataFile() {
    override val fileHeader = Header::class.java
    override val ignoreInsertErrors = true

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setString(++index, record[Header.STA_NM_ANFR])
            stmt.setInt(++index, record[Header.AER_ID].toInt())
            stmt.setInt(++index, record[Header.TAE_ID].toInt())
            stmt.setNullableFloat(++index, record[Header.AER_NB_DIMENSION].replace(',','.').toFloatOrNull())
            stmt.setNullableString(++index, record[Header.AER_FG_RAYON].take(1).takeIf { it.isNotBlank() })
            stmt.setNullableFloat(++index, record[Header.AER_NB_AZIMUT].replace(',','.').toFloatOrNull())
            stmt.setNullableFloat(++index, record[Header.AER_NB_ALT_BAS].replace(',','.').toFloatOrNull())
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
        STA_NM_ANFR,
        AER_ID,
        TAE_ID,
        AER_NB_DIMENSION,
        AER_FG_RAYON,
        AER_NB_AZIMUT,
        AER_NB_ALT_BAS
    }
}
