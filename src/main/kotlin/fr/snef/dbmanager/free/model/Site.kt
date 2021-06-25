package fr.snef.dbmanager.free.model

import fr.snef.dbmanager.free.FreeDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Site(filename: String) : FreeDataFile(filename) {
    override val tableName = "SITE"
    override val tableHeader = listOf(
            "code",
            "name",
            "latitude",
            "longitude",
            "altitude"
    )

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE code = code"

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        val altitude = record[Header.ALTITUDE_MSL].replace("\\s".toRegex(), "").toIntOrNull()
        if (altitude == null) {
            println("      > Warning, empty altitude on line : " + record.toList())
        }

        stmt.setString(++index, record[Header.NOM_SITE])
        stmt.setString(++index, record[Header.NOM_SITE])
        stmt.setFloat(++index, record[Header.LATITUDE].toFloat())
        stmt.setFloat(++index, record[Header.LONGITUDE].toFloat())
        stmt.setInt(++index, altitude ?: 0)
    }
}