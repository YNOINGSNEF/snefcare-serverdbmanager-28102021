package fr.snef.dbmanager.anfr.model

import fr.snef.dbmanager.anfr.AnfrDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Emetteur : AnfrDataFile() {
    override val fileHeader = Header::class.java
    override val ignoreInsertErrors = true

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.EMR_ID].toInt())
            stmt.setString(++index, record[Header.EMR_LB_SYSTEME])
            stmt.setString(++index, record[Header.STA_NM_ANFR])
            stmt.setInt(++index, record[Header.AER_ID].toInt())
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
        EMR_ID,
        EMR_LB_SYSTEME,
        STA_NM_ANFR,
        AER_ID
    }
}
