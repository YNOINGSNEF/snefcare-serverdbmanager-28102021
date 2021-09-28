package fr.snef.dbmanager.orange.procedures

object ProcedureCells5G : ProcedureCells() {
    override val tableName = "CELL_5G"
    override val procedureQuery = """
        INSERT INTO CELL_5G(
            id, nci, tac, pci, is_indoor, frequency, pw, in_service, is_prev, system_id, carrier_id, mcc, mnc, antenna_id, site_id
        )
        SELECT
            MIN(C.ID),
            C.NCI,
            C.TAC,
            C.PCI,
            $selectFieldIsIndoor,
            IFNULL(C.NRARFCN_DL, 0) AS FREQUENCY,
            0 AS PW, /* UNKNOWN FOR 5G */
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
            C.CELL_TYPE = 'GNODEB_CELL'
            AND C.NRARFCN_DL REGEXP '^[0-9]*${'$'}'
            AND C.NCI REGEXP '^[0-9]+${'$'}'
            AND C.TAC IS NOT NULL
            AND C.PCI IS NOT NULL
        GROUP BY C.NCI, C.TAC, C.PCI, IS_INDOOR, FREQUENCY, PW, IN_SERVICE, SYSTEM_ID, CARRIER_ID, C_MCC, C_MNC, S.ID;
    """
}
