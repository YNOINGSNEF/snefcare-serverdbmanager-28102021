package rrcap.model

import org.apache.commons.csv.CSVRecord
import rrcap.Region
import rrcap.RrcapDatafile
import rrcap.Status
import rrcap.TypeLien
import java.sql.PreparedStatement

class Dpt(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "DPT"
    override val fileHeader = Header::class.java

    override val tableName = "DPT"
    override val tableHeader = listOf(
            "region_code",
            "abis_iub_iur",
            "bts_nodeB_rnc2",
            "route_number",
            "route_sequence",
            "lien",
            "train_fh",
            "site1",
            "node1",
            "port1",
            "site2",
            "node2",
            "port2",
            "bandwidth",
            "type",
            "status"
    )

    override val onDuplicateKeySql = "ON DUPLICATE KEY UPDATE duplicates_count = duplicates_count + 1"

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            val routeSeq = record[Header.SEQUENCE]
            val routeNum = record[Header.NB_ROUTE]

            stmt.setString(++index, region.name)
            stmt.setString(++index, record[Header.ABIS_IUB_IUR])
            stmt.setString(++index, record[Header.BTS_NODEB_RNC2])
            stmt.setInt(++index, routeNum.takeIf { it.isNotBlank() }?.toInt() ?: 1)
            stmt.setInt(++index, routeSeq.takeIf { it != "?" }?.toInt() ?: -1)
            stmt.setString(++index, record[Header.LIEN])
            stmt.setString(++index, record[Header.TRAIN_FH])
            stmt.setNullableString(++index, record[Header.SITE1].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD1])
            stmt.setNullableString(++index, record[Header.PORT1].takeIf { it.isNotBlank() })
            stmt.setNullableString(++index, record[Header.SITE2].extractSiteG2R())
            stmt.setString(++index, record[Header.NOEUD2])
            stmt.setNullableString(++index, record[Header.PORT2].takeIf { it.isNotBlank() })
            stmt.setNullableString(++index, record[Header.BANDWIDTH].extractBandwidth())
            stmt.setString(++index, TypeLien.from(record[Header.TYPE_LIEN]).label)
            stmt.setString(++index, Status.from(record[Header.ETAT_LIEN]).label)
            stmt.addBatch()
            return true
        } catch (ex: NumberFormatException) {
            stmt.clearParameters()
            return false
        } catch (ex: TypeCastException) {
            stmt.clearParameters()
            return false
        }
    }

    enum class Header {
        REGION,
        ABIS_IUB_IUR,
        SEQUENCE,
        SITE_BSCBAY_RNC,
        BSCBAY_RNC,
        TYPE_BSCBAY_RNC,
        PORT_BSCBAY_RNC,
        SITE_BTS_NODEB_RNC2,
        BTS_NODEB_RNC2,
        PORT_BTS_NODEB_RNC2,
        ETAT_ABIS_IUB_IUR,
        BAND_ABIS_IUB_IUR,
        RESOLT_ABIS_IUB_IUR,
        LIEN,
        SITE1,
        NOEUD1,
        PORT1,
        SITE2,
        NOEUD2,
        PORT2,
        TYPE_LIEN,
        BANDWIDTH,
        PDHDEF,
        ETAT_LIEN,
        SUPPLIER,
        DISTSFR,
        DISTFT,
        SUBLINKTYPE,
        COMMERCIALID,
        TECHNICALID,
        STATUSCHANGEDATE,
        TRAIN_FH,
        SECTOR_BTS,
        TYPE_BTS_NODEB_RNC2,
        COMBINER_BTS,
        TYPE_BAIE_NODEB,
        CONFIGURATION_MUX,
        CONF_RADIO_REEL,
        CONF_RADIO_PREV,
        MODELE_NODEB,
        TRX_GSM_PREV,
        TRX_GSM_REEL,
        TRX_DCS_PREV,
        TRX_DCS_REEL,
        HR_GSM_PREV,
        HR_GSM_REEL,
        HR_DCS_PREV,
        HR_DCS_REEL,
        TRXEDGE_GSM_PREV,
        TRXEDGE_GSM_REEL,
        TRXEDGE_DCS_PREV,
        TRXEDGE_DCS_REEL,
        ALIAS2BSCBAY,
        ALIAS2BTS,
        NB_ROUTE,
        ALIAS3GBTS,
        RELATIVENAME,
        NBRE_DE_PAIRES_DE_CARTE_NPGEP,
        REPARTITION_STATIQUE,
        SRN,
        SN,
        SSN
    }
}