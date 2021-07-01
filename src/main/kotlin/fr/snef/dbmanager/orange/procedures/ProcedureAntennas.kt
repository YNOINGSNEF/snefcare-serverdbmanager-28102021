package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

object ProcedureAntennas : OrangeProcedureDataFile() {
    override val tableName = "ANTENNA"
    override val procedureQuery = """
        INSERT INTO ANTENNA(id_orf, sector_number, azimuth, reference, manufacturer, is_installed, hba, site_id)
        SELECT E.EQPT_ID, C.SECTEUR, C.AZM_SYNOP, E.EQPT_CATALOG, E.MANUFACTURER, NOT(E.IS_PREV), IFNULL(C.HBA, 0), E.SITE_ID
        FROM TMP_EQUIPMENT E
        INNER JOIN TMP_CELL C ON C.ID = E.CELLULAR_NODE_ID
        WHERE E.DEVICE_TYPE LIKE 'Antenne%' AND E.DEVICE_TYPE NOT LIKE '%GPS%';
    """
}