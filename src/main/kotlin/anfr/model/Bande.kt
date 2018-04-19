package anfr.model

import anfr.AnfrDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Bande : AnfrDataFile() {
    override val fileHeader = Header::class.java
    override val ignoreInsertErrors = true

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setString(++index, record[Header.STA_NM_ANFR])
            stmt.setInt(++index, record[Header.BAN_ID].toInt())
            stmt.setInt(++index, record[Header.EMR_ID].toInt())
            stmt.setNullableInt(++index, record[Header.BAN_NB_F_DEB].toIntOrNull())
            stmt.setNullableInt(++index, record[Header.BAN_NB_F_FIN].toIntOrNull())
            stmt.setString(++index, record[Header.BAN_FG_UNITE].take(1))
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
        BAN_ID,
        EMR_ID,
        BAN_NB_F_DEB,
        BAN_NB_F_FIN,
        BAN_FG_UNITE
    }
}
