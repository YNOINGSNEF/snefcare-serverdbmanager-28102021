package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

object ProcedureAntennas : OrangeProcedureDataFile() {
    override val tableName = "ANTENNA"
    override val procedureQuery = """
        INSERT INTO ANTENNA(id_orf, sector_number, azimuth, reference, manufacturer, is_installed, hba, site_id)
        SELECT E.EQPT_ID, C.SECTEUR, C.AZM_SYNOP, E.EQPT_CATALOG, E.MANUFACTURER, NOT(E.IS_PREV) AS IS_INSTALLED, IFNULL(C.HBA, 0) AS A_HBA, S.ID
        FROM TMP_EQUIPMENT E
        INNER JOIN TMP_CELL C ON C.ID = E.CELLULAR_NODE_ID
        INNER JOIN SITE S ON S.ID_ORF = E.SITE_ID
        WHERE E.DEVICE_TYPE LIKE 'Antenne%' AND E.DEVICE_TYPE NOT LIKE '%GPS%'
        GROUP BY E.EQPT_ID, C.SECTEUR, C.AZM_SYNOP, E.EQPT_CATALOG, E.MANUFACTURER, IS_INSTALLED, A_HBA, S.ID;
    """
}
