package fr.snef.dbmanager.anfr.model

import fr.snef.dbmanager.anfr.AnfrDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.Date
import java.sql.PreparedStatement
import java.text.ParseException
import java.text.SimpleDateFormat

class Station : AnfrDataFile() {
    override val fileHeader = Header::class.java

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setString(++index, record[Header.STA_NM_ANFR])
            stmt.setInt(++index, record[Header.ADM_ID].toInt())
            stmt.setInt(++index, record[Header.DEM_NM_COMSIS].toInt())
            stmt.setDate(++index, record[Header.DTE_IMPLANTATION].toSqlDate())
            stmt.setNullableDate(++index, record[Header.DTE_MODIF].toSqlDateOrNull())
            stmt.setNullableDate(++index, record[Header.DTE_EN_SERVICE].toSqlDateOrNull())
            stmt.addBatch()
            true
        } catch (ex: NumberFormatException) {
            stmt.clearParameters()
            false
        } catch (ex: ParseException) {
            stmt.clearParameters()
            false
        } catch (ex: TypeCastException) {
            stmt.clearParameters()
            false
        }
    }

    private fun String.toSqlDate() = Date(dateFormat.parse(this).time)

    private fun String.toSqlDateOrNull() = try {
        toSqlDate()
    } catch (ex: ParseException) {
        null
    }

    enum class Header {
        STA_NM_ANFR,
        ADM_ID,
        DEM_NM_COMSIS,
        DTE_IMPLANTATION,
        DTE_MODIF,
        DTE_EN_SERVICE
    }
}
