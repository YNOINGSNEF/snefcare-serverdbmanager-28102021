package rrcap.model

import org.apache.commons.csv.CSVRecord
import rrcap.Region
import rrcap.RrcapDatafile
import java.sql.PreparedStatement

class CelluleGsmDcs(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "Cellule-GSMDCS"
    override val fileHeader = Header::class.java

    override val tableName = "CELL_2G"
    override val tableHeader = listOf("bts_nodeB", "cell_name", "alias1", "system", "carrier")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        stmt.setString(++index, record[Header.BTS])
        stmt.setString(++index, record[Header.CELLULE])
        stmt.setString(++index, record[Header.ALIAS1])
        stmt.setString(++index, record[Header.SYSTEM])
        stmt.setString(++index, record[Header.PROPRIETAIRE])
        stmt.addBatch()
        return true
    }

    enum class Header {
        REGION,
        SOUS_REGION,
        DEPARTEMENT,
        SITE,
        BTS,
        CELLULE,
        ALIAS1,
        CREATEDDATE,
        CREATEDBY,
        LASTMODIFIEDDATE,
        LASTMODIFIEDBY,
        ETAT,
        CI,
        SECTORNUMBER,
        SYSTEM,
        CELLTYPE,
        LAC_PREV,
        LAC_REEL,
        STATUSCHANGEDATE,
        ID_INTERNE,
        RAC_PREV,
        RAC_REEL,
        PLMN,
        ZP_CELL_CODE,
        PROPRIETAIRE
    }
}