package fr.snef.dbmanager.orange.model

import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement

class Cell2G(filename: String) : Cell(filename) {

    companion object {
        fun from(fileName: String) = fileName.takeIf { isValid(it) }?.let { Cell2G(it) }
    }

    override val tableName = "CELL_2G"
    override val tableHeader = listOf(
        "id",
        "num_ci",
        "lac",
        "rac",
        "bcch",
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
            record[Header.ID].toIntOrNull() ?: throw InvalidParameterException("Ignoring non-2G cell (missing CI)")
        val lac =
            record[Header.LAC].toIntOrNull() ?: throw InvalidParameterException("Ignoring non-2G cell (missing LAC)")
        val rac =
            record[Header.RAC].toIntOrNull() ?: throw InvalidParameterException("Ignoring non-2G cell (missing RAC)")
        val bcch =
            0// TODO record[Header.BCCH].toIntOrNull() ?: throw InvalidParameterException("Ignoring non-2G cell (missing scrambling code)")

        val system = record[Header.BAND]
            .takeIf { it.contains("GSM") }
            ?.let { band ->
                when (band) {
                    "GSM 900 MHz" -> System.G900
                    "GSM 1800 MHz" -> System.G1800
                    else -> {
                        println("      > ERROR: Unsupported 2G cell band=$band")
                        null
                    }
                }
            }
            ?: throw InvalidParameterException("Ignoring non-2G cell (wrong band)")

//            "LTE 800 MHz" -> System.L800
//            "LTE 1800 MHz" -> System.L1800
//            "LTE 2100 MHz" -> System.L2100
//            "LTE 2600 MHz" -> System.L2600

        var index = 0
        stmt.setInt(++index, cellId)
        stmt.setInt(++index, numCI)
        stmt.setInt(++index, lac)
        stmt.setInt(++index, rac)
        stmt.setInt(++index, bcch)
        stmt.setBoolean(++index, record[Header.COUV].toLowerCase().contains("indoor"))
        stmt.setFloat(++index, 0f) // TODO record[Header.UARFCN_DL].toFloatOrNull() ?: 0f)
        stmt.setFloat(++index, 0f) // pw
        stmt.setBoolean(++index, record[Header.NET_STATUS].toLowerCase().contains("service"))
        stmt.setInt(++index, system.id)
        stmt.setInt(++index, carrier.id)
        stmt.setInt(++index, mcc)
        stmt.setInt(++index, mnc)
        stmt.setInt(++index, cellId)
    }
}
