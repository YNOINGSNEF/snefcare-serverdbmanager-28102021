package fr.snef.dbmanager.orange.procedures

object ProcedureCells4G : ProcedureCells() {
    override val tableName = "CELL_4G"
    override val procedureQuery = """
        INSERT INTO CELL_4G(
            id, eci, tac, pci, is_indoor, frequency, pw, in_service, system_id, carrier_id, mcc, mnc, antenna_id
        )
        SELECT
            C.ID,
            C.ECI,
            C.TAC,
            C.PCI,
            $selectFieldIsIndoor,
            IFNULL(C.EARFCN_DL, 0) AS FREQUENCY,
            0 AS PW, /* UNKNOWN FOR 4G */
            $selectFieldInService,
            $selectFieldSystemId,
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
