package model.files

import model.DataFile

class S1Bearer : DataFile() {
    override val fileName = "S1Bearer"
    override val fileHeader = Header::class.java

    override val tableName = "TODO"
    override val tableHeader = listOf("TODO", "TODO")

    enum class Header {
        REGION,
        S1_NAME,
        SITE_MME,
        MME_NAME,
        MME_PORT,
        SITE_ENODEB,
        ENODEB_NAME,
        ENODEB_PORT,
        ENODEB_ALIAS_4G,
        SYSTEM,
        CREATED_DATE,
        CREATED_BY,
        LAST_MODIFIED_DATE,
        LAST_MODIFIED_BY,
        PROVISION_STATUS,
        RESOLUTION_STATUS,
        BANDWIDTH
    }
}