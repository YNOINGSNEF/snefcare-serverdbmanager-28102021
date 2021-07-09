package fr.snef.dbmanager.orange

import fr.snef.dbmanager.DataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

abstract class OrangeDataFile : DataFile() {

    override val fileName get() = throw UnsupportedOperationException()
    override val fileHeader get() = throw UnsupportedOperationException()
    override val fileCharset = CHARSET_ANSI
    override val fileExtension = "csv"

    override val tableHeader get() = throw UnsupportedOperationException()

    override fun addBatch(stmt: PreparedStatement, record: CSVRecord) = throw UnsupportedOperationException()
}
