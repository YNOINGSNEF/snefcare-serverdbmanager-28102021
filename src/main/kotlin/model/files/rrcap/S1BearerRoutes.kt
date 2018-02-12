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
        try {
            stmt.setString(1, region.name)
            stmt.setString(2, record[Header.S1_NAME])
            stmt.setInt(3, record[Header.ROUTE_NUMBER].toInt())
            stmt.setInt(4, record[Header.ROUTE_SEQUENCE].toInt())
            stmt.setString(5, record[Header.CIRCUIT_NAME])
            stmt.setNullableString(6, record[Header.SITEA].extractSiteG2R())
            stmt.setString(7, record[Header.NODEA])
            stmt.setString(8, record[Header.PORTA])
            stmt.setNullableString(9, record[Header.SITEB].extractSiteG2R())
            stmt.setString(10, record[Header.NODEB])
            stmt.setString(11, record[Header.PORTB])
            stmt.setString(12, TypeLien.from(record[Header.CIRCUITTYPE]).label)
            stmt.setString(13, Status.from(record[Header.STATUS]).label)
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