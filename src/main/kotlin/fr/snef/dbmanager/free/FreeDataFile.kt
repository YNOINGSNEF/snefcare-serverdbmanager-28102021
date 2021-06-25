package fr.snef.dbmanager.free

import fr.snef.dbmanager.DataFile
import fr.snef.dbmanager.free.model.Carrier
import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement
import java.text.ParseException

abstract class FreeDataFile(
        override val fileName: String
) : DataFile() {
    override val fileHeader = Header::class.java
    override val fileCharset = CHARSET_ANSI
    override val fileExtension = "csv"

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord): Boolean {
        return try {
            populateStatement(stmt, record)
            stmt.addBatch()
            true
        } catch (ex: NumberFormatException) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            false
        } catch (ex: ParseException) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            false
        } catch (ex: TypeCastException) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            return false
        } catch (ex: InvalidParameterException) {
            // Ex: on a essayé de créer une cellule 3G sur une ligne contenant une cellule 4G, on ignore donc l'erreur
            // Ex: aucun tilt n'a été renseigné pour la cellule
            stmt.clearParameters()
            true
        } catch (ex: IllegalArgumentException) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            false
        } catch (ex: Exception) {
            println("        > An error occurred : ${ex::class.java.simpleName} ${ex.message}")
            stmt.clearParameters()
            false
        }
    }

    protected fun String.extractCarrierId(): Int = if (this == "FRM") Carrier.FRM.id else TODO("Unsupported carrier")

    abstract fun populateStatement(stmt: PreparedStatement, record: CSVRecord)

    enum class Header {
        NOM_SITE,
        LATITUDE,
        LONGITUDE,
        ALTITUDE_MSL,
        NOM_CELLULE,
        NUMERO_SECTEUR,
        AZIMUT,
        REFERENCE,
        CONSTRUCTEUR,
        HAUTEUR_BASE,
        TILT_U900,
        TILT_U2100,
        TILT_L700,
        TILT_L800,
        TILT_L1800,
        TILT_L2600,
        NUM_CI,
        LAC,
        SCRAMBLING_CODE,
        ECI,
        TAC,
        PCI,
        SYSTEME,
        TYPE,
        FREQUENCE_DL,
        PW,
        OPERATEUR
    }
}