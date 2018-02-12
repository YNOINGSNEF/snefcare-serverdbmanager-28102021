package model.files.rrcap

import model.DataFile
import model.Region
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CelluleGsmDcs : DataFile() {
    override val fileName = "Cellule-GSMDCS"
    override val fileHeader = Header::class.java

    override val tableName = "CELL_2G"
    override val tableHeader = listOf("bts_nodeB", "cell_name", "alias1", "system", "carrier")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
        stmt.setString(1, record[Header.BTS])
        stmt.setString(2, record[Header.CELLULE])
        stmt.setString(3, record[Header.ALIAS1])
        stmt.setString(4, record[Header.SYSTEM])
        stmt.setString(5, record[Header.PROPRIETAIRE])
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