package fr.snef.dbmanager.orange.model

import fr.snef.dbmanager.orange.OrangeDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class AntennaDetails(filename: String) : OrangeDataFile(filename) {

    companion object {
        private const val filePrefix = "NORIA_FLUX_GENERIQUE_CELLULE"
        private const val complementsString = "COMPLEMENT"

        fun from(fileName: String): AntennaDetails? {
            if (fileName.startsWith(filePrefix) && !fileName.contains(complementsString)) {
                return AntennaDetails(fileName)
            }
            return null
        }
    }

    override val fileHeader = Header::class.java

    override val queryType = QueryType.UPDATE
    override val tableName = Antenna.tableName
    override val tableHeader = Antenna.tableHeader

    override val updateSql: String
        get() = "UPDATE $tableName" +
                " INNER JOIN ${AntennaCell.tableName} ON $tableName.id = ${AntennaCell.tableName}.antenna_id" +
                " SET " +
                tableUpdateFields.joinToString(separator = ",", transform = { "$it=?" }) +
                " WHERE ${AntennaCell.tableName}.cell_id = ?"

    override val tableUpdateFields = listOf("sector_number", "azimuth", "hba")

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.SECTEUR].toInt())
        stmt.setInt(++index, record[Header.AZM_SYNOP].toInt())
        stmt.setFloat(++index, record[Header.HBA].toFloat())
        stmt.setString(++index, record[Header.ID])
    }

    enum class Header {
        ID,
        CELL_TYPE,
        NETWORK_ELEMENT_ID,
        NODE_ID,
        DESCRIPTION,
        DISMANTLED,
        OPERATION_PURPOSE,
        DEP_STATUS,
        NUM,
        CI,
        LCID,
        ECI,
        NCI,
        NET_STATUS,
        NAME,
        NET_NAME,
        NET_CODE,
        REL_CELL_ID,
        IDENT_SI,
        NIDT_NAME,
        LAC,
        UCID,
        COUV_LEVEL,
        COMBINEDCELL_000,
        CELLULE_S_COMBI_000,
        DISTNAME,
        IS_5G,
        UL5G,
        BAND,
        COUV,
        CLASS,
        CARACT,
        RS_CELL,
        DEP_ANTX,
        DEP_ANTY,
        DEP_ANTZ,
        TYP_ANT,
        CARRIER,
        AZM_SYNOP,
        ORIENT,
        SECTEUR,
        DATE_EXTINC,
        CARRIER_AGREGATI_000,
        MORPHOLOGIE,
        RS_OP_HOSTED,
        RS_OP_LEADER,
        RS_OP_LEADER_4G,
        CURL,
        SCRAMBLING_CODE,
        SECTEUR_OMC,
        CELL_Typage,
        RS_OP_HOSTED_4G,
        TYPE_ACCES,
        RS_CELL_4G,
        DATE_ALLU,
        CATM,
        EXT_COVERAGE,
        eDRX,
        MEST_PREV,
        FN8_INIT_DATE,
        DATE_DEM_PREV,
        DATE_MISE_MAINT,
        RET_INT_GR__000,
        DATE_FN5_1,
        DATE_MEST,
        DATE_FN8,
        DATE_FNSD,
        PCI,
        TAC,
        MIMO,
        MIMO_RS,
        RET_INSTAL,
        SERIAL_NB_RET,
        MCC,
        MNC,
        TILT_OMC,
        SAC,
        CID,
        DEBIT_HSDPA,
        DEBIT_HSUPA,
        DEBIT_UMTS,
        RAC,
        SERVICE_UMTS,
        NB_TRX_ACTIF,
        SERVICE_GSM,
        DDR_OMC,
        EARFCN_DL,
        UARFCN_DL,
        NRARFCN_DL,
        HBA,
        MARK_REF,
        CELL_PLAQUE,
        MARK_IDENT,
        BLOC,
        ZONE_VIE,
        CAT_ARCEP,
        OBJARCEP,
        CODE_MKT_P,
        PRIORITE_P,
        CODE_MKT_C,
        PRIORITE_C,
        CODE_MKT_A,
        PRIORITE_A,
        DEPT,
        INSEE,
        DCOMIRIS,
        VALIDATION,
        COMMENT,
        OFFRE_PRO_001,
        GTR_001,
        PCPMR_001,
        GP_TRAV,
        SAISON,
        EVENEMENTIEL,
        TRAF_ROAM,
        COMM,
        NCC,
        TG,
        USER_DATA,
        AICH_POWER,
        RNCID,
        FREQ_SSB,
        ANTENNA_SSB_PATTERN,
        SSB_FREQUENCY_AUTO,
        AZIMUTH_OPENNING_ANG,
        GSCN,
    }
}