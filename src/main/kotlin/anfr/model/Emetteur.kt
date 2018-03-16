package anfr.model

import anfr.AnfrDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Emetteur : AnfrDataFile() {
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        TODO()
    }

    enum class Header {
        EMR_ID,
        EMR_LB_SYSTEME,
        STA_NM_ANFR,
        AER_ID
    }
}
