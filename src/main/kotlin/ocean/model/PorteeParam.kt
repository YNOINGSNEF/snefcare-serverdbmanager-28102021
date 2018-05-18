package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class PorteeParam : OceanDataFile() {
    override val fileName = "OCEAN_PORTEEPARAM"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.PPA_ID].toInt())
            stmt.setString(++index, record[Header.PPA_LIB])
            stmt.setString(++index, record[Header.PPA_TYPOBJ])
            stmt.setString(++index, record[Header.PPA_CAT])
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
        PPA_ID,
        PPA_LIB,
        PPA_TYPOBJ,
        PPA_CAT
    }
}