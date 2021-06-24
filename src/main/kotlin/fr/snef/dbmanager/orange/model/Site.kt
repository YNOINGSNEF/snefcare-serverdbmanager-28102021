package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.Utils
import fr.snef.dbmanager.orange.OrangeDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Site(private val isPrev: Boolean, fileName: String) : OrangeDataFile(fileName) {

    companion object {
        private const val filePrefix = "NORIA_FLUX_GENERIQUE_SITE"
        private const val prevString = "PREV"

        fun from(fileName: String): Site? {
            if (fileName.startsWith(filePrefix)) {
                val isPrev = fileName.contains(prevString)
                return Site(isPrev, fileName)
            }
            return null
        }
    }

    override val fileHeader = Header::class.java

    override val tableName = "SITE"
    override val tableHeader = listOf(
            "id",
            "code",
            "name",
            "latitude",
            "longitude",
            "altitude",
            "is_prev"
    )

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        val (lat, lng) = Utils.lambert2toWgs84(record[Header.X_COORDINATE].toDouble(), record[Header.Y_COORDINATE].toDouble())

        stmt.setInt(++index, record[Header.ID].toInt())
        stmt.setString(++index, record[Header.GEO_CODE])
        stmt.setString(++index, record[Header.SITE_NAME])
        stmt.setFloat(++index, lat.toFloat())
        stmt.setFloat(++index, lng.toFloat())
        stmt.setInt(++index, record[Header.Z_COORDINATE].toInt())
        stmt.setBoolean(++index, isPrev)
    }

    enum class Header {
        ID,
        GEO_CODE,
        SITE_TYPE,
        SITE_NAME,
        X_COORDINATE,
        Y_COORDINATE,
        Z_COORDINATE,
        ADDRESS_1,
        ADDRESS_2,
        ADDRESS_3,
        ZIPCODE,
        CITY,
        INSEE,
        SECURITY,
        COMMENT,
        INACCESSIBILITY_PERIOD,
        NWH_ACCESS,
        NACELLE_REQUIRED,
        NACELLE_USE,
        DR_CODE,
        DR_NAME,
        UR_CODE,
        UR_NAME,
        END_ACTIVE_DATE,
        ZP_SITE,
        LONGITUDE_WGS84,
        LATITUDE_WGS84,
        CODE_PRG_REG,
        CODE_ZONE_REG
    }
}
