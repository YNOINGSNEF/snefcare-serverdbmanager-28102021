package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.free.FreeDataFile
import fr.snef.dbmanager.free.model.Antenna
import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement

class Cell4G(filename: String) : FreeDataFile(filename) {
    override val tableName = "CELL_4G"
    override val tableHeader = listOf(
            "id",
            "eci",
            "tac",
            "pci",
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

    override val insertSelectSql = "SELECT null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, id" + Antenna.INSERT_SELECT_SQL

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        val eci = record[Header.ECI].toIntOrNull() ?: throw InvalidParameterException("Line doesn't contain 4G cell")

        stmt.setInt(++index, eci)
        stmt.setInt(++index, record[Header.TAC].toInt())
        stmt.setInt(++index, record[Header.PCI].toInt())
        stmt.setBoolean(++index, record[Header.TYPE] == "INDOOR")
        stmt.setFloat(++index, record[Header.FREQUENCE_DL].toFloat())
        stmt.setFloat(++index, record[Header.PW].toFloat())
        stmt.setBoolean(++index, true)
        stmt.setInt(++index, System.valueOf(record[Header.SYSTEME]).id)
        stmt.setInt(++index, record[Header.OPERATEUR].extractCarrierId())
        stmt.setInt(++index, 208)
        stmt.setInt(++index, 15)
        Antenna.populateStatement(stmt, record, index)
    }
}