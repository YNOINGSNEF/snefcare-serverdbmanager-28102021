package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class EtatSw : OceanDataFile() {
    override val fileName = "OCEAN_ETATSW"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.ESW_ID].toInt())
            stmt.setString(++index, record[Header.ESW_CODE])
            stmt.setString(++index, record[Header.ESW_LIB])
            stmt.setString(++index, record[Header.ESW_CODECELL])
            stmt.setString(++index, record[Header.ESW_LIBCELL])
            stmt.setBoolean(++index, record[Header.ESW_DEF].toBool())
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
        ESW_ID,
        ESW_CODE,
        ESW_LIB,
        ESW_CODECELL,
        ESW_LIBCELL,
        ESW_DEF
    }
}