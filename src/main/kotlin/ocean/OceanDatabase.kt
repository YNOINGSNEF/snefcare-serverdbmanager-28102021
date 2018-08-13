package ocean

import Database
import ocean.model.*
import java.io.File
import java.sql.Connection

object OceanDatabase : Database() {
    override val dumpFolder = "sfr" + File.separator + "ocean" + File.separator
    override val dbName = "ocean_prod"
    override val dbUser = "admin"
    override val dbPassword = "_023HUdu6yQar8n4P_1f"

    override val filesToProcess: List<OceanDataFile> = listOf(
            Bande(), // OCEAN_BANDE
            Biastee(), // OCEAN_BIASTEE
            CatalImpExp(), // OCEAN_CATALIMPEXP
            Constructeur(), // OCEAN_CONSTRUCTEUR
            DescParam(), // OCEAN_DESCPARAM
            Environmnt(), // OCEAN_ENVIRONMNT
            EtatDm(), // OCEAN_ETATDM
            EtatIe(), // OCEAN_ETATIE
            EtatSw(), // OCEAN_ETATSW
            FctAnt(), // OCEAN_FCTANT
            MqAnt(), // OCEAN_MQANT
            OperatorApplic(), // OCEAN_OPERATORAPPLIC
            ParamCi(), // OCEAN_PARAMCI
            PorteeParam(), // OCEAN_PORTEEPARAM
            RefDiTriplex(), // OCEAN_REFDITRIPLEX
            RefRet(), // OCEAN_REFRET
            RefTma(), // OCEAN_REFTMA
            Region(), // OCEAN_REGION
            RegDep(), // OCEAN_REG_DEP
            Techno(), // OCEAN_TECHNO
            TypCell(), // OCEAN_TYPCELL
            TypeDemande(), // OCEAN_TYPEDEMANDE
            TypeProjet(), // OCEAN_TYPEPROJET
            ZoneReseaux(), // OCEAN_ZONERESEAUX
            ZoneService(), // OCEAN_ZONESERVICE
            ZoneSpec(), // OCEAN_ZONESPEC
            ZoneVie(), // OCEAN_ZONEVIE
            AttCellDm(), // OCEAN_ATTCELLDM
            CatalAnt(), // OCEAN_CATALANT
            Systeme(), // OCEAN_SYSTEME
            BanSys(), // OCEAN_BAN_SYS
            CatalBaie(), // OCEAN_CATALBAIE
            CatalRep(), // OCEAN_CATALREP
            Combiner(), // OCEAN_COMBINER
            ConfChannelElem(), // OCEAN_CONFCHANNELELEM
            ConfPuissance(), // OCEAN_CONFPUISSANCE
            Frequence3g(), // OCEAN_FREQUENCE3G
            Frequence4g(), // OCEAN_FREQUENCE4G
            GrpParam(), // OCEAN_GRPPARAM
            JeuParamHisto(), // OCEAN_JEUPARAMHISTO
            ModBbu(), // OCEAN_MODBBU
            ModRadio(), // OCEAN_MODRADIO
            Nature(), // OCEAN_NATURE
            Palier(), // OCEAN_PALIER
            RefCellAttribut(), // OCEAN_REF_CELL_ATTRIBUT
            SautFrq(), // OCEAN_SAUTFRQ
            Site(), // OCEAN_SITE
            SiteIndex(), // OCEAN_SITE_INDEX
            Ot(), // OCEAN_OT
            SousTypeRncBsc(), // OCEAN_SOUSTYPE_RNCBSC
            TiltEl(), // OCEAN_TILTEL
            Antenne(), // OCEAN_ANTENNE
            Repeteur(), // OCEAN_REPETEUR
            Modele(), // OCEAN_MODELE
            JeuParam(), // OCEAN_JEUPARAM
            Parametre(), // OCEAN_PARAMETRE
            SousModele(), // OCEAN_SOUSMODELE
            ParamStdMod(), // OCEAN_PARAMSTDMOD
            JeuParSousMod(), // OCEAN_JEUPARSSMOD
            IntSousModele(), // OCEAN_INTSSMODELE
            Baie(), // OCEAN_BAIE
            DemandImpExp(), // OCEAN_DEMANDIMPEXP
            DemandModi(), // OCEAN_DEMANDMODI
            ModeRep(), // OCEAN_MODEREP
            OdtAnt(), // OCEAN_ODT_ANT
            OdtBai(), // OCEAN_ODT_BAI
            RncBsc(), // OCEAN_RNCBSC
            RbcLac(), // OCEAN_RBC_LAC
            RbcRac(), // OCEAN_RBC_RAC
            ParamSpec(), // OCEAN_PARAMSPEC
            ParamStdSousMod(), // OCEAN_PARAMSTDSSMOD
            RbcBaie(), // OCEAN_RBC_BAIE
            OdtRbc(), // OCEAN_ODT_RBC
            LacBaie(), // OCEAN_LAC_BAIE
            RacBaie(), // OCEAN_RAC_BAIE
            Cellule(), // OCEAN_CELLULE
            Voisinage(), // OCEAN_VOISINAGE
            CdmCell(), // OCEAN_CDM_CELL
            CelluleAtt(), // OCEAN_CELLULE_ATT
            CellVoisOptiers(), // OCEAN_CELL_VOIS_OPTIERS
            ConfBaie(), // OCEAN_CONFBAIE
            OdtCfb(), // OCEAN_ODT_CFB
            CfbCell(), // OCEAN_CFB_CELL
            Frequence2g(), // OCEAN_FREQUENCE2G
            ModAnt(), // OCEAN_MODANT
            Feeder(), // OCEAN_FEEDER
            OdtMda(), // OCEAN_ODT_MDA
            MdaCell() // OCEAN_MDA_CELL
    )

    private const val dumpFileName = "dump.zip"

    override fun retrieveNewDump(): Boolean = getLocalFile(dumpFileName).isFile

    override fun archiveDump() {
        getLocalFile(dumpFileName).copyTo(getArchiveFile("$formattedDate.zip"), true)
    }

    override fun prepareDump() {
        extractArchive(dumpFileName)
    }

    override fun executePostImportActions(dbConnection: Connection) {
        // Nothing to do
    }
}