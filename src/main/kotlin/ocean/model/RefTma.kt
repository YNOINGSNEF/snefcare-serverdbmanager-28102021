package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class RefTma : OceanDataFile() {
    override val fileName = "OCEAN_REFTMA"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.RFT_ID].toInt())
            stmt.setString(++index, record[Header.RFT_LIB])
            stmt.setBoolean(++index, record[Header.RFT_DEF].toBool())
            stmt.setBoolean(++index, record[Header.RFT_DEF_RS].toBool())
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
        RFT_ID,
        RFT_LIB,
        RFT_DEF,
        RFT_DEF_RS
    }
}