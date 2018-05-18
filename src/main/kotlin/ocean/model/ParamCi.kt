package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class ParamCi : OceanDataFile() {
    override val fileName = "OCEAN_PARAMCI"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setString(++index, record[Header.PCI_NOM])
            stmt.setInt(++index, record[Header.PCI_VAL].toInt())
            stmt.setInt(++index, record[Header.PCI_TYPE].toInt())
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
        PCI_NOM,
        PCI_VAL,
        PCI_TYPE
    }
}