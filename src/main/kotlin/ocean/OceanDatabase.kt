package ocean

import Database
import ocean.model.Antenne
import java.io.File

object OceanDatabase : Database() {

    override val dumpFolder = "sfr" + File.separator + "ocean" + File.separator
    override val dbName = "ocean_tmp"
    override val dbUser = "admin"
    override val dbPassword = "_023HUdu6yQar8n4P_1f"

    override val filesToProcess: List<OceanDataFile> = listOf(
            Antenne() // OCEAN_ANTENNE
//            Attcelldm(), // OCEAN_ATTCELLDM
//            Baie(), // OCEAN_BAIE
//            Bande(), // OCEAN_BANDE
//            BanSys(), // OCEAN_BAN_SYS
//            Biastee(), // OCEAN_BIASTEE
//            Catalant(), // OCEAN_CATALANT
//            Catalbaie(), // OCEAN_CATALBAIE
//            Catalimpexp(), // OCEAN_CATALIMPEXP
//            Catalrep(), // OCEAN_CATALREP
//            CdmCell(), // OCEAN_CDM_CELL
//            Cellule(), // OCEAN_CELLULE
//            CelluleAtt(), // OCEAN_CELLULE_ATT
//            CellVoisOptiers(), // OCEAN_CELL_VOIS_OPTIERS
//            CfbCell(), // OCEAN_CFB_CELL
//            Combiner(), // OCEAN_COMBINER
//            Confbaie(), // OCEAN_CONFBAIE
//            Confchannelelem(), // OCEAN_CONFCHANNELELEM
//            Confpuissance(), // OCEAN_CONFPUISSANCE
//            Constructeur(), // OCEAN_CONSTRUCTEUR
//            Demandimpexp(), // OCEAN_DEMANDIMPEXP
//            Demandmodi(), // OCEAN_DEMANDMODI
//            Descparam(), // OCEAN_DESCPARAM
//            Environmnt(), // OCEAN_ENVIRONMNT
//            Etatdm(), // OCEAN_ETATDM
//            Etatie(), // OCEAN_ETATIE
//            Etatsw(), // OCEAN_ETATSW
//            Fctant(), // OCEAN_FCTANT
//            Feeder(), // OCEAN_FEEDER
//            Frequence2g(), // OCEAN_FREQUENCE2G
//            Frequence3g(), // OCEAN_FREQUENCE3G
//            Frequence4g(), // OCEAN_FREQUENCE4G
//            Grpparam(), // OCEAN_GRPPARAM
//            Intssmodele(), // OCEAN_INTSSMODELE
//            Jeuparam(), // OCEAN_JEUPARAM
//            Jeuparamhisto(), // OCEAN_JEUPARAMHISTO
//            Jeuparssmod(), // OCEAN_JEUPARSSMOD
//            LacBaie(), // OCEAN_LAC_BAIE
//            MdaCell(), // OCEAN_MDA_CELL
//            Modant(), // OCEAN_MODANT
//            Modbbu(), // OCEAN_MODBBU
//            Modele(), // OCEAN_MODELE
//            Moderep(), // OCEAN_MODEREP
//            Modradio(), // OCEAN_MODRADIO
//            Mqant(), // OCEAN_MQANT
//            Nature(), // OCEAN_NATURE
//            OdtAnt(), // OCEAN_ODT_ANT
//            OdtBai(), // OCEAN_ODT_BAI
//            OdtCfb(), // OCEAN_ODT_CFB
//            OdtMda(), // OCEAN_ODT_MDA
//            OdtRbc(), // OCEAN_ODT_RBC
//            Operatorapplic(), // OCEAN_OPERATORAPPLIC
//            Ot(), // OCEAN_OT
//            Palier(), // OCEAN_PALIER
//            Paramci(), // OCEAN_PARAMCI
//            Parametre(), // OCEAN_PARAMETRE
//            Paramspec(), // OCEAN_PARAMSPEC
//            Paramstdmod(), // OCEAN_PARAMSTDMOD
//            Paramstdssmod(), // OCEAN_PARAMSTDSSMOD
//            Porteeparam(), // OCEAN_PORTEEPARAM
//            RacBaie(), // OCEAN_RAC_BAIE
//            RbcBaie(), // OCEAN_RBC_BAIE
//            RbcLac(), // OCEAN_RBC_LAC
//            RbcRac(), // OCEAN_RBC_RAC
//            Refditriplex(), // OCEAN_REFDITRIPLEX
//            Refret(), // OCEAN_REFRET
//            Reftma(), // OCEAN_REFTMA
//            RefCellAttribut(), // OCEAN_REF_CELL_ATTRIBUT
//            Region(), // OCEAN_REGION
//            RegDep(), // OCEAN_REG_DEP
//            Repeteur(), // OCEAN_REPETEUR
//            Rncbsc(), // OCEAN_RNCBSC
//            Sautfrq(), // OCEAN_SAUTFRQ
//            Site(), // OCEAN_SITE
//            SiteIndex(), // OCEAN_SITE_INDEX
//            Sousmodele(), // OCEAN_SOUSMODELE
//            SoustypeRncbsc(), // OCEAN_SOUSTYPE_RNCBSC
//            Systeme(), // OCEAN_SYSTEME
//            Techno(), // OCEAN_TECHNO
//            Tiltel(), // OCEAN_TILTEL
//            Typcell(), // OCEAN_TYPCELL
//            Typedemande(), // OCEAN_TYPEDEMANDE
//            Typeprojet(), // OCEAN_TYPEPROJET
//            Voisinage(), // OCEAN_VOISINAGE
//            Zonereseaux(), // OCEAN_ZONERESEAUX
//            Zoneservice(), // OCEAN_ZONESERVICE
//            Zonespec(), // OCEAN_ZONESPEC
//            Zonevie ()// OCEAN_ZONEVIE
    )

    private const val dumpFileName = "dump.zip"

    override fun retrieveNewDump(): Boolean = getLocalFile(dumpFileName).isFile

    override fun archiveDump() {
        getLocalFile(dumpFileName).copyTo(getArchiveFile("$formattedDate.zip"), true)
    }

    override fun prepareDump() {
        extractArchive(dumpFileName)
    }
}