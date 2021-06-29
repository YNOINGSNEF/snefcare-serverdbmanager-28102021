package fr.snef.dbmanager.orange.procedures

object ProcedureCells4G : ProcedureCells() {
    override val tableName = "CELL_4G"
    override val procedureQuery = """
        INSERT INTO CELL_4G(
            id, eci, tac, pci, is_indoor, frequency, pw, in_service, system_id, carrier_id, mcc, mnc, antenna_id
        )
        SELECT DISTINCT
            C.ID,
            C.ECI,
            C.TAC,
            C.PCI,
            C.COUV = 'INDOOR' AS IS_INDOOR,
            IFNULL(C.EARFCN_DL, 0) AS FREQUENCY,
            0 AS PW, -- UNKNOWN FOR 4G
            C.NET_STATUS = 'OPERATIONAL' AS IN_SERVICE,
            CASE C.BAND
                WHEN 'LTE700' THEN 5
                WHEN 'LTE800' THEN 6
                WHEN 'LTE1800' THEN 7
                WHEN 'LTE2100' THEN 8
                WHEN 'LTE2600' THEN 9
                ELSE -1 -- UNSUPPORTED SYSTEM
            END AS SYSTEM_ID,
            $selectFieldCarrier,
            $selectFieldMcc,
            $selectFieldMnc,
            MAX(A.ID) AS ANT_ID
        $fromAndJoins
        WHERE
            C.CELL_TYPE = 'ENODEB_CELL'
            AND C.PCI IS NOT NULL
            AND C.TAC IS NOT NULL
        GROUP BY C.ID, C.ECI, C.TAC, C.PCI, IS_INDOOR, FREQUENCY, PW, IN_SERVICE, SYSTEM_ID, CARRIER_ID, C_MCC, C_MNC;
    """
}