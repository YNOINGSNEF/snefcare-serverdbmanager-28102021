package fr.snef.dbmanager.orange.import

import fr.snef.dbmanager.orange.OrangeImportDataFile

class TmpCells(fileNames: List<String>, dumpFolderPath: String) : OrangeImportDataFile(dumpFolderPath) {

    companion object {
        const val filePrefix = "NORIA_FLUX_GENERIQUE_CELLULE"

        fun from(fileNames: List<String>, dumpFolderPath: String): TmpCells {
            return TmpCells(
                fileNames.filter { it.startsWith(filePrefix) && !it.contains(TmpCellComplements.complementSuffix) },
                dumpFolderPath
            )
        }
    }

    override val tableName = "TMP_CELL"

    override val createTemporaryTableQuery = """
        CREATE TABLE $tableName (
        	ID INT AUTO_INCREMENT PRIMARY KEY,
        	ID_ORF INT,
        	CELL_TYPE VARCHAR(20),
        	NETWORK_ELEMENT_ID INT,
        	NODE_ID INT,
        	DESCRIPTION TEXT,
        	DISMANTLED VARCHAR(10),
        	OPERATION_PURPOSE VARCHAR(24),
        	DEP_STATUS TEXT,
        	NUM TEXT,
        	CI TEXT,
        	LCID TEXT,
        	ECI TEXT,
        	NCI TEXT,
        	NET_STATUS TEXT,
        	NAME TEXT,
        	NET_NAME TEXT,
        	NET_CODE TEXT,
        	REL_CELL_ID TEXT,
        	IDENT_SI VARCHAR(16),
        	NIDT_NAME TEXT,
        	LAC TEXT,
        	UCID TEXT,
        	COUV_LEVEL TEXT,
        	COMBINEDCELL_000 TEXT,
        	CELLULE_S_COMBI_000 TEXT,
        	DISTNAME TEXT,
        	X5G TEXT,
        	UL5G TEXT,
        	BAND TEXT,
        	COUV TEXT,
        	CLASS TEXT,
        	CARACT TEXT,
        	RS_CELL TEXT,
        	DEP_ANTX TEXT,
        	DEP_ANTY TEXT,
        	DEP_ANTZ TEXT,
        	TYP_ANT TEXT,
        	CARRIER TEXT,
        	AZM_SYNOP TEXT,
        	ORIENT TEXT,
        	SECTEUR TEXT,
        	DATE_EXTINC TEXT,
        	CARRIER_AGREGATI_000 TEXT,
        	MORPHOLOGIE TEXT,
        	RS_OP_HOSTED TEXT,
        	RS_OP_LEADER TEXT,
        	RS_OP_LEADER_4G TEXT,
        	CURL TEXT,
        	SCRAMBLING_CODE TEXT,
        	SECTEUR_OMC TEXT,
        	CELL_Typage TEXT,
        	RS_OP_HOSTED_4G TEXT,
        	TYPE_ACCES TEXT,
        	RS_CELL_4G TEXT,
        	DATE_ALLU TEXT,
        	CATM TEXT,
        	EXT_COVERAGE TEXT,
        	eDRX TEXT,
        	MEST_PREV TEXT,
        	FN8_INIT_DATE TEXT,
        	DATE_DEM_PREV TEXT,
        	DATE_MISE_MAINT TEXT,
        	RET_INT_GR_000 TEXT,
        	DATE_FN5_1 TEXT,
        	DATE_MEST TEXT,
        	DATE_FN8 TEXT,
        	DATE_FNSD TEXT,
        	PCI TEXT,
        	TAC TEXT,
        	MIMO TEXT,
        	MIMO_RS TEXT,
        	RET_INSTAL TEXT,
        	SERIAL_NB_RET TEXT,
        	MCC TEXT,
        	MNC TEXT,
        	TILT_OMC TEXT,
        	SAC TEXT,
        	CID TEXT,
        	DEBIT_HSDPA TEXT,
        	DEBIT_HSUPA TEXT,
        	DEBIT_UMTS TEXT,
        	RAC TEXT,
        	SERVICE_UMTS TEXT,
        	NB_TRX_ACTIF TEXT,
        	SERVICE_GSM TEXT,
        	DDR_OMC TEXT,
        	EARFCN_DL TEXT,
        	UARFCN_DL TEXT,
        	NRARFCN_DL TEXT,
        	HBA TEXT,
        	MARK_REF TEXT,
        	CELL_PLAQUE TEXT,
        	MARK_IDENT TEXT,
        	BLOC TEXT,
        	ZONE_VIE TEXT,
        	CAT_ARCEP TEXT,
        	OBJARCEP TEXT,
        	CODE_MKT_P TEXT,
        	PRIORITE_P TEXT,
        	CODE_MKT_C TEXT,
        	PRIORITE_C TEXT,
        	CODE_MKT_A TEXT,
        	PRIORITE_A TEXT,
        	DEPT TEXT,
        	INSEE TEXT,
        	DCOMIRIS TEXT,
        	VALIDATION TEXT,
        	COMMENT TEXT,
        	OFFRE_PRO_001 TEXT,
        	GTR_001 TEXT,
        	PCPMR_001 TEXT,
        	GP_TRAV TEXT,
        	SAISON TEXT,
        	EVENEMENTIEL TEXT,
        	TRAF_ROAM TEXT,
        	COMM TEXT,
        	NCC TEXT,
        	TG TEXT,
        	USER_DATA TEXT,
        	AICH_POWER TEXT,
        	RNCID TEXT,
        	FREQ_SSB TEXT,
        	ANTENNA_SSB_PATTERN TEXT,
        	SSB_FREQUENCY_AUTO TEXT,
        	AZIMUTH_OPENNING_ANG TEXT,
        	GSCN NUMERIC,
            IS_PREV BOOLEAN NOT NULL
        );
    """

