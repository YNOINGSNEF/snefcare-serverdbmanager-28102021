package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class OperatorApplic : OceanDataFile() {
    override val fileName = "OCEAN_OPERATORAPPLIC"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.OPP_ID].toInt())
            stmt.setString(++index, record[Header.OPP_LIB])
            stmt.setBoolean(++index, record[Header.OPP_DEF].toBool())
            stmt.setInt(++index, record[Header.ZP_LEADER].toInt())
            stmt.setString(++index, record[Header.OPP_CODE])
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
        OPP_ID,
        OPP_LIB,
        OPP_DEF,
        ZP_LEADER,
        OPP_CODE
    }
}