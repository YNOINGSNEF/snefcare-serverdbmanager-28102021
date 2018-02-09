package model.files

import model.DataFile
import model.Region
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CelluleLte : DataFile() {
    override val fileName = "Cellule-LTE"
    override val fileHeader = Header::class.java

    override val tableName = "CELL_4G"
    override val tableHeader = listOf("eNodeB", "cell_name", "system", "carrier")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord, region: Region): Boolean {
        stmt.setString(1, record[Header.ENODEB])
        stmt.setString(2, record[Header.CELLULE])
        stmt.setString(3, record[Header.SYSTEM])
        stmt.setString(4, record[Header.PROPRIETAIRE])
        stmt.addBatch()
        return true
    }

    enum class Header {
        REGION,
        SOUS_REGION,
        DEPARTEMENT,
        SITE,
        ENODEB,
        CELLULE,
        ALIAS1,
        CREATEDDATE,
        CREATEDBY,
        LASTMODIFIEDDATE,
        LASTMODIFIEDBY,
        ETAT,
        CI,
        SECTORNUMBER,
        NUMCELL,
        ECI,
        SYSTEM,
        CELLTYPE,
        TAC_PREV,
        TAC_REEL,
        LAC_REEL,
        STATUSCHANGEDATE,
        ID_INTERNE,
        PLMN,
        ZP_CELL_CODE,
        PROPRIETAIRE
    }
}