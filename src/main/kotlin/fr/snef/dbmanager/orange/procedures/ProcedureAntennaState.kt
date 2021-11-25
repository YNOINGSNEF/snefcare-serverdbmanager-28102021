package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

object ProcedureAntennaState : OrangeProcedureDataFile() {

    override val shouldTruncate = false
    override val tableName = "ANTENNA - INSTALLED STATE"
    override val procedureQuery = """
        UPDATE ANTENNA A
        JOIN (
            SELECT
                A.ID,
                IFNULL(MIN(C2.is_prev) = 0 
                    OR MIN(C3.is_prev) = 0 
                    OR MIN(C4.is_prev) = 0 
                    OR MIN(C5.is_prev) = 0, FALSE
                ) AS IS_INSTALLED
            FROM ANTENNA A
            LEFT JOIN CELL_2G C2 ON C2.ANTENNA_ID = A.ID
            LEFT JOIN CELL_3G C3 ON C3.ANTENNA_ID = A.ID
            LEFT JOIN CELL_4G C4 ON C4.ANTENNA_ID = A.ID
            LEFT JOIN CELL_5G C5 ON C5.ANTENNA_ID = A.ID
            GROUP BY A.ID
        ) AS A2 ON A2.ID = A.ID
        SET A.IS_INSTALLED = A2.IS_INSTALLED;
    """
}
