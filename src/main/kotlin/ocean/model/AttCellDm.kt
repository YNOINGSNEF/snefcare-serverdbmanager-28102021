package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class AttCellDm : OceanDataFile() {
    override val fileName = "OCEAN_ATTCELLDM"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.CDM_ID].toInt())
            stmt.setString(++index, record[Header.CDM_NOM])
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
        CDM_ID,
        CDM_NOM
    }
}