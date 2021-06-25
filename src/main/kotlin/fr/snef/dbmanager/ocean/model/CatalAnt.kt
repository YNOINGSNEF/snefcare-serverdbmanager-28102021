package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class CatalAnt : OceanDataFile() {
    override val fileName = "OCEAN_CATALANT"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

        stmt.setInt(++index, record[Header.CTA_ID].toInt())
        stmt.setInt(++index, record[Header.MQA_ID].toInt())
        stmt.setInt(++index, record[Header.BAN_ID].toInt())
        stmt.setString(++index, record[Header.CTA_REFANT])
        stmt.setString(++index, record[Header.CTA_REFSFR])
        stmt.setString(++index, record[Header.CTA_BANDFQ])
        stmt.setInt(++index, record[Header.CTA_OPLANH].toInt())
        stmt.setInt(++index, record[Header.CTA_OPLANV].toInt())
        stmt.setNullableFloat(++index, record[Header.CTA_ROS900].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROS1800].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROSUMTS].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROSU900].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_GAIN900].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_GAIN1800].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_GAINUMTS].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_GAINU900].toFloatOrNull())
        stmt.setNullableInt(++index, record[Header.CTA_HANT].toIntOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_POIDSANT].toFloatOrNull())
        stmt.setNullableString(++index, record[Header.CTA_SFR900].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CTA_SFR1800].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CTA_SFRUMTS].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CTA_SFRU900].takeIf { it.isNotBlank() })
        stmt.setBoolean(++index, record[Header.CTA_DEF].toBool())
        stmt.setNullableString(++index, record[Header.CTA_DIRECT].takeIf { it.isNotBlank() })
        stmt.setNullableFloat(++index, record[Header.CTA_ROSL800].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROSL1800].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROSL2600].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_GAINL800].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_GAINL1800].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_GAINL2600].toFloatOrNull())
        stmt.setNullableString(++index, record[Header.CTA_SFRL800].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CTA_SFRL1800].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CTA_SFRL2600].takeIf { it.isNotBlank() })
        stmt.setNullableFloat(++index, record[Header.CTA_GAINL3500].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROSL3500].toFloatOrNull())
        stmt.setNullableString(++index, record[Header.CTA_SFRL3500].takeIf { it.isNotBlank() })
        stmt.setNullableFloat(++index, record[Header.CTA_GAINL700].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROSL700].toFloatOrNull())
        stmt.setNullableString(++index, record[Header.CTA_SFRL700].takeIf { it.isNotBlank() })
        stmt.setNullableFloat(++index, record[Header.CTA_GAINL1500].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROSL1500].toFloatOrNull())
        stmt.setNullableString(++index, record[Header.CTA_SFRL1500].takeIf { it.isNotBlank() })
        stmt.setNullableFloat(++index, record[Header.CTA_GAINL2100].toFloatOrNull())
        stmt.setNullableFloat(++index, record[Header.CTA_ROSL2100].toFloatOrNull())
        stmt.setNullableString(++index, record[Header.CTA_SFRL2100].takeIf { it.isNotBlank() })
    }

    enum class Header {
        CTA_ID,
        MQA_ID,
        BAN_ID,
        CTA_REFANT,
        CTA_REFSFR,
        CTA_BANDFQ,
        CTA_OPLANH,
        CTA_OPLANV,
        CTA_ROS900,
        CTA_ROS1800,
        CTA_ROSUMTS,
        CTA_ROSU900,
        CTA_GAIN900,
        CTA_GAIN1800,
        CTA_GAINUMTS,
        CTA_GAINU900,
        CTA_HANT,
        CTA_POIDSANT,
        CTA_SFR900,
        CTA_SFR1800,
        CTA_SFRUMTS,
        CTA_SFRU900,
        CTA_DEF,
        CTA_DIRECT,
        CTA_ROSL800,
        CTA_ROSL1800,
        CTA_ROSL2600,
        CTA_GAINL800,
        CTA_GAINL1800,
        CTA_GAINL2600,
        CTA_SFRL800,
        CTA_SFRL1800,
        CTA_SFRL2600,
        CTA_GAINL3500,
        CTA_ROSL3500,
        CTA_SFRL3500,
        CTA_GAINL700,
        CTA_ROSL700,
        CTA_SFRL700,
        CTA_GAINL1500,
        CTA_ROSL1500,
        CTA_SFRL1500,
        CTA_GAINL2100,
        CTA_ROSL2100,
        CTA_SFRL2100
    }
}