package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class MqAnt : OceanDataFile() {
    override val fileName = "OCEAN_MQANT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.MQA_ID].toInt())
        stmt.setString(++index, record[Header.MQA_LIB])
    }

    enum class Header {
        MQA_ID,
        MQA_LIB
    }
}