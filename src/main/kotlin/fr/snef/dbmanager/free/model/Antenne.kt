package fr.snef.dbmanager.free.model

import fr.snef.dbmanager.free.FreeDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Antenne(filename: String) : FreeDataFile(filename) {
    override val tableName = "ANTENNA"
    override val tableHeader = listOf(
            // "id",
            "sector_number",
            "azimuth",
            "reference",
            "manufacturer",
            "is_installed",
            "hba",
            "site_code"
    )

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE site_code = site_code"

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) = Antenne.populateStatement(stmt, record)

    companion object {
        const val INSERT_SELECT_SQL = " FROM ANTENNA" +
                " WHERE " +
                " sector_number = ?" +
                " AND azimuth = ?" +
                " AND reference = ?" +
                " AND manufacturer = ?" +
                " AND is_installed = ?" +
                " AND hba = ?" +
                " AND site_code = ?"

        fun populateStatement(stmt: PreparedStatement, record: CSVRecord, index: Int = 0) {
            var idx = index
            stmt.setInt(++idx, record[Header.NUMERO_SECTEUR].toInt())
            stmt.setInt(++idx, record[Header.AZIMUT].toInt())
            stmt.setString(++idx, record[Header.REFERENCE].extractAntennaReference())
            stmt.setString(++idx, record[Header.CONSTRUCTEUR])
            stmt.setBoolean(++idx, true)
            stmt.setFloat(++idx, record[Header.HAUTEUR_BASE].toFloat())
            stmt.setString(++idx, record[Header.NOM_SITE])
        }

        private fun String.extractAntennaReference(): String =
                "(.*)_[A-Z][0-9]{2}_T[0-9]{2}".toRegex()
                        .matchEntire(this)
                        ?.groupValues
                        ?.getOrNull(1)
                        ?: this
    }
}