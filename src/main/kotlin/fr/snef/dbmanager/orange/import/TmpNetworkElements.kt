package fr.snef.dbmanager.orange.import

import fr.snef.dbmanager.orange.OrangeImportDataFile

class TmpNetworkElements(
    fileNames: List<String>,
    dumpFolderPath: String
) : OrangeImportDataFile(fileNames, dumpFolderPath) {

    companion object {
        private const val filePrefix = "NORIA_FLUX_GENERIQUE_NETWORK_ELEMENT"

        fun from(fileNames: List<String>, dumpFolderPath: String) = TmpNetworkElements(
            fileNames.filter { it.startsWith(filePrefix) && !it.contains(complementSuffix) },
            dumpFolderPath
        )
    }

    override val tableName = "TMP_NETWORK_ELEMENT"

    override val createTemporaryTableQuery = """
        CREATE TABLE $tableName (
            ID              INT AUTO_INCREMENT PRIMARY KEY,
            GN_CODE	        TEXT NOT NULL,
            GN_NAME			VARCHAR(40) NOT NULL,
            MANUFACTURER	VARCHAR(255) NOT NULL,
            TYPE			VARCHAR(40) NOT NULL,
            NET_NAME		VARCHAR(255) NOT NULL,
            NAME			VARCHAR(255) NOT NULL,
            NET_STAT		VARCHAR(40) NOT NULL,
            NODE_ID         INT NOT NULL,
            IS_PREV         BOOLEAN NOT NULL
        );
    """

    override fun makePopulateTemporaryTableQueries() = fileNames.map { fileName ->
        return@map fileName to """
                LOAD DATA LOCAL INFILE '${fullPath(fileName)}'
                INTO TABLE $tableName
                FIELDS TERMINATED BY ';'
                LINES TERMINATED BY '\n'
                IGNORE 1 LINES
                ( ${retrieveHeaderColumns()} )
                SET ID = @ID,
                    GN_CODE = @GN_CODE,
                    GN_NAME = @GN_NAME,
                    MANUFACTURER = @MANUFACTURER,
                    TYPE = @TYPE,
                    NET_NAME = @NET_NAME,
                    NAME = @NAME,
                    NET_STAT = @NET_STAT,
                    NODE_ID = @NODE_ID,
                    IS_PREV = ${fileName.contains(prevSuffix)};
            """
    }

    override val defaultFileHeaderColumns = listOf(
        "ID",
        "GN_CODE",
        "GN_NAME",
        "MANUFACTURER",
        "CANDIDATE_ID",
        "LAST_ACQUIS_DATE",
        "DESCRIPTION",
        "TYPE",
        "FAMILLE_REPETEUR",
        "ADRESSE_DNS",
        "TYPE_REPETEUR",
        "MODELE_REPETEUR",
        "TYPE_CARTE_SIM",
        "NUM_BTS",
        "RANSHARING",
        "INC_ID",
        "NUM_BSC",
        "TYPE_BSC",
        "NUM_SEMAPHORE",
        "TYPE_RNC",
        "CAPA_AB",
        "NUM_IMEI",
        "NUM_IMMO",
        "NUM_IMSI",
        "PS_BSSAP",
        "PS_MGW",
        "PS_RSQA",
        "ROHS",
        "RS_OP_LEADER",
        "SGTQS",
        "TYPE_TCU",
        "BAND_REP",
        "MOD_LE_MASTER",
        "LEADER_000",
        "PROPRI_TAIRE_000",
        "OP_RATEURS_H_BER_000",
        "NB_TRX900_INST",
        "NB_TRX1800_INST",
        "NOM_POOL_MSC",
        "NET_NAME",
        "ENBID",
        "GNBID",
        "IDENT_SI",
        "BUILD",
        "NAME",
        "NET_STAT",
        "NUM",
        "PALIER",
        "NUM_MSC",
        "NUM_OMCR",
        "RNCID",
        "PS_LOCAL",
        "SIG_PT_CODE",
        "RNCID_NB",
        "PS_NAT",
        "NET_NAME_HR",
        "LPWA_NOM_RES",
        "LORA___CODE_GDN__000",
        "DISTNAME",
        "SBTSID",
        "IDNODE",
        "SWITCH_OFF_DATE",
        "LPWA_DEPL_MEST",
        "LPWA_DEPL_MEXPL",
        "LPWA_DEPL_FN1_2",
        "LPWA_DEPL_DEM",
        "LPWA_DEPL_CODE",
        "DATE_DE_MEST_000",
        "DATE_DE_MEST__DA_000",
        "DATE_DE_MEX__DAS_000",
        "IP_1",
        "IP_2",
        "COLLECT_AREA",
        "CONNECT_TECHNO",
        "ADDRESS_IP",
        "PASSERELLE_DEFAUT",
        "ADRESSE_IP_SWITC_000",
        "IPSEC",
        "DDR_OMC",
        "SGTQS_CODE",
        "ROUTER_TYPE",
        "TAC",
        "MAC_ADDR1",
        "MAC_ADDR2",
        "MCC",
        "MNC",
        "RESP",
        "TR_RESP",
        "MAINT_AER",
        "MAINT_NRJ",
        "MAINT_EV",
        "MAINT_LL",
        "MAINT_SYST",
        "SUPERVIS__000",
        "DATE_DEB_SUP",
        "NUM_SIM",
        "N__VLAN_000",
        "MODE_EXPLOITATIO_000",
        "LL_SUPERVISION_M_000",
        "COMM",
        "LPWA_MODEM_LRRID",
        "LPWA_MODEM_BUILD",
        "LPWA_MODEM_SERIAL_NU",
        "LPWA_MODEM_VERSION",
        "LPWA_MODEM_EDS",
        "LPWA_ROUTEUR_BUILD",
        "LPWA_ROUTEUR_TYPE",
        "LPWA_ROUTEUR_N",
        "LPWA_ROUTEUR_IP_ADR",
        "LPWA_ROUTEUR_DEF_IP",
        "LPWA_ROUTEUR_C_TYPE",
        "LPWA_ROUTEUR_SIMCARD",
        "LPWA_ROUTEUR_P_TYPE",
        "LPWA_ROUTEUR_PREST_N",
        "LPWA_ROUTEUR_PORT_N",
        "LPWA_ROUTEUR_VLAN_N",
        "LPWA_ROUTEUR_EDS",
        "LPWA_ANT_PROVIDER",
        "LPWA_ANT_REF",
        "LPWA_ANT_HBA",
        "LPWA_ANT_SECTOR",
        "LPWA_ANT_ANTX",
        "LPWA_ANT_ANTY",
        "LPWA_ANT_AZIMUT",
        "LPWA_ANT_TILT",
        "LPWA_ANT_BRET_TYP",
        "DIVERSIT__ANTENN_000",
        "GTR_002",
        "NODE_ID"
    )
}
