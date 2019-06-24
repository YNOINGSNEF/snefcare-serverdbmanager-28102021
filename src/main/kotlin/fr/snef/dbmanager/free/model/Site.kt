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

        stmt.setString(++index, record[Header.NOM_SITE])
        stmt.setString(++index, record[Header.NOM_SITE])
        stmt.setFloat(++index, record[Header.LATITUDE].toFloat())
        stmt.setFloat(++index, record[Header.LONGITUDE].toFloat())
        stmt.setInt(++index, record[Header.ALTITUDE_MSL].toInt())
    }
}