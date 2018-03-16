package anfr.model

import anfr.AnfrDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Antenne : AnfrDataFile() {
    override val fileHeader = Header::class.java

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        TODO()
    }

    enum class Header {
        STA_NM_ANFR,
        AER_ID,
        TAE_ID,
        AER_NB_DIMENSION,
        AER_FG_RAYON,
        AER_NB_AZIMUT,
        AER_NB_ALT_BAS
    }
}
