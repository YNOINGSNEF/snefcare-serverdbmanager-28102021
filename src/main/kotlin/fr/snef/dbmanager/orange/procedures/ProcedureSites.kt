package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

object ProcedureSites : OrangeProcedureDataFile() {
    override val tableName = "SITE"
    override val procedureQuery = """
        INSERT INTO SITE(id, id_orf, code, name, ur_name, latitude, longitude, altitude, is_prev)
        SELECT
            I.ID,
            I.SITE_ID,
            MAX(I.GN_CODE) AS GN_CODE,
            S.SITE_NAME,
            S.UR_NAME,
            LAMBERT_IIe_TO_WGS84_LAT(S.X_COORDINATE, S.Y_COORDINATE) AS LAT,
            LAMBERT_IIe_TO_WGS84_LNG(S.X_COORDINATE, S.Y_COORDINATE) AS LNG,
            S.Z_COORDINATE AS ALT,
            I.IS_PREV
        FROM (
            SELECT MAX(S.ID) AS ID, MAX(S.SITE_ID) AS SITE_ID, N.GN_CODE, MIN(S.IS_PREV) AS IS_PREV
            FROM TMP_SITE S
            JOIN TMP_EQUIPMENT E ON E.SITE_ID = S.SITE_ID
            JOIN TMP_CELL C ON C.ID_ORF = E.CELLULAR_NODE_ID
            JOIN TMP_NETWORK_ELEMENT N ON N.ID = C.NETWORK_ELEMENT_ID
            GROUP BY N.GN_CODE
            ORDER BY ID ASC
        ) I
        JOIN TMP_SITE S ON S.ID = I.ID
        GROUP BY I.ID, I.SITE_ID, I.IS_PREV;
    """
}
