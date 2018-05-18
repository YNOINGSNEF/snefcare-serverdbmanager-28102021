package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class CatalBaie : OceanDataFile() {
    override val fileName = "OCEAN_CATALBAIE"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.CTB_ID].toInt())
            stmt.setInt(++index, record[Header.CTR_ID].toInt())
            stmt.setInt(++index, record[Header.BAN_ID].toInt())
            stmt.setString(++index, record[Header.CTB_REF])
            stmt.setString(++index, record[Header.CTB_POWER])
            stmt.setBoolean(++index, record[Header.CTB_DEF].toBool())
            stmt.setInt(++index, record[Header.RS_FLAG].toInt())
            stmt.setNullableInt(++index, record[Header.MONO].toIntOrNull())
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
        CTB_ID,
        CTR_ID,
        BAN_ID,
        CTB_REF,
        CTB_POWER,
        CTB_DEF,
        RS_FLAG,
        MONO
    }
}