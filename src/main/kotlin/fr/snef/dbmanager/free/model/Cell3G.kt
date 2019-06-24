package fr.snef.dbmanager.free.model

import fr.snef.dbmanager.free.FreeDataFile
import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement

class Cell3G(filename: String) : FreeDataFile(filename) {
    override val tableName = "CELL_3G"
    override val tableHeader = listOf(
            "id",
            "num_ci",
            "lac",
            "rac",
            "scrambling_code",
            "is_indoor",
            "frequency",
            "pw",
            "in_service",
            "system_id",
            "carrier_id",
            "mcc",
            "mnc",
            "antenna_id"
    )

    override val insertSelectSql = "SELECT null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, id" + Antenne.INSERT_SELECT_SQL

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        val numCi = record[Header.NUM_CI].toIntOrNull()
                ?: throw InvalidParameterException("Line doesn't contain 3G cell")

        stmt.setInt(++index, numCi)
        stmt.setInt(++index, record[Header.LAC].toInt())
        stmt.setInt(++index, record[Header.RAC].toIntOrNull() ?: 1)
        stmt.setInt(++index, record[Header.SCRAMBLING_CODE].toInt())
        stmt.setBoolean(++index, record[Header.TYPE] == "INDOOR")
        stmt.setFloat(++index, record[Header.FREQUENCE_DL].toFloat())
        stmt.setFloat(++index, record[Header.PW].toFloat())
        stmt.setBoolean(++index, true)
        stmt.setInt(++index, System.valueOf(record[Header.SYSTEME]).id)
        stmt.setInt(++index, record[Header.OPERATEUR].extractCarrierId())
        stmt.setInt(++index, 208)
        stmt.setInt(++index, 15)
        Antenne.populateStatement(stmt, record, index)
    }
}