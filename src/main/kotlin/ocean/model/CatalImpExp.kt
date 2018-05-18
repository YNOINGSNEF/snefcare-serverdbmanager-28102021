package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class CatalImpExp : OceanDataFile() {
    override val fileName = "OCEAN_CATALIMPEXP"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.CIE_ID].toInt())
            stmt.setString(++index, record[Header.CIE_NOM])
            stmt.setString(++index, record[Header.CIE_DESCRIPTION])
            stmt.setString(++index, record[Header.CIE_PACKAGE])
            stmt.setInt(++index, record[Header.CIE_TYPE].toInt())
            stmt.setInt(++index, record[Header.CIE_VALID].toInt())
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
        CIE_ID,
        CIE_NOM,
        CIE_DESCRIPTION,
        CIE_PACKAGE,
        CIE_TYPE,
        CIE_VALID
    }
}