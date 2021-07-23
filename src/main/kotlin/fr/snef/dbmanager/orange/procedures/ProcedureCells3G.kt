package fr.snef.dbmanager.orange.procedures

object ProcedureCells3G : ProcedureCells() {
    override val procedureQuery = """
        INSERT INTO CELL_3G(
        	id, num_ci, lac, rac, scrambling_code, is_indoor, frequency, pw, in_service, system_id, carrier_id, mcc, mnc, antenna_id, site_id
        )
        SELECT
        	MIN(C.ID),
            C.CID,
            C.LAC,
            C.RAC,
            C.SCRAMBLING_CODE,
            $selectFieldIsIndoor,
            IFNULL(C.UARFCN_DL, 0) AS FREQUENCY,
            0 AS PW, /* UNKNOWN FOR 3G */
            $selectFieldInService,
            $selectFieldSystemId,
            $selectFieldCarrier,
            $selectFieldMcc,
            $selectFieldMnc,
            null, /* ANTENNA_ID populated later */
            S.ID
        $fromAndJoins
        WHERE
            C.CELL_TYPE = 'NODEB_CELL'
            AND C.SCRAMBLING_CODE IS NOT NULL
            AND C.LAC IS NOT NULL
        GROUP BY C.CID, C.LAC, C.RAC, C.SCRAMBLING_CODE, IS_INDOOR, FREQUENCY, PW, IN_SERVICE, SYSTEM_ID, CARRIER_ID, C_MCC, C_MNC, S.ID;
    """
    override val tableName = "CELL_3G"
}
