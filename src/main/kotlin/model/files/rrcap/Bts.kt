package model.files.rrcap

import model.DataFile
import model.Region
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Bts : DataFile() {
    override val fileName = "Bts"
    override val fileHeader = Header::class.java

    override val tableName = "BTS"
    override val tableHeader = listOf("bts", "zpt")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
        stmt.setString(1, record[Header.BTS])
        stmt.setString(2, record[Header.ZPT])
        stmt.addBatch()
        return true
    }

    enum class Header {
        REGION,
        SOUS_REGION,
        DEPARTEMENT,
        SITE,
        ZPT,
        BTS,
        BTS_ALIAS_1,
        MODELE,
        CREATEDDATE,
        LASTMODIFIEDDATE,
        CREATEDBY,
        LASTMODIFIEDBY,
        TYPE,
        STATUS,
        COMBINERTYPE,
        SYSTEM,
        STATUSCHANGEDATE,
        BTS_ALIAS_2,
        BAIETYPE,
        ID_INTERNE,
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
        EMPLACEMENT,
        ADRESSE_IP,
        CONSTRUCTEUR
    }
}