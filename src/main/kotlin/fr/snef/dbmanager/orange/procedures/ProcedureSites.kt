package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

object ProcedureSites : OrangeProcedureDataFile() {
    override val tableName = "SITE"
    override val procedureQuery = """
        INSERT INTO SITE(id, code, name, latitude, longitude, altitude, is_prev)
        SELECT
        	MIN(S.ID),
            S.GEO_CODE,
            S.SITE_NAME,
            LAMBERT_IIe_TO_WGS84_LAT(S.X_COORDINATE, S.Y_COORDINATE) AS LAT,
            LAMBERT_IIe_TO_WGS84_LNG(S.X_COORDINATE, S.Y_COORDINATE) AS LNG,
            S.Z_COORDINATE AS ALT,
            MIN(S.IS_PREV)
        FROM TMP_SITE S
        INNER JOIN TMP_NETWORK_ELEMENT N ON S.SITE_NAME = N.GN_NAME
        GROUP BY S.GEO_CODE, S.SITE_NAME, LAT, LNG, ALT;
    """ // TODO Use TMP_NETWORK_ELEMENT.GN_CODE for site_code
}
