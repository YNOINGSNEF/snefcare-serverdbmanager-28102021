package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Site : OceanDataFile() {
    override val fileName = "OCEAN_SITE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

//        val x = record[Header.SIT_COOXSIM].toDouble() * 1000
//        val y = record[Header.SIT_COOYSIM].toDouble() * 1000
//
//        val (lat, lng) = lambert2toWgs84(x, y)
//
//        println("($x; $y) -> ($lat, $lng)")

        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setInt(++index, record[Header.DEP_NUM].toInt())
        stmt.setInt(++index, record[Header.REG_NUM].toInt())
        stmt.setNullableInt(++index, record[Header.ZGE_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.TPS_ID].toIntOrNull())
        stmt.setNullableString(++index, record[Header.SIT_NUMG2R].takeIf { it.isNotBlank() })
        stmt.setString(++index, record[Header.SIT_NOM])
        stmt.setFloat(++index, record[Header.SIT_COOXREEL].toFloat())
        stmt.setFloat(++index, record[Header.SIT_COOYREEL].toFloat())
        stmt.setInt(++index, record[Header.SIT_COOZREEL].toInt())
        stmt.setFloat(++index, record[Header.SIT_COOXSIM].toFloat())
        stmt.setFloat(++index, record[Header.SIT_COOYSIM].toFloat())
        stmt.setInt(++index, record[Header.SIT_COOZSIM].toInt())
        stmt.setString(++index, record[Header.SIT_LONGIT])
        stmt.setString(++index, record[Header.SIT_LATIT])
        stmt.setString(++index, record[Header.SIT_NSCADAS])
        stmt.setString(++index, record[Header.SIT_NPCADAS])
        stmt.setString(++index, record[Header.SIT_INSEE])
        stmt.setString(++index, record[Header.SIT_COMMENT])
        stmt.setString(++index, record[Header.SIT_QUI])
        stmt.setTimestamp(++index, record[Header.SIT_QUAND].toTimestamp())
        stmt.setString(++index, record[Header.SIT_ADRESSE])
        stmt.setString(++index, record[Header.SIT_COMPADR])
        stmt.setString(++index, record[Header.SIT_LIEUDIT])
        stmt.setString(++index, record[Header.SIT_CODPOST])
        stmt.setString(++index, record[Header.SIT_COMMUNE])
        stmt.setString(++index, record[Header.SIT_PROPR])
        stmt.setString(++index, record[Header.SIT_RESPONS])
        stmt.setString(++index, record[Header.SIT_TEL])
        stmt.setString(++index, record[Header.SIT_FAX])
        stmt.setInt(++index, record[Header.SIT_VERSION].toInt())
        stmt.setNullableString(++index, record[Header.SIT_ZPID].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.TYPE_SITE_RS_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.SIT_PHASE].toIntOrNull())
        stmt.setInt(++index, record[Header.OPP_ID].toInt())
        stmt.setInt(++index, record[Header.SIT_RS].toIntOrNull() ?: 0)
        stmt.setNullableInt(++index, record[Header.SIT_ZONERS].toIntOrNull())
        stmt.setString(++index, record[Header.SIT_PLQEXPL])
        stmt.setNullableBoolean(++index, record[Header.SIT_SANTE].toBoolOrNull())
        stmt.setNullableString(++index, record[Header.CONF_HARD].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CONF_INTERNE].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PLAQ_CONCEPT_ID].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PLAQ_CONCEPT_LABEL].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PLAQ_DEPL_LABEL].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PLAQ_DEPL_ID].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.SPECIAUX_LABEL].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.FRONTIERE].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.SIT_ZONE].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.ERS_ID].takeIf { it.isNotBlank() })
    }

