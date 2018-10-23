package fr.snef.dbmanager.rrcap.model

import org.apache.commons.csv.CSVRecord
import fr.snef.dbmanager.rrcap.Region
import fr.snef.dbmanager.rrcap.RrcapDatafile
import java.sql.PreparedStatement

class ENodeB(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "eNodeB"
    override val fileHeader = Header::class.java

    override val tableName = "ENODEB"
    override val tableHeader = listOf("nodeB", "zpt", "nodeB_alias_4g", "site", "type", "system", "baie_type", "modele", "emplacement")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        return try {
            stmt.setString(++index, record[Header.NODEB])
            stmt.setString(++index, record[Header.ZPT])
            stmt.setString(++index, record[Header.NODEB_ALIAS4G])
            stmt.setString(++index, record[Header.SITE_G2R])
            stmt.setString(++index, record[Header.TYPE])
            stmt.setString(++index, record[Header.SYSTEM])
            stmt.setString(++index, record[Header.BAIETYPE])
            stmt.setString(++index, record[Header.MODELE])
            stmt.setInt(++index, record[Header.EMPLACEMENT].toInt())
            stmt.addBatch()
            true
        } catch (ex: TypeCastException) {
            stmt.clearParameters()
            false
        }
    }

    enum class Header {
        REGION,
        SOUS_REGION,
        DEPARTEMENT,
        SITE_G2R,
        SITE,
        ZPT,
        NODEB,
        NODEB_ALIAS1,
        NODEB_ALIAS4G,
        ENODEBID,
        DEF,
        CREATEDDATE,
        LASTMODIFIEDDATE,
        CREATEDBY,
        LASTMODIFIEDBY,
        TYPE,
        SYSTEM,
        VERSIONNUMBERLOG,
        BAIETYPE,
        STATUSCHANGEDATE,
        MODELE,
        ID_INTERNE,
        EMPLACEMENT
    }
}