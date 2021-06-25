package fr.snef.dbmanager.rrcap.model

import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class NodeB(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "NodeB"
    override val fileHeader = Header::class.java

    override val tableName = "NODEB"
    override val tableHeader = listOf("nodeB", "zpt", "type", "manufacturer", "status", "system")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        stmt.setString(++index, record[Header.NODEB])
        stmt.setString(++index, record[Header.ZPT])
        stmt.setString(++index, record[Header.TYPE])
        stmt.setString(++index, record[Header.TYPE].removePrefix("NodeB "))
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
        NODEB,
        NODEB_ALIAS1,
        DEF,
        CREATEDDATE,
        LASTMODIFIEDDATE,
        CREATEDBY,
        LASTMODIFIEDBY,
        TYPE,
        STATUS,
        SYSTEM,
        VERSIONNUMBERLOG,
        BAIETYPE,
        STATUSCHANGEDATE,
        CONFCE_UMTS_REEL,
        CONFCE_UMTS_PREV,
        MODELE,
        ID_INTERNE,
        NODEB_ALIAS2,
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
        SECTORISATION,
        NOM_EXPLOITATION_2G,
        NOM_EXPLOITATION_3G,
        NOM_EXPLOITATION_4G
    }
}