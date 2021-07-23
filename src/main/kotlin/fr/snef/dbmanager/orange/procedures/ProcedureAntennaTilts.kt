package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

object ProcedureAntennaTilts : OrangeProcedureDataFile() {
    override val tableName = "ANTENNA_TILT"
    override val procedureQuery = """
        INSERT INTO ANTENNA_TILT(antenna_id, system_id, tilt)
        SELECT C.ANTENNA_ID, C.SYSTEM_ID, MAX(T.TILT_OMC)
        FROM (
            SELECT ID, ANTENNA_ID, SYSTEM_ID FROM CELL_2G UNION
            SELECT ID, ANTENNA_ID, SYSTEM_ID FROM CELL_3G UNION
            SELECT ID, ANTENNA_ID, SYSTEM_ID FROM CELL_4G UNION
            SELECT ID, ANTENNA_ID, SYSTEM_ID FROM CELL_5G
        ) AS C
        JOIN TMP_CELL T ON T.ID = C.ID
        WHERE T.TILT_OMC IS NOT NULL AND C.ANTENNA_ID IS NOT NULL
        GROUP BY C.ANTENNA_ID, C.SYSTEM_ID;
    """
}
