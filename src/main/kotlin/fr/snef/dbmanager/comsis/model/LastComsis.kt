package fr.snef.dbmanager.comsis.model

import fr.snef.dbmanager.DataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class LastComsis : DataFile() {
    override val fileName = "last_comsis"
    override val fileHeader = Header::class.java
    override val fileCharset = CHARSET_ANSI
    override val fileExtension = "csv"

    override val tableName = "LAST_COMSIS"
    override val tableHeader = listOf(
            "region",
            "num_g2r",
            "nom_g2r",
            "numero_otc",
            "numero_anfr",
            "types_de_demande",
            "version",
            "date_derniere_maj",
            "statut_comsis",
            "date_statut",
            "commentaires_statut",
            "decision_anfr",
            "date_motif_anfr",
            "motif_anfr",
            "station_declar",
            "crozon",
            "idjv",
            "id_leader",
            "id_anfr_site_leader"
    )

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        var index = 0
        try {
            val g2r = record[Header.NUM_G2R].padStartG2R() ?: return false

            stmt.setString(++index, record[Header.REGION])
            stmt.setString(++index, g2r)
            stmt.setString(++index, record[Header.NOM_G2R])
            stmt.setNullableInt(++index, record[Header.NUMERO_OTC].toIntOrNull())
            stmt.setString(++index, record[Header.NUMERO_ANFR])
            stmt.setString(++index, record[Header.TYPES_DE_DEMANDE])
            stmt.setInt(++index, record[Header.VERSION].toInt())
            stmt.setNullableString(++index, record[Header.DATE_DERNIERE_MAJ].takeIf { it.isNotBlank() })
            stmt.setString(++index, record[Header.STATUT_COMSIS])
            stmt.setNullableString(++index, record[Header.DATE_STATUT].takeIf { it.isNotBlank() })
            stmt.setString(++index, record[Header.COMMENTAIRES_STATUT])
            stmt.setNullableString(++index, record[Header.DECISION_ANFR].takeIf { it.isNotBlank() })
            stmt.setNullableString(++index, record[Header.DATE_MOTIF_ANFR].takeIf { it.isNotBlank() })
            stmt.setString(++index, record[Header.MOTIF_ANFR])
            stmt.setString(++index, record[Header.STATION_DECLAR].take(1))
            stmt.setNullableBoolean(++index, record[Header.CROZON].takeIf { it.isNotBlank() }?.contentEquals("1"))
            stmt.setNullableInt(++index, record[Header.IDJV].toIntOrNull())
            stmt.setNullableInt(++index, record[Header.ID_LEADER].toIntOrNull())
            stmt.setNullableInt(++index, record[Header.ID_ANFR_SITE_LEADER].toIntOrNull())

            stmt.addBatch()
            return true
        } catch (ex: NumberFormatException) {
            stmt.clearParameters()
            return false
        } catch (ex: TypeCastException) {
            stmt.clearParameters()
            return false
        }
    }

    private fun String.padStartG2R(): String? = when (length) {
        6, 10 -> this
        5 -> padStart(6, '0')
        9 -> padStart(10, '0')
        else -> null
    }

    enum class Header {
        REGION,
        NUM_G2R,
        NOM_G2R,
        NUMERO_OTC,
        NUMERO_ANFR,
        TYPES_DE_DEMANDE,
        VERSION,
        DATE_DERNIERE_MAJ,
        STATUT_COMSIS,
        DATE_STATUT,
        COMMENTAIRES_STATUT,
        DECISION_ANFR,
        DATE_MOTIF_ANFR,
        MOTIF_ANFR,
        STATION_DECLAR,
        CROZON,
        IDJV,
        ID_LEADER,
        ID_ANFR_SITE_LEADER
    }
}
