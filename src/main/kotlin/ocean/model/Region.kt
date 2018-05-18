package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class Region : OceanDataFile() {
    override val fileName = "OCEAN_REGION"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.REG_NUM].toInt())
            stmt.setString(++index, record[Header.REG_LIB])
            stmt.setString(++index, record[Header.REG_CODE])
            stmt.setBoolean(++index, record[Header.REG_VIRT].toBool())
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
        REG_NUM,
        REG_LIB,
        REG_CODE,
        REG_VIRT
    }
}