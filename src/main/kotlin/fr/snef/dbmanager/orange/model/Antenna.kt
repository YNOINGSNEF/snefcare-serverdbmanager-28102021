package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.orange.OrangeDataFile
import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement

class Antenna(private val isPrev: Boolean, filename: String) : OrangeDataFile(filename) {

    companion object {
        const val tableName = "ANTENNA"
        val tableHeader = listOf(
            "id",
            "sector_number",
            "azimuth",
            "reference",
            "manufacturer",
            "is_installed",
            "hba",
            "site_id"
        )

        const val filePrefix = "NORIA_FLUX_GENERIQUE_EQPT"
        private const val prevString = "PREV"

        fun from(fileName: String) = fileName
            .takeIf { it.startsWith(filePrefix) }
            ?.let { Antenna(it.contains(prevString), it) }

        fun isValid(deviceType: String) = deviceType.toLowerCase().contains("antenne")
    }

    override val fileHeader = Header::class.java

    override val tableName = Antenna.tableName
    override val tableHeader = Antenna.tableHeader

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE id = id"

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        if (!isValid(record[Header.DEVICE_TYPE])) {
            // Equipment file also contains bays & other RF equipments, but we only need antennas
            throw InvalidParameterException("Ignoring entry as not containing an antenna")
        }

