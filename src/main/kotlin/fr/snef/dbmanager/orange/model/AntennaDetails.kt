package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.orange.OrangeDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class AntennaDetails(filename: String) : OrangeDataFile(filename) {

    companion object {
        fun from(fileName: String) = AntennaDetails(fileName).takeIf { Cell.isValid(fileName) }
    }

    override val fileHeader = Cell.Header::class.java

    override val queryType = QueryType.UPDATE
    override val tableName = Antenna.tableName
    override val tableHeader = Antenna.tableHeader

    override val updateSql: String
        get() = "UPDATE $tableName" +
                " INNER JOIN ${AntennaCell.tableName} ON $tableName.id = ${AntennaCell.tableName}.antenna_id" +
                " SET " +
                tableUpdateFields.joinToString(separator = ",", transform = { "$it=?" }) +
                " WHERE ${AntennaCell.tableName}.cell_id = ?"

    override val tableUpdateFields = listOf("sector_number", "azimuth", "hba")

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Cell.Header.SECTEUR].toInt())
        stmt.setInt(++index, record[Cell.Header.AZM_SYNOP].toInt())
        stmt.setFloat(++index, record[Cell.Header.HBA].toFloat())
        stmt.setString(++index, record[Cell.Header.ID])
    }
}