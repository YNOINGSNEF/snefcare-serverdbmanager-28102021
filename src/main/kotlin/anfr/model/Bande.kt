package anfr.model

import anfr.AnfrDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Bande : AnfrDataFile() {
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        TODO()
    }

    enum class Header {
        STA_NM_ANFR,
        BAN_ID,
        EMR_ID,
        BAN_NB_F_DEB,
        BAN_NB_F_FIN,
        BAN_FG_UNITE
    }
}
