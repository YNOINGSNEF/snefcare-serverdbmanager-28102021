package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class DummyVlan(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "DummyVlan"
    override val fileHeader = Header::class.java

    override val tableName = "DUMMY_VLAN"
    override val tableHeader = listOf(
            "region_code",
            "circuit_name",
            "vlan_id"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.CIRCUIT])
            stmt.setInt(++index, record[Header.DUMMY_VLAN].toInt())
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
        CIRCUIT,
        DUMMY_VLAN
    }
}