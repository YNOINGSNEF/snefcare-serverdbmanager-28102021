package rrcap.model

import org.apache.commons.csv.CSVRecord
import rrcap.Region
import rrcap.RrcapDatafile
import java.sql.PreparedStatement

class Bts(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "Bts"
    override val fileHeader = Header::class.java

    override val tableName = "BTS"
    override val tableHeader = listOf("bts", "zpt", "type", "status", "system")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        stmt.setString(++index, record[Header.BTS])
        stmt.setString(++index, record[Header.ZPT])
        stmt.setString(++index, record[Header.TYPE])
        stmt.setString(++index, record[Header.STATUS])
        stmt.setString(++index, record[Header.SYSTEM])
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