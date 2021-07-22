package fr.snef.dbmanager.orange.procedures

import fr.snef.dbmanager.orange.OrangeProcedureDataFile

object ProcedureAntennas : OrangeProcedureDataFile() {
    override val tableName = "ANTENNA"
    override val procedureQuery = """
        INSERT INTO ANTENNA(id_orf, sector_number, azimuth, reference, manufacturer, is_installed, hba, site_id)
        SELECT MAX(E.EQPT_ID), C.SECTEUR, C.AZM_SYNOP, E.EQPT_CATALOG, E.MANUFACTURER, NOT(E.IS_PREV) AS IS_INSTALLED, IFNULL(C.HBA, 0) AS A_HBA, IDS.SITE_ID
        FROM (
            SELECT ID, SITE_ID FROM CELL_2G UNION
            SELECT ID, SITE_ID FROM CELL_3G UNION
            SELECT ID, SITE_ID FROM CELL_4G UNION
            SELECT ID, SITE_ID FROM CELL_5G
        ) IDS
        JOIN TMP_CELL C ON C.ID = IDS.ID
        JOIN TMP_EQUIPMENT E ON E.CELLULAR_NODE_ID = C.ID_ORF
        WHERE $equipmentAntennaFilter
        GROUP BY C.SECTEUR, C.AZM_SYNOP, E.EQPT_CATALOG, E.MANUFACTURER, IS_INSTALLED, A_HBA, IDS.SITE_ID;
    """
}
