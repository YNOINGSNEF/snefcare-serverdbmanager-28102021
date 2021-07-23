package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

abstract class ProcedureCells : OrangeProcedureDataFile() {

    protected val selectFieldIsIndoor = "IFNULL(C.COUV = 'INDOOR', false) AS IS_INDOOR"
    protected val selectFieldInService = "C.NET_STATUS = 'OPERATIONAL' AS IN_SERVICE"

    protected val selectFieldSystemId = """
        CASE C.BAND
            WHEN 'GSM900' THEN 1
            WHEN 'GSM1800' THEN 2
            
            WHEN 'UMTS900' THEN 3
            WHEN 'UMTS2200' THEN 15
            
            WHEN 'LTE700' THEN 5
            WHEN 'LTE800' THEN 6
            WHEN 'LTE1800' THEN 7
            WHEN 'LTE2100' THEN 8
            WHEN 'LTE2600' THEN 9
            
            ELSE -1 /* UNSUPPORTED SYSTEM */
        END AS SYSTEM_ID
    """

    protected val selectFieldCarrier = """
        CASE
            WHEN IFNULL(C.MNC, 1) * 1 IN (1, 2, 91, 95) THEN 3 /* ORF */
            WHEN IFNULL(C.MNC, 1) * 1 IN (15, 16) THEN 4 /* FRM */
            WHEN IFNULL(C.MNC, 1) * 1 IN (8, 9, 10, 11, 13) THEN 1 /* SFR */
            WHEN IFNULL(C.MNC, 1) * 1 IN (20, 21, 88) THEN 2 /* BYT */
            ELSE -1 /* UNSUPPORTED CARRIER */
        END AS CARRIER_ID
    """

    protected val selectFieldMcc = "IFNULL(C.MCC, 208) * 1 AS C_MCC"
    protected val selectFieldMnc = "IFNULL(C.MNC, 1) * 1 AS C_MNC"

    protected val fromAndJoins = """
        FROM TMP_CELL C
        JOIN TMP_EQUIPMENT E ON E.CELLULAR_NODE_ID = C.ID_ORF AND $equipmentAntennaFilter
		JOIN TMP_NETWORK_ELEMENT T ON T.ID = C.NETWORK_ELEMENT_ID
		JOIN SITE S ON S.CODE = T.GN_CODE
    """
}