    override val createIndexesQueries = listOf(
        "ALTER TABLE $tableName ADD INDEX index2 (SECTEUR(10), AZM_SYNOP(10), HBA(10));",
        "ALTER TABLE $tableName ADD INDEX index_id_orf (ID_ORF);",
        "ALTER TABLE $tableName ADD INDEX index4 (ID_ORF, IDENT_SI);"
    )

    override val populateTemporaryTableQueries = fileNames.map { fileName ->
        val isPrev = fileName.contains(prevString)
        return@map """
                LOAD DATA LOCAL INFILE '${fullPath(fileName)}'
                INTO TABLE $tableName
                FIELDS TERMINATED BY ';'
                LINES TERMINATED BY '\n'
                IGNORE 1 LINES
                (
                    @CELL_ID,
                    @CELL_TYPE,
                    @NETWORK_ELEMENT_ID,
                    @NODE_ID,
                    @DESCRIPTION,
                    @DISMANTLED,
                    @OPERATION_PURPOSE,
                    @DEP_STATUS,
                    @NUM,
                    @CI,
                    @LCID,
                    @ECI,
                    @NCI,
                    @NET_STATUS,
                    @NAME,
                    @NET_NAME,
                    @NET_CODE,
                    @REL_CELL_ID,
                    @IDENT_SI,
                    @NIDT_NAME,
                    @LAC,
                    @UCID,
                    @COUV_LEVEL,
                    @COMBINEDCELL_000,
                    @CELLULE_S_COMBI_000,
                    @DISTNAME,
                    @X5G,
                    @UL5G,
                    @BAND,
                    @COUV,
                    @CLASS,
                    @CARACT,
                    @RS_CELL,
                    @DEP_ANTX,
                    @DEP_ANTY,
                    @DEP_ANTZ,
                    @TYP_ANT,
                    @CARRIER,
                    @AZM_SYNOP,
                    @ORIENT,
                    @SECTEUR,
                    @DATE_EXTINC,
                    @CARRIER_AGREGATI_000,
                    @MORPHOLOGIE,
                    @RS_OP_HOSTED,
                    @RS_OP_LEADER,
                    @RS_OP_LEADER_4G,
                    @CURL,
                    @SCRAMBLING_CODE,
                    @SECTEUR_OMC,
                    @CELL_Typage,
                    @RS_OP_HOSTED_4G,
                    @TYPE_ACCES,
                    @RS_CELL_4G,
                    @DATE_ALLU,
                    @CATM,
                    @EXT_COVERAGE,
                    @eDRX,
                    @MEST_PREV,
                    @FN8_INIT_DATE,
                    @DATE_DEM_PREV,
                    @DATE_MISE_MAINT,
                    @RET_INT_GR_000,
                    @DATE_FN5_1,
                    @DATE_MEST,
                    @DATE_FN8,
                    @DATE_FNSD,
                    @PCI,
                    @TAC,
                    @MIMO,
                    @MIMO_RS,
                    @RET_INSTAL,
                    @SERIAL_NB_RET,
                    @MCC,
                    @MNC,
                    @TILT_OMC,
                    @SAC,
                    @CID,
                    @DEBIT_HSDPA,
                    @DEBIT_HSUPA,
                    @DEBIT_UMTS,
                    @RAC,
                    @SERVICE_UMTS,
                    @NB_TRX_ACTIF,
                    @SERVICE_GSM,
                    @DDR_OMC,
                    @EARFCN_DL,
                    @UARFCN_DL,
                    @NRARFCN_DL,
                    @HBA,
                    @MARK_REF,
                    @CELL_PLAQUE,
                    @MARK_IDENT,
                    @BLOC,
                    @ZONE_VIE,
                    @CAT_ARCEP,
                    @OBJARCEP,
                    @CODE_MKT_P,
                    @PRIORITE_P,
                    @CODE_MKT_C,
                    @PRIORITE_C,
                    @CODE_MKT_A,
                    @PRIORITE_A,
                    @DEPT,
                    @INSEE,
                    @DCOMIRIS,
                    @VALIDATION,
                    @COMMENT,
                    @OFFRE_PRO_001,
                    @GTR_001,
                    @PCPMR_001,
                    @GP_TRAV,
                    @SAISON,
                    @EVENEMENTIEL,
                    @TRAF_ROAM,
                    @COMM,
                    @NCC,
                    @TG,
                    @USER_DATA,
                    @AICH_POWER,
                    @RNCID,
                    @FREQ_SSB,
                    @ANTENNA_SSB_PATTERN,
                    @SSB_FREQUENCY_AUTO,
                    @AZIMUTH_OPENNING_ANG,
                    @GSCN
                )
                SET
                    ID_ORF = NULLIF(@CELL_ID, ''),
                    CELL_TYPE = NULLIF(@CELL_TYPE, ''),
                    NETWORK_ELEMENT_ID = NULLIF(@NETWORK_ELEMENT_ID, ''),
                    NODE_ID = NULLIF(@NODE_ID, ''),
                    DESCRIPTION = NULLIF(@DESCRIPTION, ''),
                    DISMANTLED = NULLIF(@DISMANTLED, ''),
                    OPERATION_PURPOSE = NULLIF(@OPERATION_PURPOSE, ''),
                    DEP_STATUS = NULLIF(@DEP_STATUS, ''),
                    NUM = NULLIF(@NUM, ''),
                    CI = NULLIF(@CI, ''),
                    LCID = NULLIF(@LCID, ''),
                    ECI = NULLIF(@ECI, ''),
                    NCI = NULLIF(@NCI, ''),
                    NET_STATUS = NULLIF(@NET_STATUS, ''),
                    NAME = NULLIF(@NAME, ''),
                    NET_NAME = NULLIF(@NET_NAME, ''),
                    NET_CODE = NULLIF(@NET_CODE, ''),
                    REL_CELL_ID = NULLIF(@REL_CELL_ID, ''),
                    IDENT_SI = NULLIF(@IDENT_SI, ''),
                    NIDT_NAME = NULLIF(@NIDT_NAME, ''),
                    LAC = NULLIF(@LAC, ''),
                    UCID = NULLIF(@UCID, ''),
                    COUV_LEVEL = NULLIF(@COUV_LEVEL, ''),
                    COMBINEDCELL_000 = NULLIF(@COMBINEDCELL_000, ''),
                    CELLULE_S_COMBI_000 = NULLIF(@CELLULE_S_COMBI_000, ''),
                    DISTNAME = NULLIF(@DISTNAME, ''),
                    X5G = NULLIF(@X5G, ''),
                    UL5G = NULLIF(@UL5G, ''),
                    BAND = NULLIF(@BAND, ''),
                    COUV = NULLIF(@COUV, ''),
                    CLASS = NULLIF(@CLASS, ''),
                    CARACT = NULLIF(@CARACT, ''),
                    RS_CELL = NULLIF(@RS_CELL, ''),
                    DEP_ANTX = NULLIF(@DEP_ANTX, ''),
                    DEP_ANTY = NULLIF(@DEP_ANTY, ''),
                    DEP_ANTZ = NULLIF(@DEP_ANTZ, ''),
                    TYP_ANT = NULLIF(@TYP_ANT, ''),
                    CARRIER = NULLIF(@CARRIER, ''),
                    AZM_SYNOP = NULLIF(@AZM_SYNOP, ''),
                    ORIENT = NULLIF(@ORIENT, ''),
                    SECTEUR = NULLIF(@SECTEUR, ''),
                    DATE_EXTINC = NULLIF(@DATE_EXTINC, ''),
                    CARRIER_AGREGATI_000 = NULLIF(@CARRIER_AGREGATI_000, ''),
                    MORPHOLOGIE = NULLIF(@MORPHOLOGIE, ''),
                    RS_OP_HOSTED = NULLIF(@RS_OP_HOSTED, ''),
                    RS_OP_LEADER = NULLIF(@RS_OP_LEADER, ''),
                    RS_OP_LEADER_4G = NULLIF(@RS_OP_LEADER_4G, ''),
                    CURL = NULLIF(@CURL, ''),
                    SCRAMBLING_CODE = NULLIF(@SCRAMBLING_CODE, ''),
                    SECTEUR_OMC = NULLIF(@SECTEUR_OMC, ''),
                    CELL_Typage = NULLIF(@CELL_Typage, ''),
                    RS_OP_HOSTED_4G = NULLIF(@RS_OP_HOSTED_4G, ''),
                    TYPE_ACCES = NULLIF(@TYPE_ACCES, ''),
                    RS_CELL_4G = NULLIF(@RS_CELL_4G, ''),
                    DATE_ALLU = NULLIF(@DATE_ALLU, ''),
                    CATM = NULLIF(@CATM, ''),
                    EXT_COVERAGE = NULLIF(@EXT_COVERAGE, ''),
                    eDRX = NULLIF(@eDRX, ''),
                    MEST_PREV = NULLIF(@MEST_PREV, ''),
                    FN8_INIT_DATE = NULLIF(@FN8_INIT_DATE, ''),
                    DATE_DEM_PREV = NULLIF(@DATE_DEM_PREV, ''),
                    DATE_MISE_MAINT = NULLIF(@DATE_MISE_MAINT, ''),
                    RET_INT_GR_000 = NULLIF(@RET_INT_GR_000, ''),
                    DATE_FN5_1 = NULLIF(@DATE_FN5_1, ''),
                    DATE_MEST = NULLIF(@DATE_MEST, ''),
                    DATE_FN8 = NULLIF(@DATE_FN8, ''),
                    DATE_FNSD = NULLIF(@DATE_FNSD, ''),
                    PCI = NULLIF(@PCI, ''),
                    TAC = NULLIF(@TAC, ''),
                    MIMO = NULLIF(@MIMO, ''),
                    MIMO_RS = NULLIF(@MIMO_RS, ''),
                    RET_INSTAL = NULLIF(@RET_INSTAL, ''),
                    SERIAL_NB_RET = NULLIF(@SERIAL_NB_RET, ''),
                    MCC = NULLIF(@MCC, ''),
                    MNC = NULLIF(@MNC, ''),
                    TILT_OMC = NULLIF(@TILT_OMC, ''),
                    SAC = NULLIF(@SAC, ''),
                    CID = NULLIF(@CID, ''),
                    DEBIT_HSDPA = NULLIF(@DEBIT_HSDPA, ''),
                    DEBIT_HSUPA = NULLIF(@DEBIT_HSUPA, ''),
                    DEBIT_UMTS = NULLIF(@DEBIT_UMTS, ''),
                    RAC = NULLIF(@RAC, ''),
                    SERVICE_UMTS = NULLIF(@SERVICE_UMTS, ''),
                    NB_TRX_ACTIF = NULLIF(@NB_TRX_ACTIF, ''),
                    SERVICE_GSM = NULLIF(@SERVICE_GSM, ''),
                    DDR_OMC = NULLIF(@DDR_OMC, ''),
                    EARFCN_DL = NULLIF(@EARFCN_DL, ''),
                    UARFCN_DL = NULLIF(@UARFCN_DL, ''),
                    NRARFCN_DL = NULLIF(@NRARFCN_DL, ''),
                    HBA = NULLIF(@HBA, ''),
                    MARK_REF = NULLIF(@MARK_REF, ''),
                    CELL_PLAQUE = NULLIF(@CELL_PLAQUE, ''),
                    MARK_IDENT = NULLIF(@MARK_IDENT, ''),
                    BLOC = NULLIF(@BLOC, ''),
                    ZONE_VIE = NULLIF(@ZONE_VIE, ''),
                    CAT_ARCEP = NULLIF(@CAT_ARCEP, ''),
                    OBJARCEP = NULLIF(@OBJARCEP, ''),
                    CODE_MKT_P = NULLIF(@CODE_MKT_P, ''),
                    PRIORITE_P = NULLIF(@PRIORITE_P, ''),
                    CODE_MKT_C = NULLIF(@CODE_MKT_C, ''),
                    PRIORITE_C = NULLIF(@PRIORITE_C, ''),
                    CODE_MKT_A = NULLIF(@CODE_MKT_A, ''),
                    PRIORITE_A = NULLIF(@PRIORITE_A, ''),
                    DEPT = NULLIF(@DEPT, ''),
                    INSEE = NULLIF(@INSEE, ''),
                    DCOMIRIS = NULLIF(@DCOMIRIS, ''),
                    VALIDATION = NULLIF(@VALIDATION, ''),
                    COMMENT = NULLIF(@COMMENT, ''),
                    OFFRE_PRO_001 = NULLIF(@OFFRE_PRO_001, ''),
                    GTR_001 = NULLIF(@GTR_001, ''),
                    PCPMR_001 = NULLIF(@PCPMR_001, ''),
                    GP_TRAV = NULLIF(@GP_TRAV, ''),
                    SAISON = NULLIF(@SAISON, ''),
                    EVENEMENTIEL = NULLIF(@EVENEMENTIEL, ''),
                    TRAF_ROAM = NULLIF(@TRAF_ROAM, ''),
                    COMM = NULLIF(@COMM, ''),
                    NCC = NULLIF(@NCC, ''),
                    TG = NULLIF(@TG, ''),
                    USER_DATA = NULLIF(@USER_DATA, ''),
                    AICH_POWER = NULLIF(@AICH_POWER, ''),
                    RNCID = NULLIF(@RNCID, ''),
                    FREQ_SSB = NULLIF(@FREQ_SSB, ''),
                    ANTENNA_SSB_PATTERN = NULLIF(@ANTENNA_SSB_PATTERN, ''),
                    SSB_FREQUENCY_AUTO = NULLIF(@SSB_FREQUENCY_AUTO, ''),
                    AZIMUTH_OPENNING_ANG = NULLIF(@AZIMUTH_OPENNING_ANG, ''),
                    GSCN = NULLIF(@GSCN, ''),
                    IS_PREV=$isPrev;
            """
    }
}