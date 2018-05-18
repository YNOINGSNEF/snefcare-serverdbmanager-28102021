package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class ZoneSpec : OceanDataFile() {
    override val fileName = "OCEAN_ZONESPEC"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.ZSP_ID].toInt())
            stmt.setInt(++index, record[Header.REG_NUM].toInt())
            stmt.setString(++index, record[Header.ZSP_LIB])
            stmt.setString(++index, record[Header.ZSP_CODE])
            stmt.setBoolean(++index, record[Header.ZSP_DEF].toBool())
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
        ZSP_ID,
        REG_NUM,
        ZSP_LIB,
        ZSP_CODE,
        ZSP_DEF
    }
}