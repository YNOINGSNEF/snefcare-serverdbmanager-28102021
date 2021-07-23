package fr.snef.dbmanager.orange.import

import fr.snef.dbmanager.orange.OrangeImportDataFile

class TmpEquipments(fileNames: List<String>, dumpFolderPath: String) : OrangeImportDataFile(dumpFolderPath) {

    companion object {
        private const val filePrefix = "NORIA_FLUX_GENERIQUE_EQPT"

        fun from(fileNames: List<String>, dumpFolderPath: String): TmpEquipments {
            return TmpEquipments(
                fileNames.filter { it.startsWith(filePrefix) },
                dumpFolderPath
            )
        }
    }

    override val tableName = "TMP_EQUIPMENT"

    override val createTemporaryTableQuery = """
        CREATE TABLE $tableName (
            ID INT AUTO_INCREMENT PRIMARY KEY,
            EQPT_ID INT,
            DEVICE_TYPE VARCHAR(40),
            SITE_ID INT,
            DEVICE_ID INT,
            SIGNAL_TYPE VARCHAR(24),
            IS_SPARE VARCHAR(10),
            CONTAINER INT,
            EQPT_CATALOG VARCHAR(80),
            BSS_CAT_KEY VARCHAR(20),
            END_ACTIVE_DATE TIMESTAMP,
            MANUFACTURER VARCHAR(255),
            IS_RECOMMENDED VARCHAR(10),
            CELLULAR_NODE_ID INT,
            EIRP FLOAT,
            PTOT FLOAT,
            ANTENNA_GAIN FLOAT,
            DEVICE_FOOTPRINT TEXT,
            CAPACITE_BSC NUMERIC,
            PUISSANCE_W TEXT,
            UL_CAPA_MAX TEXT(10),
            CLASSE_900 TEXT(20),
            DL_CAPA_MAX TEXT(10),
            GAMME_FREQUENCE_2_MHZ TEXT(5),
            PUISSANCE_DBM TEXT,
            BANDE_DE_FREQUENCE_MHZ TEXT(10),
            FOURNISSEUR TEXT(5),
            GAMME_FREQUENCE_1_MHZ TEXT(5),
            TILT_ELECTRIQUE_900 TEXT(12),
            PUISSANCE_MIN_900_DBM TEXT,
            PUIS_W FLOAT,
            TYPE_ABREGE TEXT(255),
            CONNECTEUR TEXT(12),
            PUISSANCE_MAX_900_DBM TEXT,
            FREQUENCE_MHZ TEXT(255),
            NOMBRE_DE_TX_RX NUMERIC,
            PAS_DE_PUISSANCE TEXT,
            MAXIMUM_INPUT_POWER TEXT(10),
            TYPE_SELECTIF_LARGE_BANDE TEXT(20),
            BANDE_DE_FREQ_SENS_MONTANT_MHZ TEXT(20),
            FREQUENCY_RANGE TEXT(10),
            BANDE_DE_FREQ_SENS_DESCENDANT_MHZ TEXT(20),
            BANDE_DE_FREQUENCE_1800_MHZ TEXT(12),
            IMPEDANCE TEXT(10),
            FILTRE_MHZ TEXT,
            CLASSE_NR3500 TEXT(20),
            PUISSANCE_DE_SORTIE_MAX TEXT,
            PUISSANCE_DE_SORTIE_MAX_DBM TEXT,
            TYPE TEXT(12),
            PUISSANCE_MIN_1800_DBM TEXT,
            PUISSANCE_MAX_1800_DBM TEXT,
            PUISSANCE_NR3500_W TEXT,
            BANDE_DE_FREQUENCE_900_MHZ TEXT(12),
            PUISSANCE_2200_W TEXT,
            TYPOLOGIE_CONSTRUCTEUR TEXT(20),
            BANDE_DE_FREQUENCE_2600_MHZ TEXT(12),
            CLASSE_1800 TEXT(12),
            TILT_ELECTRIQUE_NR3500 NUMERIC,
            BANDE_DE_FREQUENCE_NR3500_MHZ TEXT(12),
            PUISSANCE_2600_W TEXT,
            BANDE_DE_FREQUENCE_800_MHZ TEXT(20),
            PUISSANCE_1800_W TEXT,
            TILT_ELECTRIQUE_1800 TEXT(12),
            PUISSANCE_900_W TEXT,
            PUISSANCE_800_W TEXT,
            UTILISATION_MACRO_MICRO_INDOOR_TUNNEL TEXT(12),
            CLASSE_2200 TEXT(20),
            PUISSANCE_700_W TEXT,
            BANDE_DE_FREQUENCE_2200_MHZ TEXT(12),
            TILT_ELECTRIQUE_2200 TEXT(12),
            REF_PARCELL TEXT(80),
            CLASSE_2600 TEXT(20),
            TILT_ELECTRIQUE_2600 TEXT(12),
            TILT_ELECTRIQUE_800 TEXT(12),
            CLASSE_800 TEXT(12),
            CLASSE_700 TEXT(12),
            CLASSE_LTE700 TEXT(12),
            TILT_ELECTRIQUE_700 TEXT(12),
            BANDE_DE_FREQUENCE_700_MHZ TEXT(12),
            CLASSE_LTE3500 TEXT(20),
            PUISSANCE_3500_W TEXT,
            TILT_ELECTRIQUE_3500 TEXT(12),
            BANDE_DE_FREQUENCE_3500_MHZ TEXT(12),
            IS_PREV BOOLEAN NOT NULL
        );
    """

