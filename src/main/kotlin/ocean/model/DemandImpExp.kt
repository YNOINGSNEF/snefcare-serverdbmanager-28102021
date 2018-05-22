package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class DemandImpExp : OceanDataFile() {
    override val fileName = "OCEAN_DEMANDIMPEXP"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.DIE_ID].toInt())
        stmt.setInt(++index, record[Header.CIE_ID].toInt())
        stmt.setInt(++index, record[Header.EIE_ID].toInt())
        stmt.setInt(++index, record[Header.UTI_ID].toInt())
        stmt.setString(++index, record[Header.DIE_NOM])
        stmt.setString(++index, record[Header.DIE_COMMENTAIRE])
        stmt.setTimestamp(++index, record[Header.DIE_DATE_CREATION].toTimestamp())
        stmt.setTimestamp(++index, record[Header.DIE_DATE_LANCEMENT].toTimestamp())
        stmt.setString(++index, record[Header.DIE_FICENTREE])
        stmt.setString(++index, record[Header.DIE_FICSORTIE])
        stmt.setString(++index, record[Header.DIE_CR])
        stmt.setString(++index, record[Header.DIE_QUI])
        stmt.setTimestamp(++index, record[Header.DIE_QUAND].toTimestamp())
        stmt.setInt(++index, record[Header.DIE_TYPE_LANCEMENT].toInt())
        stmt.setInt(++index, record[Header.DIE_TYPE_TRAITEMENT].toInt())
    }

    enum class Header {
        DIE_ID,
        CIE_ID,
        EIE_ID,
        UTI_ID,
        DIE_NOM,
        DIE_COMMENTAIRE,
        DIE_DATE_CREATION,
        DIE_DATE_LANCEMENT,
        DIE_FICENTREE,
        DIE_FICSORTIE,
        DIE_CR,
        DIE_QUI,
        DIE_QUAND,
        DIE_TYPE_LANCEMENT,
        DIE_TYPE_TRAITEMENT
    }
}