package fr.snef.dbmanager.orange.procedures

object ProcedureCells2G : ProcedureCells() {
    override val tableName = "CELL_2G"
    override val procedureQuery = """
        INSERT INTO CELL_2G(
        	id, num_ci, lac, rac, bcch, is_indoor, frequency, pw, in_service, system_id, carrier_id, mcc, mnc, antenna_id
        )
        SELECT
        	C.ID,
            C.CI,
            C.LAC,
            IFNULL(C.RAC, 0) AS C_RAC,
            IFNULL(N.BCCH, 0) AS C_BCCH,
            $selectFieldIsIndoor,
            0 AS FREQUENCY, /* UNKNOWN FOR 2G */
            0 AS PW, /* UNKNOWN FOR 2G */
            $selectFieldInService,
            $selectFieldSystemId,
            $selectFieldCarrier,
            $selectFieldMcc,
            $selectFieldMnc,
            MAX(A.ID) AS ANT_ID
        $fromAndJoins
        INNER JOIN TMP_CELL_COMP N ON N.CELL_IDENTIFIER = C.CELL_IDENTIFIER
        WHERE
        	C.CELL_TYPE = 'BTS_CELL'
        	AND C.CI REGEXP '^[0-9]+${'$'}'
            AND C.LAC REGEXP '^[0-9]+${'$'}'
        GROUP BY C.ID, C.CI, C.LAC, C_RAC, C_BCCH, IS_INDOOR, FREQUENCY, PW, IN_SERVICE, SYSTEM_ID, CARRIER_ID, C_MCC, C_MNC;
    """
}
