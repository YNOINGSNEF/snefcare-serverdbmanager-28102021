package model.files

import model.DataFile

class DptMlppp : DataFile() {
    override val fileName = "DPT-MLPPP"
    override val fileHeader = Header::class.java

    override val tableName = "TODO"
    override val tableHeader = listOf("TODO", "TODO")

    enum class Header {
        REGION,
        MLPPP,
        SITE_MLPPP_1,
        NODE_MLPPP_1,
        SITE_MLPPP_2,
        NODE_MLPPP_2,
        STATUS,
        BANDWIDTH_TH,
        RESOLUTION,
        LIEN,
        SITE_1,
        NOEUD_1,
        PORT_1,
        SITE_2,
        NOEUD_2,
        PORT_2,
        TYPE,
        BANDWIDTH,
        DEF_PDH,
        ETAT,
        SUPPLIER,
        DISTSFR,
        DISTFT,
        SUBLINKTYPE,
        COMMERCIALID,
        TECHNICALID,
        STATUSCHANGEDATE,
        NOEUD_1_TRANS,
        PORT_1_TRANS,
        NOEUD_2_TRANS,
        PORT_2_TRANS,
        ALIAS2_RNC1,
        ALIAS2_RNC2,
        NOTES,
        TRAINFH,
        RELATIVE_NAME,
        SEQUENCE_NUMBER
    }
}