package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import java.text.ParseException

class ConfChannelElem : OceanDataFile() {
    override val fileName = "OCEAN_CONFCHANNELELEM"
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setInt(++index, record[Header.CHE_ID].toInt())
            stmt.setInt(++index, record[Header.CTR_ID].toInt())
            stmt.setString(++index, record[Header.CHE_CONF])
            stmt.setString(++index, record[Header.CHE_REPART])
            stmt.setInt(++index, record[Header.CHE_NBCE].toInt())
            stmt.setString(++index, record[Header.CHE_NBCECART])
            stmt.setString(++index, record[Header.CHE_INFO])
            stmt.setBoolean(++index, record[Header.CHE_DEF].toBool())
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
        CHE_ID,
        CTR_ID,
        CHE_CONF,
        CHE_REPART,
        CHE_NBCE,
        CHE_NBCECART,
        CHE_INFO,
        CHE_DEF
    }
}