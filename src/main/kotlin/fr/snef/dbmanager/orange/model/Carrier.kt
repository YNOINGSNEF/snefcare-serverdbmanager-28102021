package fr.snef.dbmanager.orange.model

enum class Carrier(val id: Int) {
    SFR(1),
    BYT(2),
    ORF(3),
    FRM(4);

    companion object {
        fun from(mcc: Int, mnc: Int) = when (mcc to mnc) {
            (208 to 10) -> SFR
            (208 to 20) -> BYT
            (208 to 1) -> ORF
            (208 to 15) -> FRM
            (208 to 2) -> ORF
            (208 to 8) -> SFR
            (208 to 9) -> SFR
            (208 to 11) -> SFR
            (208 to 13) -> SFR
            (208 to 16) -> FRM
            (208 to 21) -> BYT
            (208 to 88) -> BYT
            (208 to 91) -> ORF
            (208 to 95) -> ORF
            else -> {
                println("      > ERROR: Unknown carrier for MCC=$mcc, MNC=$mnc")
                null
            }
        }
    }
}