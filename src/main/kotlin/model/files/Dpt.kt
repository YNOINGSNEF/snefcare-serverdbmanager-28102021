package model.files

import model.DataFile

class Dpt : DataFile() {
    override val fileName = "DPT"
    override val fileHeader = Header::class.java

    override val tableName = "TODO"
    override val tableHeader = listOf("TODO", "TODO")

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