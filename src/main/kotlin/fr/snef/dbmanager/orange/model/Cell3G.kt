package fr.snef.dbmanager.orange.model

import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement

class Cell3G(filename: String) : Cell(filename) {

    companion object {
        fun from(fileName: String) = fileName.takeIf { isValid(it) }?.let { Cell3G(it) }
    }

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

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        val cellId = record[Header.ID].toInt()
        val mcc = record[Header.MCC].toIntOrNull() ?: 208
        val mnc = record[Header.MNC].toIntOrNull() ?: 1
        val carrier =
            Carrier.from(mcc, mnc) ?: throw InvalidParameterException("Unsupported carrier for MCC=$mcc, MNC=$mnc")

        val numCI =
            record[Header.CID].toIntOrNull() ?: throw InvalidParameterException("Ignoring non-3G cell (missing CI)")
        val lac =
            record[Header.LAC].toIntOrNull() ?: throw InvalidParameterException("Ignoring non-3G cell (missing LAC)")
        val rac =
            record[Header.RAC].toIntOrNull() ?: throw InvalidParameterException("Ignoring non-3G cell (missing RAC)")
        val scramblingCode = record[Header.SCRAMBLING_CODE].toIntOrNull()
            ?: throw InvalidParameterException("Ignoring non-3G cell (missing scrambling code)")

        val system = record[Header.BAND]
            .takeIf { it.contains("UMTS") }
            ?.let { band ->
                when (band) {
                    "UMTS 900 MHz" -> System.U900
                    "UMTS 2200 MHz" -> System.U2200
                    else -> {
                        println("      > ERROR: Unsupported 3G cell band=$band")
                        null
                    }
                }
            }
            ?: throw InvalidParameterException("Ignoring non-3G cell (wrong band)")

        var index = 0
        stmt.setInt(++index, cellId)
        stmt.setInt(++index, numCI)
        stmt.setInt(++index, lac)
        stmt.setInt(++index, rac)
        stmt.setInt(++index, scramblingCode)
        stmt.setBoolean(++index, record[Header.COUV].toLowerCase().contains("indoor"))
        stmt.setFloat(++index, record[Header.UARFCN_DL].toFloatOrNull() ?: 0f)
        stmt.setFloat(++index, 0f) // pw
        stmt.setBoolean(++index, record[Header.NET_STATUS].toLowerCase().contains("service"))
        stmt.setInt(++index, system.id)
        stmt.setInt(++index, carrier.id)
        stmt.setInt(++index, mcc)
        stmt.setInt(++index, mnc)
        stmt.setInt(++index, cellId)
    }
}