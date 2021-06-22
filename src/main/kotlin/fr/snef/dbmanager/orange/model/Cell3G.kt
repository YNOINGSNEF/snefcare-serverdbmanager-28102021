package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.free.FreeDataFile
import fr.snef.dbmanager.free.model.Antenne
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

        val power = record[Header.PW].toFloatOrNull()
        if (power == null) {
            println("      > Warning, empty PW on line : " + record.toList())
        }

        val frequency = record[Header.FREQUENCE_DL].toFloatOrNull()
        if (frequency == null) {
            println("      > Warning, empty frequency on line : " + record.toList())
        }

        stmt.setInt(++index, numCi)
        stmt.setInt(++index, record[Header.LAC].toInt())
        stmt.setInt(++index, 1)
        stmt.setInt(++index, record[Header.SCRAMBLING_CODE].toInt())
        stmt.setBoolean(++index, record[Header.TYPE] == "INDOOR")
        stmt.setFloat(++index, frequency ?: 0f)
        stmt.setFloat(++index, power ?: 0f)
        stmt.setBoolean(++index, true)
        stmt.setInt(++index, System.valueOf(record[Header.SYSTEME]).id)
        stmt.setInt(++index, record[Header.OPERATEUR].extractCarrierId())
        stmt.setInt(++index, 208)
        stmt.setInt(++index, 15)
        Antenne.populateStatement(stmt, record, index)
    }
}