//    private fun lambert2toWgs84(x: Double, y: Double): Pair<Double, Double> {
//        val p = Projection.LAMBERT_II_EXTENDED
//
//        val gamma = atan((x - p.xs) / (p.ys - y))
//        val lng = p.lambda0 + (gamma / p.n)
//
//        val r = sqrt((x - p.xs).pow(2) + (y - p.ys).pow(2))
//        val isometricLat = (-1 / p.n) * ln(abs(r / p.c))
//
//        val phi0 = 2 * atan(exp(isometricLat)) - PI / 2
//
//        var prevPhi = phi0
//        var lat = phi0
//        do {
//            val tmpPhi = lat
//            lat = 2 * atan(((1 + p.e * sin(prevPhi)) / (1 - p.e * sin(prevPhi))).pow(p.e / 2) * exp(isometricLat)) - PI / 2
//            prevPhi = tmpPhi
//        } while (abs(lat - prevPhi) < p.epsilon)
//
//        return Math.toDegrees(lat) to Math.toDegrees(lng)
//    }
//
//    enum class Projection(
//            /**
//             * Exposant de la projection
//             */
//            val n: Double,
//            /**
//             * Constante de la projection
//             */
//            val c: Double,
//            /**
//             * Coordonnée X en projection du pôle
//             */
//            val xs: Double,
//            /**
//             * Coordonnée Y en projection du pôle
//             */
//            val ys: Double,
//            /**
//             * Longitude du méridien central
//             */
//            val lambda0: Double,
//            /**
//             * Première excentricité de l'ellipsoïde
//             */
//            val e: Double,
//            /**
//             * Tolérance de convergence
//             */
//            val epsilon: Double
//    ) {
//        LAMBERT_II_EXTENDED(0.728_968_627_4, 11_745_793.39, 600_000.0, 8_199_695.768, Math.toRadians(2.337_229_167), 0.082_483_256_76, 10e-11)
//        /*
//                 |   Lambert I  |   Lambert II  |   Lambert III  |   Lambert IV  |   Lambert II Etendue  |  Lambert 93
//          -------|--------------|---------------|----------------|---------------|-----------------------|---------------
//              n  | 0.7604059656 |  0.7289686274 |   0.6959127966 | 0.6712679322  |    0.7289686274       |  0.7256077650
//          -------|--------------|---------------|----------------|---------------|-----------------------|---------------
//              c  | 11603796.98  |  11745793.39  |   11947992.52  | 12136281.99   |    11745793.39        |  11754255.426
//          -------|--------------|---------------|----------------|---------------|-----------------------|---------------
//              Xs |   600000.0   |    600000.0   |   600000.0     |      234.358  |    600000.0           |     700000.0
//          -------|--------------|---------------|----------------|---------------|-----------------------|---------------
//              Ys | 5657616.674  |  6199695.768  |   6791905.085  |  7239161.542  |    8199695.768        | 12655612.050
//        */
//    }

    enum class Header {
        SIT_ID,
        DEP_NUM,
        REG_NUM,
        ZGE_ID,
        TPS_ID,
        SIT_NUMG2R,
        SIT_NOM,
        SIT_COOXREEL,
        SIT_COOYREEL,
        SIT_COOZREEL,
        SIT_COOXSIM,
        SIT_COOYSIM,
        SIT_COOZSIM,
        SIT_LONGIT,
        SIT_LATIT,
        SIT_NSCADAS,
        SIT_NPCADAS,
        SIT_INSEE,
        SIT_COMMENT,
        SIT_QUI,
        SIT_QUAND,
        SIT_ADRESSE,
        SIT_COMPADR,
        SIT_LIEUDIT,
        SIT_CODPOST,
        SIT_COMMUNE,
        SIT_PROPR,
        SIT_RESPONS,
        SIT_TEL,
        SIT_FAX,
        SIT_VERSION,
        SIT_ZPID,
        TYPE_SITE_RS_ID,
        SIT_PHASE,
        OPP_ID,
        SIT_RS,
        SIT_ZONERS,
        SIT_PLQEXPL,
        SIT_SANTE,
        CONF_HARD,
        CONF_INTERNE,
        PLAQ_CONCEPT_ID,
        PLAQ_CONCEPT_LABEL,
        PLAQ_DEPL_LABEL,
        PLAQ_DEPL_ID,
        SPECIAUX_LABEL,
        FRONTIERE,
        SIT_ZONE,
        ERS_ID
    }
}