        var index = 0
        stmt.setInt(++index, record[Header.ID].toInt())
        stmt.setInt(++index, 0) // Set by AntennaDetails (sector number)
        stmt.setInt(++index, -1) // Set by AntennaDetails (antenna azimuth)
        stmt.setString(++index, record[Header.EQPT_CATALOG])
        stmt.setString(++index, record[Header.MANUFACTURER])
        stmt.setBoolean(++index, !isPrev)
        stmt.setFloat(++index, 0f) // Set by AntennaDetails (antenna base height)
        stmt.setString(++index, record[Header.SITE_ID])
    }

    enum class Header {
        ID,
        DEVICE_TYPE,
        COMMENT,
        SITE_ID,
        DEVICE_ID,
        SIGNAL_TYPE,
        IS_SPARE,
        CONTAINER,
        EQPT_CATALOG,
        BSS_CAT_KEY,
        END_ACTIVE_DATE,
        MANUFACTURER,
        IS_RECOMMENDED,
        CELLULAR_NODE_ID,
        COMPUTATION_TS,
        EIRP,
        PTOT,
        POWER_BALANCING,
        TRANSMISSION_LINE_LOSS,
        ANTENNA_GAIN,
        DEVICE_FOOTPRINT,
        ATTENUATION_DB,
        CAPACITE_BSC,
        GAIN_900_DB,
        GAIN_GSM900_DBI,
        GAIN_MINIMUM_DB,
        PERTE_EMISSION_900_DB,
        PERTE_EMISSION_DB,
        PUISSANCE_W,
        UL_CAPA_MAX,
        ATTENUATION_CATALOGUE_1800_DB,
        CLASSE_900,
        DL_CAPA_MAX,
        GAIN_1800_DB,
        GAIN_MAXIMUM_DB,
        GAMME_FREQUENCE_2_MHZ,
        PERTE_INSERTION_800_DB,
        PERTE_RECEPTION_900_DB,
        PERTE_RECEPTION_DB,
        PERTES_INSERTION_DB,
        PUISSANCE_DBM,
        ATTENUATION_CATALOGUE_2200_DB,
        BANDE_DE_FREQUENCE_MHZ,
        FOURNISSEUR,
        GAMME_FREQUENCE_1_MHZ,
        PERTE_DUPLEXEUR_900_DB,
        TILT_ELECTRIQUE_900,
        ATTENUATION_CATALOGUE_900_DB,
        OUVERTURE_HORIZONTALE_900,
        PERTE_DE_COUPLAGE_900_DB,
        PERTE_INSERTION_1800_DB,
        ATTENUATION_CATALOGUE_2600_DB,
        BBU_WSP_EQUIPABLE_CAPACITE_MAX,
        OUVERTURE_VERTICALE_900,
        PERTE_DE_COUPLAGE_1800_DB,
        PERTE_DUPLEXEUR_1800_DB,
        PROFONDEUR_MM,
        PUISSANCE_MIN_900_DBM,
        PUIS_W,
        TYPE_ABREGE,
        ATTENUATION_CATALOGUE_800_DB,
        CARRIER_EQUIPABLE_CAPACITE_MAX,
        CONNECTEUR,
        PERTE_INSERTION_2600_DB,
        PERTE_PAR_100_M_900_DB,
        PUISSANCE_MAX_900_DBM,
        ATTENUATION_CATALOGUE_700_DB,
        FREQUENCE_MHZ,
        LONGUEUR,
        NOMBRE_DE_TX_RX,
        PA_EQUIPABLE_CAPACITE_MAX,
        PAS_DE_PUISSANCE,
        DIAMETRE_EXTERNE_MM,
        MAXIMUM_INPUT_POWER,
        PERTE_DUPLEXEUR_800_DB,
        PERTE_DUPLEXEUR_INTEGRE_1800_DB,
        TYPE_SELECTIF_LARGE_BANDE,
        BANDE_DE_FREQ_SENS_MONTANT_MHZ,
        DUPLEXEUR_INTEGRE_O_N,
        FREQUENCY_RANGE,
        GAIN_2200_DB,
        PERTE_A_VERS_B_900_DB,
        PERTE_EN_SORTIE,
        RAYON_EXTERNE_DE_COURBURE,
        BANDE_DE_FREQ_SENS_DESCENDANT_MHZ,
        BANDE_DE_FREQUENCE_1800_MHZ,
        IMPEDANCE,
        LARGEUR_MM,
        PERTE_A_VERS_C_900_DB,
        PERTE_DUPLEXEUR_2200_DB,
        POIDS_ANTENNE_SEULE_KG,
        POIDS_KG,
        FILTRE_MHZ,
        PERTE_DUPLEXEUR_INTEGRE_900_DB,
        PERTE_RECEPTION_1800_DB,
        RESEAU,
        SENSIBILITE_900_DBM,
        CLASSE_NR3500,
        PERTE_A_VERS_B_1800_DB,
        PERTE_DUPLEXEUR_2600_DB,
        PERTE_EMISSION_1800_DB,
        PERTE_PAR_100_M_1800_DB,
        PUISSANCE_DE_SORTIE_MAX,
        PUISSANCE_DE_SORTIE_MAX_DBM,
        TYPE,
        GAIN_2600_DB,
        OUVERTURE_HORIZONTALE_NR3500,
        PERTE_A_VERS_C_1800_DB,
        PERTE_EMISSION_2200_DB,
        PERTE_PAR_100_M_2200_DB,
        PUISSANCE_MIN_1800_DBM,
        COUPLAGE_A_95_900,
        OUVERTURE_VERTICALE_NR3500,
        PUISSANCE_MAX_1800_DBM,
        RAPPORT_AV_AR_900_DB,
        COUPLAGE_A_95_2200,
        GAIN_NR3500_DBI,
        GAIN_800_DB,
        ISOLATION_ENTRE_BANDE_900_2200_DB,
        PERTE_A_VERS_C_2200_DB,
        PERTE_EMISSION_2600_DB,
        PERTE_RECEPTION_2200_DB,
        SENSIBILITE_1800_DBM,
        COUPLAGE_A_95_1800,
        ISOLATION_ENTRE_BANDE_1800_2200_DB,
        PERTE_A_VERS_B_2200_DB,
        PRISE_AU_VENT_M2,
        PUISSANCE_NR3500_W,
        HAUTEUR_MM,
        ISOLATION_ENTRE_BANDE_1800_800_DB,
        PERTE_A_VERS_C_800_DB,
        PERTE_DUPLEXEUR_700_DB,
        PERTE_EMISSION_700_DB,
        PERTE_EMISSION_800_DB,
        BANDE_DE_FREQUENCE_900_MHZ,
        ISOLATION_ENTRE_ACCES_900_DB,
        PERTE_A_VERS_B_800_DB,
        PERTE_PAR_100_M_2600_DB,
        ISOLATION_ENTRE_ACCES_NR3500_DB,
        PUISSANCE_2200_W,
        TYPOLOGIE_CONSTRUCTEUR,
        BANDE_DE_FREQUENCE_2600_MHZ,
        ISOLATION_ENTRE_BANDE_900_1800_DB,
        PERTE_A_VERS_C_2600_DB,
        PERTE_PAR_100_M_700_DB,
        ROS_900,
        CLASSE_1800,
        PERTE_A_VERS_B_2600_DB,
        PERTE_PAR_100_M_800_DB,
        TILT_ELECTRIQUE_NR3500,
        BANDE_DE_FREQUENCE_NR3500_MHZ_1,
        PERTE_RECEPTION_700_DB,
        TYPE_DE_COUPLAGE,
        UTILISATION,
        GAIN_GSM1800_DBI,
        PERTE_INSERTION_900_DB,
        PUISSANCE_2600_W,
        X_1_X,
        X1_X,
        BANDE_DE_FREQUENCE_800_MHZ,
        OUVERTURE_HORIZONTALE_1800,
        PERTE_INSERTION_700_DB,
        OUVERTURE_VERTICALE_1800,
        PUISSANCE_1800_W,
        ISOLATION_ENTRE_BANDE_1800_2600_DB,
        PERTE_INSERTION_2100_DB,
        ISOLATION_ENTRE_BANDE_2200_2600_DB,
        ISOLATION_ENTRE_BANDE_900_2600_DB,
        PERTE_INSERTION_2200_DB,
        TILT_ELECTRIQUE_1800,
        PUISSANCE_900_W,
        ISOLATION_ENTRE_BANDE_900_800_DB,
        PERTE_INSERTION_3500_DB,
        ROS_1800,
        COMMENTAIRE,
        PUISSANCE_800_W,
        RAPPORT_AV_AR_1800_DB,
        ISOLATION_ENTRE_ACCES_1800_DB,
        UTILISATION_MACRO_MICRO_INDOOR_TUNNEL,
        GAIN_UMTS2200_DBI,
        CLASSE_2200,
        OUVERTURE_HORIZONTALE_2200,
        PUISSANCE_700_W,
        OUVERTURE_VERTICALE_2200,
        BANDE_DE_FREQUENCE_2200_MHZ,
        ISOLATION_ENTRE_ACCES_2200_DB,
        ROS_2200,
        RAPPORT_AV_AR_2200_DB,
        TILT_ELECTRIQUE_2200,
        REF_PARCELL,
        GAIN_LTE2600_DBI,
        CLASSE_2600,
        OUVERTURE_VERTICALE_2600,
        OUVERTURE_HORIZONTALE_2600,
        TILT_ELECTRIQUE_2600,
        ISOLATION_ENTRE_ACCES_2600_DB,
        ROS_2600,
        RAPPORT_AV_AR_2600_DB,
        GAIN_LTE800_DBI,
        RAPPORT_AV_AR_800_DB,
        ROS_800,
        ISOLATION_ENTRE_ACCES_800_DB,
        OUVERTURE_HORIZONTALE_800,
        TILT_ELECTRIQUE_800,
        OUVERTURE_VERTICALE_800,
        CLASSE_800,
        GAIN_LTE2100_DBI,
        OUVERTURE_HORIZONTALE_700,
        OUVERTURE_VERTICALE_700,
        CLASSE_700,
        GAIN_UMTS900_DBI,
        GAIN_700_DB,
        CLASSE_LTE700,
        GAIN_LTE700_DBI,
        TILT_ELECTRIQUE_700,
        BANDE_DE_FREQUENCE_700_MHZ,
        ISOLATION_ENTRE_ACCES_700_DB,
        ROS_700,
        RAPPORT_AV_AR_700_DB,
        GAIN_LTE1800_DBI,
        GAIN_LTE3500_DBI,
        CLASSE_LTE3500,
        OUVERTURE_HORIZONTALE_3500,
        OUVERTURE_VERTICALE_3500,
        PUISSANCE_3500_W,
        TILT_ELECTRIQUE_3500,
        BANDE_DE_FREQUENCE_3500_MHZ,
        ISOLATION_ENTRE_ACCES_3500_DB,
        RAPPORT_AV_AR_3500_DB,
        ROS_3500,
        GAIN_NR2100_DBI,
        PERTE_DUPLEXEUR_2100_DB,
        PERTE_D_INSERTION_DB,
        PUISSANCE_NR2100_W,
        PUISSANCE_LTE2100_W,
        BANDE_DE_FREQUENCE_NR3500_MHZ,
        PUISSANCE_LTE1800_W,
        TILT_ELECTRIQUE_RN3500_,
        RAPPORT_AV_AR_NR3500_DB,
        BANDE_PASSANTE_MHZ,
        GAIN_2100_DB,
        ROS_NR3500,
        PUISSANCE_UMTS2200_W
    }
}

