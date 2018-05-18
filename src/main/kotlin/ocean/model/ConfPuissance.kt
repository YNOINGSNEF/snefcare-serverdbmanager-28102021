package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class ConfPuissance : OceanDataFile() {
    override val fileName = "OCEAN_CONFPUISSANCE"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.PUI_ID].toInt())
            stmt.setInt(++index, record[Header.CTR_ID].toInt())
            stmt.setString(++index, record[Header.PUI_LIB])
            stmt.setBoolean(++index, record[Header.PUI_DEF].toBool())
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
        PUI_ID,
        CTR_ID,
        PUI_LIB,
        PUI_DEF
    }
}