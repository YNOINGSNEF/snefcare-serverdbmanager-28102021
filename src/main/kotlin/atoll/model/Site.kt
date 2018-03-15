package atoll.model

import atoll.DataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement
import kotlin.math.*

class Site : DataFile() {
    override val fileName = "Sites"
    override val fileHeader = Header::class.java

    override val tableName = "SITE"
    override val tableHeader = listOf(
            "region_code",
            "gn",
            "name",
            "latitude",
            "longitude",
            "opp_leader"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0

        val x = record[Header.X].replace(" ", "").toDouble()
        val y = record[Header.Y].replace(" ", "").toDouble()
        val (lat, lng) = lambert93ToWgs84(x, y)

        val oppLeader = record[Header.OPP_LEADER]
                ?.takeIf { it.isNotBlank() }
                ?.substringBefore('_', "")
                ?.replace("Free", "FRM", true)

        stmt.setString(++index, record[Header.UR])
        stmt.setString(++index, record[Header.NAME])
        stmt.setString(++index, record[Header.ALIAS])
        stmt.setDouble(++index, lat)
        stmt.setDouble(++index, lng)
        stmt.setNullableString(++index, oppLeader)
        stmt.addBatch()
        return true
    }

    /**
     * http://geodesie.ign.fr/contenu/fichiers/documentation/algorithmes/notice/NTG_71.pdf
     * http://geodesie.ign.fr/contenu/fichiers/documentation/pedagogiques/TransformationsCoordonneesGeodesiques.pdf
     * WGS84 -> http://spatialreference.org/ref/epsg/4326/
     */
    private fun lambert93ToWgs84(x: Double, y: Double): Pair<Double, Double> {
        // Constantes de la projection (Lambert II étendu)
        val n = 0.725_607_765
        val c = 11_754_255.426
        val xs = 700_000.0
        val ys = 12_655_612.050

        // Longitude du point origine par rapport au méridien origine (radians)
        // (3° 00' E Greenwich)
        val lambda0 = 3.0.toRadians()

        // Première excentricité de l’ellipsoïde IAG GRS 80
        val e = 0.081_819_191_12

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
        NAME,
        X,
        Y,
        ALIAS,
        UR,
        OPP_LEADER
    }
}