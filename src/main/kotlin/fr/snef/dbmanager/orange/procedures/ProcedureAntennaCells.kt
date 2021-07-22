package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

sealed class ProcedureAntenna(cellTable: String) : OrangeProcedureDataFile() {
    object Cell2G : ProcedureAntenna("CELL_2G")
    object Cell3G : ProcedureAntenna("CELL_3G")
    object Cell4G : ProcedureAntenna("CELL_4G")
    object Cell5G : ProcedureAntenna("CELL_5G")

    override val shouldTruncate = false
    override val tableName = "ANTENNA LINK - $cellTable"
    override val procedureQuery = """
        UPDATE $cellTable
        JOIN TMP_CELL C ON C.ID = $cellTable.ID
        JOIN TMP_EQUIPMENT E ON E.CELLULAR_NODE_ID = C.ID_ORF
        JOIN TMP_NETWORK_ELEMENT T ON T.ID = C.NETWORK_ELEMENT_ID
        JOIN SITE S ON S.CODE = T.GN_CODE
        JOIN ANTENNA A ON
            A.SITE_ID = S.ID
            AND A.REFERENCE = E.EQPT_CATALOG
            AND A.MANUFACTURER = E.MANUFACTURER
            AND A.SECTOR_NUMBER = C.SECTEUR
            AND A.AZIMUTH = C.AZM_SYNOP
            AND (A.HBA LIKE C.HBA  OR (A.HBA = 0 AND C.HBA IS NULL))
        SET ANTENNA_ID = A.ID;
    """
}
