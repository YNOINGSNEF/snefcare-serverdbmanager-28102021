package fr.snef.dbmanager

import kotlin.math.*

object Utils {

        enum class Projection(
            /**
             * Exposant de la projection
             */
            val n: Double,
            /**
             * Constante de la projection
             */
            val c: Double,
            /**
             * Coordonnée X en projection du pôle
             */
            val xs: Double,
            /**
             * Coordonnée Y en projection du pôle
             */
            val ys: Double,
            /**
             * Longitude du méridien central
             */
            val lambda0: Double,
            /**
             * Première excentricité de l'ellipsoïde
             */
            val e: Double,
            /**
             * Tolérance de convergence
             */
            val epsilon: Double
    ) {
        LAMBERT_II_EXTENDED(0.728_968_627_4, 11_745_793.39, 600_000.0, 8_199_695.768, Math.toRadians(2.337_229_167), 0.082_483_256_76, 10e-11)
        /*
                 |   Lambert I  |   Lambert II  |   Lambert III  |   Lambert IV  |   Lambert II Etendue  |  Lambert 93
          -------|--------------|---------------|----------------|---------------|-----------------------|---------------
              n  | 0.7604059656 |  0.7289686274 |   0.6959127966 | 0.6712679322  |    0.7289686274       |  0.7256077650
          -------|--------------|---------------|----------------|---------------|-----------------------|---------------
              c  | 11603796.98  |  11745793.39  |   11947992.52  | 12136281.99   |    11745793.39        |  11754255.426
          -------|--------------|---------------|----------------|---------------|-----------------------|---------------
              Xs |   600000.0   |    600000.0   |   600000.0     |      234.358  |    600000.0           |     700000.0
          -------|--------------|---------------|----------------|---------------|-----------------------|---------------
              Ys | 5657616.674  |  6199695.768  |   6791905.085  |  7239161.542  |    8199695.768        | 12655612.050
        */
    }

    fun lambert2toWgs84(x: Double, y: Double): Pair<Double, Double> {
        val p = Projection.LAMBERT_II_EXTENDED

        val gamma = atan((x - p.xs) / (p.ys - y))
        val lng = p.lambda0 + (gamma / p.n)

        val r = sqrt((x - p.xs).pow(2) + (y - p.ys).pow(2))
        val isometricLat = (-1 / p.n) * ln(abs(r / p.c))

        val phi0 = 2 * atan(exp(isometricLat)) - PI / 2

        var prevPhi = phi0
        var lat = phi0
        do {
            val tmpPhi = lat
            lat = 2 * atan(((1 + p.e * sin(prevPhi)) / (1 - p.e * sin(prevPhi))).pow(p.e / 2) * exp(isometricLat)) - PI / 2
            prevPhi = tmpPhi
        } while (abs(lat - prevPhi) < p.epsilon)

        return Math.toDegrees(lat) to Math.toDegrees(lng)
    }
}