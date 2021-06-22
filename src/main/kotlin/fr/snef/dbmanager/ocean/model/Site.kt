package fr.snef.dbmanager.ocean.model

import fr.snef.dbmanager.ocean.OceanDataFile
import org.apache.commons.csv.CSVRecord
import java.sql.PreparedStatement

class Site : OceanDataFile() {
    override val fileName = "OCEAN_SITE"
    override val fileHeader = Header::class.java

    override fun populateStatement(stmt: PreparedStatement, record: CSVRecord) {
        var index = 0

//        val x = record[Header.SIT_COOXSIM].toDouble() * 1000
//        val y = record[Header.SIT_COOYSIM].toDouble() * 1000
//
//        val (lat, lng) = lambert2toWgs84(x, y)
//
//        println("($x; $y) -> ($lat, $lng)")

        stmt.setInt(++index, record[Header.SIT_ID].toInt())
        stmt.setInt(++index, record[Header.DEP_NUM].toInt())
        stmt.setInt(++index, record[Header.REG_NUM].toInt())
        stmt.setNullableInt(++index, record[Header.ZGE_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.TPS_ID].toIntOrNull())
        stmt.setNullableString(++index, record[Header.SIT_NUMG2R].takeIf { it.isNotBlank() })
        stmt.setString(++index, record[Header.SIT_NOM])
        stmt.setFloat(++index, record[Header.SIT_COOXREEL].toFloat())
        stmt.setFloat(++index, record[Header.SIT_COOYREEL].toFloat())
        stmt.setInt(++index, record[Header.SIT_COOZREEL].toInt())
        stmt.setFloat(++index, record[Header.SIT_COOXSIM].toFloat())
        stmt.setFloat(++index, record[Header.SIT_COOYSIM].toFloat())
        stmt.setInt(++index, record[Header.SIT_COOZSIM].toInt())
        stmt.setString(++index, record[Header.SIT_LONGIT])
        stmt.setString(++index, record[Header.SIT_LATIT])
        stmt.setString(++index, record[Header.SIT_NSCADAS])
        stmt.setString(++index, record[Header.SIT_NPCADAS])
        stmt.setString(++index, record[Header.SIT_INSEE])
        stmt.setString(++index, record[Header.SIT_COMMENT])
        stmt.setString(++index, record[Header.SIT_QUI])
        stmt.setTimestamp(++index, record[Header.SIT_QUAND].toTimestamp())
        stmt.setString(++index, record[Header.SIT_ADRESSE])
        stmt.setString(++index, record[Header.SIT_COMPADR])
        stmt.setString(++index, record[Header.SIT_LIEUDIT])
        stmt.setString(++index, record[Header.SIT_CODPOST])
        stmt.setString(++index, record[Header.SIT_COMMUNE])
        stmt.setString(++index, record[Header.SIT_PROPR])
        stmt.setString(++index, record[Header.SIT_RESPONS])
        stmt.setString(++index, record[Header.SIT_TEL])
        stmt.setString(++index, record[Header.SIT_FAX])
        stmt.setInt(++index, record[Header.SIT_VERSION].toInt())
        stmt.setNullableString(++index, record[Header.SIT_ZPID].takeIf { it.isNotBlank() })
        stmt.setNullableInt(++index, record[Header.TYPE_SITE_RS_ID].toIntOrNull())
        stmt.setNullableInt(++index, record[Header.SIT_PHASE].toIntOrNull())
        stmt.setInt(++index, record[Header.OPP_ID].toInt())
        stmt.setInt(++index, record[Header.SIT_RS].toIntOrNull() ?: 0)
        stmt.setNullableInt(++index, record[Header.SIT_ZONERS].toIntOrNull())
        stmt.setString(++index, record[Header.SIT_PLQEXPL])
        stmt.setNullableBoolean(++index, record[Header.SIT_SANTE].toBoolOrNull())
        stmt.setNullableString(++index, record[Header.CONF_HARD].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.CONF_INTERNE].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PLAQ_CONCEPT_ID].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PLAQ_CONCEPT_LABEL].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PLAQ_DEPL_LABEL].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.PLAQ_DEPL_ID].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.SPECIAUX_LABEL].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.FRONTIERE].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.SIT_ZONE].takeIf { it.isNotBlank() })
        stmt.setNullableString(++index, record[Header.ERS_ID].takeIf { it.isNotBlank() })
    }

    enum class Header {
        SIT_ID,
        DEP_NUM,
        REG_NUM,
        ZGE_ID,
        TPS_ID,
        SIT_NUMG2R,
        SIT_NOM,
        SIT_COOXREEL,
        SIT_COOYREEL,
        SIT_COOZREEL,
        SIT_COOXSIM,
        SIT_COOYSIM,
        SIT_COOZSIM,
        SIT_LONGIT,
        SIT_LATIT,
        SIT_NSCADAS,
        SIT_NPCADAS,
        SIT_INSEE,
        SIT_COMMENT,
        SIT_QUI,
        SIT_QUAND,
        SIT_ADRESSE,
        SIT_COMPADR,
        SIT_LIEUDIT,
        SIT_CODPOST,
        SIT_COMMUNE,
        SIT_PROPR,
        SIT_RESPONS,
        SIT_TEL,
        SIT_FAX,
        SIT_VERSION,
        SIT_ZPID,
        TYPE_SITE_RS_ID,
        SIT_PHASE,
        OPP_ID,
        SIT_RS,
        SIT_ZONERS,
        SIT_PLQEXPL,
        SIT_SANTE,
        CONF_HARD,
        CONF_INTERNE,
        PLAQ_CONCEPT_ID,
        PLAQ_CONCEPT_LABEL,
        PLAQ_DEPL_LABEL,
        PLAQ_DEPL_ID,
        SPECIAUX_LABEL,
        FRONTIERE,
        SIT_ZONE,
        ERS_ID
    }
}