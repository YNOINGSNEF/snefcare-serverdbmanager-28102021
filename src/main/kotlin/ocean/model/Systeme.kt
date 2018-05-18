package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class Systeme : OceanDataFile() {
    override val fileName = "OCEAN_SYSTEME"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.SYS_ID].toInt())
            stmt.setInt(++index, record[Header.TEC_ID].toInt())
            stmt.setString(++index, record[Header.SYS_LIB])
            stmt.setString(++index, record[Header.SYS_DESC])
            stmt.setString(++index, record[Header.SYS_CODE])
            stmt.addBatch()
            true
        } catch (ex: NumberFormatException) {
            stmt.clearParameters()
            false
        } catch (ex: ParseException) {
            stmt.clearParameters()
            false
        } catch (ex: IllegalArgumentException) {
            stmt.clearParameters()
            false
        } catch (ex: Exception) {
            println("        > An error occurred : ${ex.message}")
            stmt.clearParameters()
            false
        }
    }

    enum class Header {
        SYS_ID,
        TEC_ID,
        SYS_LIB,
        SYS_DESC,
        SYS_CODE
    }
}