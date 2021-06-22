package fr.snef.dbmanager.orange

import fr.snef.dbmanager.DataFile
import org.apache.commons.csv.CSVRecord
import java.security.InvalidParameterException
import java.sql.PreparedStatement
import java.text.ParseException

abstract class OrangeDataFile(override val fileName: String) : DataFile() {
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

    abstract fun populateStatement(stmt: PreparedStatement, record: CSVRecord)
}
