package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

abstract class ProcedureCells : OrangeProcedureDataFile() {

    protected val selectFieldCarrier = """
        CASE
            WHEN IFNULL(C.MNC, 1) * 1 IN (1, 2, 91, 95) THEN 3 -- ORF
            WHEN IFNULL(C.MNC, 1) * 1 IN (15, 16) THEN 4 -- FRM
            WHEN IFNULL(C.MNC, 1) * 1 IN (8, 9, 10, 11, 13) THEN 1 -- SFR
            WHEN IFNULL(C.MNC, 1) * 1 IN (20, 21, 88) THEN 2 -- BYT
            ELSE -1 -- UNSUPPORTED CARRIER
        END AS CARRIER_ID
    """

    protected val selectFieldMcc = "IFNULL(C.MCC, 208) * 1 AS C_MCC"
    protected val selectFieldMnc = "IFNULL(C.MNC, 1) * 1 AS C_MNC"

    protected val fromAndJoins = """
        FROM TMP_CELL C
        INNER JOIN TMP_EQUIPMENT E ON E.CELLULAR_NODE_ID = C.ID
        INNER JOIN ANTENNA A
            ON A.ID_ORF = E.EQPT_ID
            AND C.SECTEUR = A.SECTOR_NUMBER
            AND C.AZM_SYNOP = A.AZIMUTH
            AND C.HBA = A.HBA
    """
}