package fr.snef.dbmanager.orange.procedures

object ProcedureCells3G : ProcedureCells() {
    override val tableName = "CELL_3G"
    override val procedureQuery = """
        INSERT INTO CELL_3G(
        	id, num_ci, lac, rac, scrambling_code, is_indoor, frequency, pw, in_service, system_id, carrier_id, mcc, mnc, antenna_id
        )
        SELECT DISTINCT
        	C.ID,
            C.CID,
            C.LAC,
            C.RAC,
            C.SCRAMBLING_CODE,
            C.COUV = 'INDOOR' AS IS_INDOOR,
            IFNULL(C.UARFCN_DL, 0),
            0 AS PW, -- UNKNOWN FOR 3G
            C.NET_STATUS = 'OPERATIONAL' AS IN_SERVICE,
            CASE C.BAND
        		WHEN 'UMTS900' THEN 3
                WHEN 'UMTS2200' THEN 15
                ELSE -1 -- UNSUPPORTED SYSTEM
        	END AS SYSTEM_ID,
            $selectFieldCarrier,
            $selectFieldMcc,
            $selectFieldMnc,
            A.ID AS ANT_ID
        $fromAndJoins
        WHERE C.CELL_TYPE = 'NODEB_CELL';
    """
}