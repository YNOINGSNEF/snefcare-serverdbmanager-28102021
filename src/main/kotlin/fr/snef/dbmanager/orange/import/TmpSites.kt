package fr.snef.dbmanager.orange.import

import fr.snef.dbmanager.orange.OrangeImportDataFile

class TmpSites(fileNames: List<String>, dumpFolderPath: String) : OrangeImportDataFile(dumpFolderPath) {

    companion object {
        private const val filePrefix = "NORIA_FLUX_GENERIQUE_SITE"

        fun from(fileNames: List<String>, dumpFolderPath: String) = TmpSites(
            fileNames.filter { it.startsWith(filePrefix) && it.contains(prevSuffix) },
            dumpFolderPath
        )
    }

    override val tableName = "TMP_SITE"

    override val createTemporaryTableQuery = """
        CREATE TABLE $tableName (
            ID              INT AUTO_INCREMENT PRIMARY KEY,
        	SITE_ID	        INT NOT NULL,
        	GEO_CODE		VARCHAR(10) NOT NULL,
        	SITE_TYPE		VARCHAR(10) NOT NULL,
        	SITE_NAME		VARCHAR(40) NOT NULL,
            X_COORDINATE	INT NOT NULL,
        	Y_COORDINATE	INT NOT NULL,
        	Z_COORDINATE	INT NOT NULL,
            IS_PREV         BOOLEAN NOT NULL
        );
    """

    override val createIndexesQueries = listOf(
        "ALTER TABLE $tableName ADD INDEX index2 (SITE_ID);"
    )

    override val populateTemporaryTableQueries = fileNames.map { fileName ->
        return@map """
                LOAD DATA LOCAL INFILE '${fullPath(fileName)}'
                INTO TABLE $tableName
                FIELDS TERMINATED BY ';'
                LINES TERMINATED BY '\n'
                IGNORE 1 LINES
                (
                    @ID,
                    @GEO_CODE,
                    @SITE_TYPE,
                    @SITE_NAME,
                    @X_COORDINATE,
                    @Y_COORDINATE,
                    @Z_COORDINATE,
                    @ADDRESS_1,
                    @ADDRESS_2,
                    @ADDRESS_3,
                    @ZIPCODE,
                    @CITY,
                    @INSEE,
                    @SECURITY,
                    @COMMENT,
                    @INACCESSIBILITY_PERIOD,
                    @NWH_ACCESS,
                    @NACELLE_REQUIRED,
                    @NACELLE_USE,
                    @DR_CODE,
                    @DR_NAME,
                    @UR_CODE,
                    @UR_NAME,
                    @END_ACTIVE_DATE,
                    @ZP_SITE,
                    @LONGITUDE_WGS84,
                    @LATITUDE_WGS84,
                    @CODE_PRG_REG,
                    @CODE_ZONE_REG,
                    @GEST_SITE,
                    @FLAG
                )
                SET SITE_ID = @ID,
                    GEO_CODE = @GEO_CODE,
                    SITE_TYPE = @SITE_TYPE,
                    SITE_NAME = @SITE_NAME,
                    X_COORDINATE = @X_COORDINATE,
                    Y_COORDINATE = @Y_COORDINATE,
                    Z_COORDINATE = @Z_COORDINATE,
                    IS_PREV = IF(@FLAG = 'PREV', true, false);
            """
    }
}
