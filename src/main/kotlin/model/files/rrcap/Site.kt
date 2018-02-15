package model.files.rrcap

import model.DataFile
import model.Region
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import kotlin.math.*

class Site : DataFile() {
    override val fileName = "Site"
    override val fileHeader = Header::class.java

    override val tableName = "SITE"
    override val tableHeader = listOf(
            "region_code",
            "num_g2r",
            "name",
            "latitude",
            "longitude"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
        var index = 0
        val siteName = record[Header.SITE].extractSiteName()
        if (siteName != null) {
            val x = record[Header.COORDX].replace(',', '.').toDoubleOrNull()
            val y = record[Header.COORDY].replace(',', '.').toDoubleOrNull()

            val (lat, lng) =
                    if (x == null || y == null) null to null
                    else lambert2EtenduToWgs84(x * 1000, y * 1000)

            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.ALIAS_SITE])
            stmt.setString(++index, siteName)
            stmt.setNullableDouble(++index, lat)
            stmt.setNullableDouble(++index, lng)
            stmt.addBatch()
            return true
        }

        return false
    }

    /**
     * http://geodesie.ign.fr/contenu/fichiers/documentation/algorithmes/notice/NTG_71.pdf
     * http://geodesie.ign.fr/contenu/fichiers/documentation/pedagogiques/TransformationsCoordonneesGeodesiques.pdf
     * WGS84 -> http://spatialreference.org/ref/epsg/4326/
     * Lambert II étendu -> http://spatialreference.org/ref/epsg/27572/
     */
    private fun lambert2EtenduToWgs84(x: Double, y: Double): Pair<Double, Double> {

        // Constantes de la projection (Lambert II étendu)
        val n = 0.728_968_627_4
        val c = 11_745_793.39
        val xs = 600_000.0
        val ys = 8_199_695.768

        // Longitude du point origine par rapport au méridien origine (radians)
        // 0 grades Paris (soit 2° 20' 14,025" E Greenwich)
        val lambda0 = 2.337_229_17.toRadians()

        // Première excentricité de l’ellipsoïde Clarke 1880 français
        val e = 0.082_483_256_76

        val convergenceTolerance = 10.0.pow(-11)

        val isometricLatitude = -1 / n * ln(abs(sqrt((x - xs).pow(2) + (y - ys).pow(2)) / c))

        var phi1: Double
        var phi2 = 2 * atan(exp(isometricLatitude)) - PI / 2
        do {
            phi1 = phi2
            phi2 = 2 * atan(((1 + e * sin(phi1)) / (1 - e * sin(phi1))).pow(e / 2) * exp(isometricLatitude)) - PI / 2
        } while (abs(phi2 - phi1) > convergenceTolerance)

        val longitude = lambda0 + atan((x - xs) / (ys - y)) / n
        val latitude = phi2

        return latitude.toDegrees() to longitude.toDegrees()
    }

    private fun Double.toDegrees() = Math.toDegrees(this)
    private fun Double.toRadians() = Math.toRadians(this)

    enum class Header {
        REGION,
        ALIAS_REGION,
        PROVINCE,
        SOUS_REGION,
        ALIAS_SOUS_REGION,
        DEPARTEMENT,
        ALIAS_DEPARTEMENT,
        SITE,
        ALIAS_SITE,
        TOWNCITY,
        ZIP,
        RESPONSIBLE,
        TELEPHONE,
        FAX,
        NOTES,
        CREATEDDATE,
        LASTMODIFIEDDATE,
        CREATEDBY,
        LASTMODIFIEDBY,
        COORDX,
        COORDY,
        COORDZ,
        BUILDINGHEIGHT,
        SITEOWNER,
        BUILDINGTYPE,
        ANTENNAHEIGHT,
        ANTENNAHEIGHTSEALEVEL,
        PYLONTYPE,
        TRANSSITETYPE,
        ADRINTL,
        ADRCPLT,
        LIEUDIT,
        STATUSCHANGEDATE,
        STATUS,
        ZP_SITE_CODE
    }
}