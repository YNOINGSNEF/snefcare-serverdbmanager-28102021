package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class Biastee : OceanDataFile() {
    override val fileName = "OCEAN_BIASTEE"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.BTE_ID].toInt())
            stmt.setString(++index, record[Header.BTE_LIB])
            stmt.setBoolean(++index, record[Header.BTE_DEF].toBool())
            stmt.setBoolean(++index, record[Header.BTE_DEF_RS].toBool())
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
        BTE_ID,
        BTE_LIB,
        BTE_DEF,
        BTE_DEF_RS
    }
}