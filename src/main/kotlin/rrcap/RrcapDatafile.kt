package rrcap

import DataFile
import java.io.File

abstract class RrcapDatafile(protected val region: Region) : DataFile() {
    abstract val shortFileName: String
    override val fileName get() = region.name + File.separatorChar + region.name + "-" + shortFileName
    override val fileCharset = DataFile.CHARSET_ANSI
    override val fileExtension = "csv"

    protected fun String.extractSiteName() = "(\\d*)\\s*-\\s*(.*)".toRegex().matchEntire(this)?.groupValues?.get(2)?.takeIf { it.isNotBlank() }
    protected fun String.extractSiteG2R() = "(\\d*)\\s*-\\s*(.*)".toRegex().matchEntire(this)?.groupValues?.get(1)?.takeIf { it.isNotBlank() }
    protected fun String.extractBandwidth() = takeIf { it != "-" && it != "Not Specified" && !it.isBlank() }
}