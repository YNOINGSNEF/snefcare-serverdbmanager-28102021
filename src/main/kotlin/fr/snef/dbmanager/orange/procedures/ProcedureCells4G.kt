package fr.snef.dbmanager.orange.procedures

object ProcedureCells4G : ProcedureCells() {
    override val tableName = "CELL_4G"
    override val procedureQuery = """
        INSERT INTO CELL_4G(
            id, eci, tac, pci, is_indoor, frequency, pw, in_service, is_prev, system_id, carrier_id, mcc, mnc, antenna_id, site_id
        )
        SELECT
            MIN(C.ID),
            C.ECI,
            C.TAC,
            C.PCI,
            $selectFieldIsIndoor,
            IFNULL(C.EARFCN_DL, 0) AS FREQUENCY,
            0 AS PW, /* UNKNOWN FOR 4G */
            $selectFieldInService,
            $selectFieldIsPrev,
            $selectFieldSystemId,
            $selectFieldCarrier,
            $selectFieldMcc,
            $selectFieldMnc,
            null, /* ANTENNA_ID populated later */
            S.ID
        $fromAndJoins
        WHERE
            C.CELL_TYPE = 'ENODEB_CELL'
            AND C.ECI REGEXP '^[0-9]+${'$'}'
            AND C.TAC IS NOT NULL
            AND C.PCI IS NOT NULL
        GROUP BY C.ECI, C.TAC, C.PCI, IS_INDOOR, FREQUENCY, PW, IN_SERVICE, SYSTEM_ID, CARRIER_ID, C_MCC, C_MNC, S.ID;
    """
}
