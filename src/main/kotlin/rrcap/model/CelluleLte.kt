package rrcap.model

import org.apache.commons.csv.CSVRecord
import rrcap.Region
import rrcap.RrcapDatafile
import java.sql.PreparedStatement

class CelluleLte(region: Region) : RrcapDatafile(region) {
    override val shortFileName = "Cellule-LTE"
    override val fileHeader = Header::class.java

    override val tableName = "CELL_4G"
    override val tableHeader = listOf("eNodeB", "cell_name", "system", "carrier")

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        stmt.setString(++index, record[Header.ENODEB])
        stmt.setString(++index, record[Header.CELLULE])
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