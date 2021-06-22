package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.orange.OrangeDataFile
import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement

class AntennaTilt(filename: String) : OrangeDataFile(filename) {

    companion object {
        fun from(fileName: String) = fileName
            .takeIf { it.startsWith(Antenna.filePrefix) }
            ?.let { AntennaTilt(it) }
    }

    override val fileHeader = Antenna.Header::class.java

    override val tableName = "ANTENNA_TILT"
    override val tableHeader = listOf(
        "antenna_id",
        "system_id",
        "tilt"
    )

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE tilt = tilt"

    private val supportedEntries = mapOf(
        System.L700 to Antenna.Header.TILT_ELECTRIQUE_700,
        System.L800 to Antenna.Header.TILT_ELECTRIQUE_800,
        System.G900 to Antenna.Header.TILT_ELECTRIQUE_900,
        System.U900 to Antenna.Header.TILT_ELECTRIQUE_900,
        System.G1800 to Antenna.Header.TILT_ELECTRIQUE_1800,
        System.L1800 to Antenna.Header.TILT_ELECTRIQUE_1800,
        System.U2200 to Antenna.Header.TILT_ELECTRIQUE_2200,
        System.L2600 to Antenna.Header.TILT_ELECTRIQUE_2600,
        System.L3500 to Antenna.Header.TILT_ELECTRIQUE_3500,
        System.N3500 to Antenna.Header.TILT_ELECTRIQUE_NR3500
    )

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        if (!Antenna.isValid(record[Antenna.Header.DEVICE_TYPE])) {
            // Equipment file also contains bays & other RF equipments, but we only need antennas
            throw InvalidParameterException("Ignoring entry as not containing an antenna")
        }

        val antennaId = record[Antenna.Header.ID].toInt()
        val tilts = supportedEntries.mapNotNull { (system, header) ->
            record[header].toIntOrNull()?.let { tilt -> system to tilt }
        }

        tilts.forEach { (system, tilt) ->
            var index = 0
            stmt.setInt(++index, antennaId)
            stmt.setInt(++index, system.id)
            stmt.setInt(++index, tilt)
            stmt.addBatch()
        }
    }
}