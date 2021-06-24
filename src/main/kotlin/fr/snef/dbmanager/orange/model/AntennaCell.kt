package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.orange.OrangeDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class AntennaCell(filename: String) : OrangeDataFile(filename) {

    companion object {
        const val tableName = "ANTENNA_CELL"

        fun from(fileName: String) = fileName
            .takeIf { it.startsWith(Antenna.filePrefix) }
            ?.let { AntennaCell(it) }
    }

    override val fileHeader = Antenna.Header::class.java

    override val tableName = AntennaCell.tableName
    override val tableHeader = listOf("antenna_id", "cell_id")

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE antenna_id = antenna_id"

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0
        stmt.setInt(++index, record[Antenna.Header.ID].toInt())
        stmt.setInt(++index, record[Antenna.Header.CELLULAR_NODE_ID].toInt())
    }
}
