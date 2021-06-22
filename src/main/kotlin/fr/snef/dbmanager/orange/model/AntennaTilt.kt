package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.free.FreeDataFile
import fr.snef.dbmanager.free.model.Antenna
import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement

class AntennaTilt(filename: String) : FreeDataFile(filename) {
    override val tableName = "ANTENNA_TILT"
    override val tableHeader = listOf(
            "antenna_id",
            "system_id",
            "tilt"
    )

    override val insertSelectSql = "SELECT id, ?, ?" + Antenna.INSERT_SELECT_SQL

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE tilt = tilt"

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        val (system, tilt) = when {
            record[Header.TILT_U900].isNotBlank() -> System.U900 to record[Header.TILT_U900]
            record[Header.TILT_U2100].isNotBlank() -> System.U2100 to record[Header.TILT_U2100]
            record[Header.TILT_L700].isNotBlank() -> System.L700 to record[Header.TILT_L700]
            record[Header.TILT_L800].isNotBlank() -> System.L800 to record[Header.TILT_L800]
            record[Header.TILT_L1800].isNotBlank() -> System.L1800 to record[Header.TILT_L1800]
            record[Header.TILT_L2600].isNotBlank() -> System.L2600 to record[Header.TILT_L2600]
            else -> throw InvalidParameterException("Tilt has not been set")
        }

        stmt.setInt(++index, system.id)
        stmt.setInt(++index, tilt.filter { it.isDigit() }.toInt())
        Antenna.populateStatement(stmt, record, index)
    }
}