package model.files.rrcap

import model.DataFile
import model.Region
import model.Status
import model.TypeLien
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class S1BearerRoutes : DataFile() {
    override val fileName = "S1Bearer-routes"
    override val fileHeader = Header::class.java

    override val tableName = "S1BEARER_ROUTES"
    override val tableHeader = listOf(
            "region_code",
            "s1_name",
            "route_number",
            "route_sequence",
            "circuit_name",
            "site1",
            "node1",
            "port1",
            "site2",
            "node2",
            "port2",
            "type",
            "status"
    )

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE duplicates_count = duplicates_count + 1"

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
        var index = 0
        try {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.S1_NAME])
            stmt.setInt(++index, record[Header.ROUTE_NUMBER].toInt())
            stmt.setInt(++index, record[Header.ROUTE_SEQUENCE].toInt())
            stmt.setString(++index, record[Header.CIRCUIT_NAME])
            stmt.setNullableString(++index, record[Header.SITEA].extractSiteG2R())
            stmt.setString(++index, record[Header.NODEA])
            stmt.setString(++index, record[Header.PORTA])
            stmt.setNullableString(++index, record[Header.SITEB].extractSiteG2R())
            stmt.setString(++index, record[Header.NODEB])
            stmt.setString(++index, record[Header.PORTB])
            stmt.setString(++index, TypeLien.from(record[Header.CIRCUITTYPE]).label)
            stmt.setString(++index, Status.from(record[Header.STATUS]).label)
            stmt.addBatch()
            return true
        } catch (ex: NumberFormatException) {
            stmt.clearParameters()
            return false
        } catch (ex: TypeCastException) {
            stmt.clearParameters()
            return false
        }
    }

    enum class Header {
        REGION,
        S1_NAME,
        CIRCUIT_NAME,
        CIRCUITTYPE,
        ROUTE_NUMBER,
        ROUTE_SEQUENCE,
        SITEA,
        SITEB,
        NODEA,
        NODEB,
        NODETYPEA,
        NODETYPEB,
        NODEDEFA,
        NODEDEFB,
        PORTA,
        PORTB,
        BANDWIDTH,
        STATUS,
        RESOLUTION,
        SECTOR,
        VLANID
    }
}