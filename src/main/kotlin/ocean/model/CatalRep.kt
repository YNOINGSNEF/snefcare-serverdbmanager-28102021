package ocean.model

import ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CatalRep : OceanDataFile() {
    override val fileName = "OCEAN_CATALREP"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CRE_ID].toInt())
        stmt.setInt(++index, record[Header.BAN_ID].toInt())
        stmt.setString(++index, record[Header.CRE_REF])
        stmt.setString(++index, record[Header.CRE_FOURN])
        stmt.setString(++index, record[Header.CRE_TYPE])
        stmt.setString(++index, record[Header.CRE_NATURE])
        stmt.setString(++index, record[Header.CRE_GENRE])
        stmt.setNullableFloat(++index, record[Header.CRE_GAIN900].replace(',', '.').toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAIN1800].replace(',', '.').toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINUMTS].replace(',', '.').toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINU900].replace(',', '.').toFloatOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUT900].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUT1800].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTUMTS].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTU900].toIntOrNull())
        stmt.setBoolean(++index, record[Header.CRE_DEF].toBool())
        stmt.setNullableFloat(++index, record[Header.CRE_PROPA_DELAY].replace(',', '.').toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINL800].replace(',', '.').toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINL1800].replace(',', '.').toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINL2600].replace(',', '.').toFloatOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTL800].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTL1800].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTL2600].toIntOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINL3500].replace(',', '.').toFloatOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTL3500].toIntOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINL700].replace(',', '.').toFloatOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTL700].toIntOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINL1500].replace(',', '.').toFloatOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTL1500].toIntOrNull())
        stmt.setNullableFloat(++index, record[Header.CRE_GAINL2100].replace(',', '.').toFloatOrNull())
        stmt.setNullableInt(++index, record[Header.CRE_POUTL2100].toIntOrNull())
    }

    enum class Header {
        CRE_ID,
        BAN_ID,
        CRE_REF,
        CRE_FOURN,
        CRE_TYPE,
        CRE_NATURE,
        CRE_GENRE,
        CRE_GAIN900,
        CRE_GAIN1800,
        CRE_GAINUMTS,
        CRE_GAINU900,
        CRE_POUT900,
        CRE_POUT1800,
        CRE_POUTUMTS,
        CRE_POUTU900,
        CRE_DEF,
        CRE_PROPA_DELAY,
        CRE_GAINL800,
        CRE_GAINL1800,
        CRE_GAINL2600,
        CRE_POUTL800,
        CRE_POUTL1800,
        CRE_POUTL2600,
        CRE_GAINL3500,
        CRE_POUTL3500,
        CRE_GAINL700,
        CRE_POUTL700,
        CRE_GAINL1500,
        CRE_POUTL1500,
        CRE_GAINL2100,
        CRE_POUTL2100
    }
}