    override val createIndexesQueries = listOf(
        "ALTER TABLE $tableName ADD INDEX index2 (CELLULAR_NODE_ID, EQPT_ID);",
        "ALTER TABLE $tableName ADD INDEX index3 (DEVICE_TYPE);",
        "ALTER TABLE $tableName ADD INDEX index4 (SITE_ID);",
        "ALTER TABLE $tableName ADD INDEX index5 (CELLULAR_NODE_ID, SITE_ID);"
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
                    @ID,
                    @DEVICE_TYPE,
                    @COMMENT,
                    @SITE_ID,
                    @DEVICE_ID,
                    @SIGNAL_TYPE,
                    @IS_SPARE,
                    @CONTAINER,
                    @EQPT_CATALOG,
                    @BSS_CAT_KEY,
                    @END_ACTIVE_DATE,
                    @MANUFACTURER,
                    @IS_RECOMMENDED,
                    @CELLULAR_NODE_ID,
                    @COMPUTATION_TS,
                    @EIRP,
                    @PTOT,
                    @POWER_BALANCING,
                    @TRANSMISSION_LINE_LOSS,
                    @ANTENNA_GAIN,
                    @DEVICE_FOOTPRINT,
                    @ATTENUATION_DB,
                    @CAPACITE_BSC,
                    @GAIN_900_DB,
                    @GAIN_GSM900_DBI,
                    @GAIN_MINIMUM_DB,
                    @PERTE_EMISSION_900_DB,
                    @PERTE_EMISSION_DB,
                    @PUISSANCE_W,
                    @UL_CAPA_MAX,
                    @ATTENUATION_CATALOGUE_1800_DB,
                    @CLASSE_900,
                    @DL_CAPA_MAX,
                    @GAIN_1800_DB,
                    @GAIN_MAXIMUM_DB,
                    @GAMME_FREQUENCE_2_MHZ,
                    @PERTE_INSERTION_800_DB,
                    @PERTE_RECEPTION_900_DB,
                    @PERTE_RECEPTION_DB,
                    @PERTES_INSERTION_DB,
                    @PUISSANCE_DBM,
                    @ATTENUATION_CATALOGUE_2200_DB,
                    @BANDE_DE_FREQUENCE_MHZ,
                    @FOURNISSEUR,
                    @GAMME_FREQUENCE_1_MHZ,
                    @PERTE_DUPLEXEUR_900_DB,
                    @TILT_ELECTRIQUE_900,
                    @ATTENUATION_CATALOGUE_900_DB,
                    @OUVERTURE_HORIZONTALE_900,
                    @PERTE_DE_COUPLAGE_900_DB,
                    @PERTE_INSERTION_1800_DB,
                    @ATTENUATION_CATALOGUE_2600_DB,
                    @BBU_WSP_EQUIPABLE_CAPACITE_MAX,
                    @OUVERTURE_VERTICALE_900,
                    @PERTE_DE_COUPLAGE_1800_DB,
                    @PERTE_DUPLEXEUR_1800_DB,
                    @PROFONDEUR_MM,
                    @PUISSANCE_MIN_900_DBM,
                    @PUIS_W,
                    @TYPE_ABREGE,
                    @ATTENUATION_CATALOGUE_800_DB,
                    @CARRIER_EQUIPABLE_CAPACITE_MAX,
                    @CONNECTEUR,
                    @PERTE_INSERTION_2600_DB,
                    @PERTE_PAR_100_M_900_DB,
                    @PUISSANCE_MAX_900_DBM,
                    @ATTENUATION_CATALOGUE_700_DB,
                    @FREQUENCE_MHZ,
                    @LONGUEUR,
                    @NOMBRE_DE_TX_RX,
                    @PA_EQUIPABLE_CAPACITE_MAX,
                    @PAS_DE_PUISSANCE,
                    @DIAMETRE_EXTERNE_MM,
                    @MAXIMUM_INPUT_POWER,
                    @PERTE_DUPLEXEUR_800_DB,
                    @PERTE_DUPLEXEUR_INTEGRE_1800_DB,
                    @TYPE_SELECTIF_LARGE_BANDE,
                    @BANDE_DE_FREQ_SENS_MONTANT_MHZ,
                    @DUPLEXEUR_INTEGRE_O_N,
                    @FREQUENCY_RANGE,
                    @GAIN_2200_DB,
                    @PERTE_A_VERS_B_900_DB,
                    @PERTE_EN_SORTIE,
                    @RAYON_EXTERNE_DE_COURBURE,
                    @BANDE_DE_FREQ_SENS_DESCENDANT_MHZ,
                    @BANDE_DE_FREQUENCE_1800_MHZ,
                    @IMPEDANCE,
                    @LARGEUR_MM,
                    @PERTE_A_VERS_C_900_DB,
                    @PERTE_DUPLEXEUR_2200_DB,
                    @POIDS_ANTENNE_SEULE_KG,
                    @POIDS_KG,
                    @FILTRE_MHZ,
                    @PERTE_DUPLEXEUR_INTEGRE_900_DB,
                    @PERTE_RECEPTION_1800_DB,
                    @RESEAU,
                    @SENSIBILITE_900_DBM,
                    @CLASSE_NR3500,
                    @PERTE_A_VERS_B_1800_DB,
                    @PERTE_DUPLEXEUR_2600_DB,
                    @PERTE_EMISSION_1800_DB,
                    @PERTE_PAR_100_M_1800_DB,
                    @PUISSANCE_DE_SORTIE_MAX,
                    @PUISSANCE_DE_SORTIE_MAX_DBM,
                    @TYPE,
                    @GAIN_2600_DB,
                    @OUVERTURE_HORIZONTALE_NR3500,
                    @PERTE_A_VERS_C_1800_DB,
                    @PERTE_EMISSION_2200_DB,
                    @PERTE_PAR_100_M_2200_DB,
                    @PUISSANCE_MIN_1800_DBM,
                    @COUPLAGE_A_95_900,
                    @OUVERTURE_VERTICALE_NR3500,
                    @PUISSANCE_MAX_1800_DBM,
                    @RAPPORT_AV_AR_900_DB,
                    @COUPLAGE_A_95_2200,
                    @GAIN_NR3500_DBI,
                    @GAIN_800_DB,
                    @ISOLATION_ENTRE_BANDE_900_2200_DB,
                    @PERTE_A_VERS_C_2200_DB,
                    @PERTE_EMISSION_2600_DB,
                    @PERTE_RECEPTION_2200_DB,
                    @SENSIBILITE_1800_DBM,
                    @COUPLAGE_A_95_1800,
                    @ISOLATION_ENTRE_BANDE_1800_2200_DB,
                    @PERTE_A_VERS_B_2200_DB,
                    @PRISE_AU_VENT_M2,
                    @PUISSANCE_NR3500_W,
                    @HAUTEUR_MM,
                    @ISOLATION_ENTRE_BANDE_1800_800_DB,
                    @PERTE_A_VERS_C_800_DB,
                    @PERTE_DUPLEXEUR_700_DB,
                    @PERTE_EMISSION_700_DB,
                    @PERTE_EMISSION_800_DB,
                    @BANDE_DE_FREQUENCE_900_MHZ,
                    @ISOLATION_ENTRE_ACCES_900_DB,
                    @PERTE_A_VERS_B_800_DB,
                    @PERTE_PAR_100_M_2600_DB,
                    @ISOLATION_ENTRE_ACCES_NR3500_DB,
                    @PUISSANCE_2200_W,
                    @TYPOLOGIE_CONSTRUCTEUR,
                    @BANDE_DE_FREQUENCE_2600_MHZ,
                    @ISOLATION_ENTRE_BANDE_900_1800_DB,
                    @PERTE_A_VERS_C_2600_DB,
                    @PERTE_PAR_100_M_700_DB,
                    @ROS_900,
                    @CLASSE_1800,
                    @PERTE_A_VERS_B_2600_DB,
                    @PERTE_PAR_100_M_800_DB,
                    @TILT_ELECTRIQUE_NR3500,
                    @BANDE_DE_FREQUENCE_NR3500_MHZ_1,
                    @PERTE_RECEPTION_700_DB,
                    @TYPE_DE_COUPLAGE,
                    @UTILISATION,
                    @GAIN_GSM1800_DBI,
                    @PERTE_INSERTION_900_DB,
                    @PUISSANCE_2600_W,
                    @X_1_X,
                    @X1_X,
                    @BANDE_DE_FREQUENCE_800_MHZ,
                    @OUVERTURE_HORIZONTALE_1800,
                    @PERTE_INSERTION_700_DB,
                    @OUVERTURE_VERTICALE_1800,
                    @PUISSANCE_1800_W,
                    @ISOLATION_ENTRE_BANDE_1800_2600_DB,
                    @PERTE_INSERTION_2100_DB,
                    @ISOLATION_ENTRE_BANDE_2200_2600_DB,
                    @ISOLATION_ENTRE_BANDE_900_2600_DB,
                    @PERTE_INSERTION_2200_DB,
                    @TILT_ELECTRIQUE_1800,
                    @PUISSANCE_900_W,
                    @ISOLATION_ENTRE_BANDE_900_800_DB,
                    @PERTE_INSERTION_3500_DB,
                    @ROS_1800,
                    @COMMENTAIRE,
                    @PUISSANCE_800_W,
                    @RAPPORT_AV_AR_1800_DB,
                    @ISOLATION_ENTRE_ACCES_1800_DB,
                    @UTILISATION_MACRO_MICRO_INDOOR_TUNNEL,
                    @GAIN_UMTS2200_DBI,
                    @CLASSE_2200,
                    @OUVERTURE_HORIZONTALE_2200,
                    @PUISSANCE_700_W,
                    @OUVERTURE_VERTICALE_2200,
                    @BANDE_DE_FREQUENCE_2200_MHZ,
                    @ISOLATION_ENTRE_ACCES_2200_DB,
                    @ROS_2200,
                    @RAPPORT_AV_AR_2200_DB,
                    @TILT_ELECTRIQUE_2200,
                    @REF_PARCELL,
                    @GAIN_LTE2600_DBI,
                    @CLASSE_2600,
                    @OUVERTURE_VERTICALE_2600,
                    @OUVERTURE_HORIZONTALE_2600,
                    @TILT_ELECTRIQUE_2600,
                    @ISOLATION_ENTRE_ACCES_2600_DB,
                    @ROS_2600,
                    @RAPPORT_AV_AR_2600_DB,
                    @GAIN_LTE800_DBI,
                    @RAPPORT_AV_AR_800_DB,
                    @ROS_800,
                    @ISOLATION_ENTRE_ACCES_800_DB,
                    @OUVERTURE_HORIZONTALE_800,
                    @TILT_ELECTRIQUE_800,
                    @OUVERTURE_VERTICALE_800,
                    @CLASSE_800,
                    @GAIN_LTE2100_DBI,
                    @OUVERTURE_HORIZONTALE_700,
                    @OUVERTURE_VERTICALE_700,
                    @CLASSE_700,
                    @GAIN_UMTS900_DBI,
                    @GAIN_700_DB,
                    @CLASSE_LTE700,
                    @GAIN_LTE700_DBI,
                    @TILT_ELECTRIQUE_700,
                    @BANDE_DE_FREQUENCE_700_MHZ,
                    @ISOLATION_ENTRE_ACCES_700_DB,
                    @ROS_700,
                    @RAPPORT_AV_AR_700_DB,
                    @GAIN_LTE1800_DBI,
                    @GAIN_LTE3500_DBI,
                    @CLASSE_LTE3500,
                    @OUVERTURE_HORIZONTALE_3500,
                    @OUVERTURE_VERTICALE_3500,
                    @PUISSANCE_3500_W,
                    @TILT_ELECTRIQUE_3500,
                    @BANDE_DE_FREQUENCE_3500_MHZ,
                    @ISOLATION_ENTRE_ACCES_3500_DB,
                    @RAPPORT_AV_AR_3500_DB,
                    @ROS_3500,
                    @GAIN_NR2100_DBI,
                    @PERTE_DUPLEXEUR_2100_DB,
                    @PERTE_D_INSERTION_DB,
                    @PUISSANCE_NR2100_W,
                    @PUISSANCE_LTE2100_W,
                    @BANDE_DE_FREQUENCE_NR3500_MHZ,
                    @PUISSANCE_LTE1800_W,
                    @TILT_ELECTRIQUE_RN3500_,
                    @RAPPORT_AV_AR_NR3500_DB,
                    @BANDE_PASSANTE_MHZ,
                    @GAIN_2100_DB,
                    @ROS_NR3500,
                    @PUISSANCE_UMTS2200_W
                )
                SET
                    EQPT_ID = NULLIF(@ID, ''),
                    DEVICE_TYPE = NULLIF(@DEVICE_TYPE, ''),
                    SITE_ID = NULLIF(@SITE_ID, ''),
                    DEVICE_ID = NULLIF(@DEVICE_ID, ''),
                    SIGNAL_TYPE = NULLIF(@SIGNAL_TYPE, ''),
                    IS_SPARE = NULLIF(@IS_SPARE, ''),
                    CONTAINER = NULLIF(@CONTAINER, ''),
                    EQPT_CATALOG = NULLIF(@EQPT_CATALOG, ''),
                    BSS_CAT_KEY = NULLIF(@BSS_CAT_KEY, ''),
                    END_ACTIVE_DATE = NULLIF(@END_ACTIVE_DATE, ''),
                    MANUFACTURER = NULLIF(@MANUFACTURER, ''),
                    IS_RECOMMENDED = NULLIF(@IS_RECOMMENDED, ''),
                    CELLULAR_NODE_ID = NULLIF(@CELLULAR_NODE_ID, ''),
                    EIRP = NULLIF(@EIRP, ''),
                    PTOT = NULLIF(@PTOT, ''),
                    ANTENNA_GAIN = NULLIF(@ANTENNA_GAIN, ''),
                    DEVICE_FOOTPRINT = NULLIF(@DEVICE_FOOTPRINT, ''),
                    CAPACITE_BSC = NULLIF(@CAPACITE_BSC, ''),
                    PUISSANCE_W = NULLIF(@PUISSANCE_W, ''),
                    UL_CAPA_MAX = NULLIF(@UL_CAPA_MAX, ''),
                    CLASSE_900 = NULLIF(@CLASSE_900, ''),
                    DL_CAPA_MAX = NULLIF(@DL_CAPA_MAX, ''),
                    GAMME_FREQUENCE_2_MHZ = NULLIF(@GAMME_FREQUENCE_2_MHZ, ''),
                    PUISSANCE_DBM = NULLIF(@PUISSANCE_DBM, ''),
                    BANDE_DE_FREQUENCE_MHZ = NULLIF(@BANDE_DE_FREQUENCE_MHZ, ''),
                    FOURNISSEUR = NULLIF(@FOURNISSEUR, ''),
                    GAMME_FREQUENCE_1_MHZ = NULLIF(@GAMME_FREQUENCE_1_MHZ, ''),
                    TILT_ELECTRIQUE_900 = NULLIF(@TILT_ELECTRIQUE_900, ''),
                    PUISSANCE_MIN_900_DBM = NULLIF(@PUISSANCE_MIN_900_DBM, ''),
                    PUIS_W = NULLIF(@PUIS_W, ''),
                    TYPE_ABREGE = NULLIF(@TYPE_ABREGE, ''),
                    CONNECTEUR = NULLIF(@CONNECTEUR, ''),
                    PUISSANCE_MAX_900_DBM = NULLIF(@PUISSANCE_MAX_900_DBM, ''),
                    FREQUENCE_MHZ = NULLIF(@FREQUENCE_MHZ, ''),
                    NOMBRE_DE_TX_RX = NULLIF(@NOMBRE_DE_TX_RX, ''),
                    PAS_DE_PUISSANCE = NULLIF(@PAS_DE_PUISSANCE, ''),
                    MAXIMUM_INPUT_POWER = NULLIF(@MAXIMUM_INPUT_POWER, ''),
                    TYPE_SELECTIF_LARGE_BANDE = NULLIF(@TYPE_SELECTIF_LARGE_BANDE, ''),
                    BANDE_DE_FREQ_SENS_MONTANT_MHZ = NULLIF(@BANDE_DE_FREQ_SENS_MONTANT_MHZ, ''),
                    FREQUENCY_RANGE = NULLIF(@FREQUENCY_RANGE, ''),
                    BANDE_DE_FREQ_SENS_DESCENDANT_MHZ = NULLIF(@BANDE_DE_FREQ_SENS_DESCENDANT_MHZ, ''),
                    BANDE_DE_FREQUENCE_1800_MHZ = NULLIF(@BANDE_DE_FREQUENCE_1800_MHZ, ''),
                    IMPEDANCE = NULLIF(@IMPEDANCE, ''),
                    FILTRE_MHZ = NULLIF(@FILTRE_MHZ, ''),
                    CLASSE_NR3500 = NULLIF(@CLASSE_NR3500, ''),
                    PUISSANCE_DE_SORTIE_MAX = NULLIF(@PUISSANCE_DE_SORTIE_MAX, ''),
                    PUISSANCE_DE_SORTIE_MAX_DBM = NULLIF(@PUISSANCE_DE_SORTIE_MAX_DBM, ''),
                    TYPE = NULLIF(@TYPE, ''),
                    PUISSANCE_MIN_1800_DBM = NULLIF(@PUISSANCE_MIN_1800_DBM, ''),
                    PUISSANCE_MAX_1800_DBM = NULLIF(@PUISSANCE_MAX_1800_DBM, ''),
                    PUISSANCE_NR3500_W = NULLIF(@PUISSANCE_NR3500_W, ''),
                    BANDE_DE_FREQUENCE_900_MHZ = NULLIF(@BANDE_DE_FREQUENCE_900_MHZ, ''),
                    PUISSANCE_2200_W = NULLIF(@PUISSANCE_2200_W, ''),
                    TYPOLOGIE_CONSTRUCTEUR = NULLIF(@TYPOLOGIE_CONSTRUCTEUR, ''),
                    BANDE_DE_FREQUENCE_2600_MHZ = NULLIF(@BANDE_DE_FREQUENCE_2600_MHZ, ''),
                    CLASSE_1800 = NULLIF(@CLASSE_1800, ''),
                    TILT_ELECTRIQUE_NR3500 = NULLIF(@TILT_ELECTRIQUE_NR3500, ''),
                    BANDE_DE_FREQUENCE_NR3500_MHZ = NULLIF(@BANDE_DE_FREQUENCE_NR3500_MHZ, ''),
                    PUISSANCE_2600_W = NULLIF(@PUISSANCE_2600_W, ''),
                    BANDE_DE_FREQUENCE_800_MHZ = NULLIF(@BANDE_DE_FREQUENCE_800_MHZ, ''),
                    PUISSANCE_1800_W = NULLIF(@PUISSANCE_1800_W, ''),
                    TILT_ELECTRIQUE_1800 = NULLIF(@TILT_ELECTRIQUE_1800, ''),
                    PUISSANCE_900_W = NULLIF(@PUISSANCE_900_W, ''),
                    PUISSANCE_800_W = NULLIF(@PUISSANCE_800_W, ''),
                    UTILISATION_MACRO_MICRO_INDOOR_TUNNEL = NULLIF(@UTILISATION_MACRO_MICRO_INDOOR_TUNNEL, ''),
                    CLASSE_2200 = NULLIF(@CLASSE_2200, ''),
                    PUISSANCE_700_W = NULLIF(@PUISSANCE_700_W, ''),
                    BANDE_DE_FREQUENCE_2200_MHZ = NULLIF(@BANDE_DE_FREQUENCE_2200_MHZ, ''),
                    TILT_ELECTRIQUE_2200 = NULLIF(@TILT_ELECTRIQUE_2200, ''),
                    REF_PARCELL = NULLIF(@REF_PARCELL, ''),
                    CLASSE_2600 = NULLIF(@CLASSE_2600, ''),
                    TILT_ELECTRIQUE_2600 = NULLIF(@TILT_ELECTRIQUE_2600, ''),
                    TILT_ELECTRIQUE_800 = NULLIF(@TILT_ELECTRIQUE_800, ''),
                    CLASSE_800 = NULLIF(@CLASSE_800, ''),
                    CLASSE_700 = NULLIF(@CLASSE_700, ''),
                    CLASSE_LTE700 = NULLIF(@CLASSE_LTE700, ''),
                    TILT_ELECTRIQUE_700 = NULLIF(@TILT_ELECTRIQUE_700, ''),
                    BANDE_DE_FREQUENCE_700_MHZ = NULLIF(@BANDE_DE_FREQUENCE_700_MHZ, ''),
                    CLASSE_LTE3500 = NULLIF(@CLASSE_LTE3500, ''),
                    PUISSANCE_3500_W = NULLIF(@PUISSANCE_3500_W, ''),
                    TILT_ELECTRIQUE_3500 = NULLIF(@TILT_ELECTRIQUE_3500, ''),
                    BANDE_DE_FREQUENCE_3500_MHZ = NULLIF(@BANDE_DE_FREQUENCE_3500_MHZ, ''),
                    IS_PREV=$isPrev;
            """
    }
}