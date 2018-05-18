package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class FctAnt : OceanDataFile() {
    override val fileName = "OCEAN_FCTANT"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.FCA_ID].toInt())
            stmt.setString(++index, record[Header.FCA_LIB])
            stmt.setBoolean(++index, record[Header.FCA_DEF].toBool())
            stmt.setBoolean(++index, record[Header.FCA_DEF_RS].toBool())
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
        FCA_ID,
        FCA_LIB,
        FCA_DEF,
        FCA_DEF_RS
    }
}