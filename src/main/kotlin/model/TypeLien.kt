package model

enum class TypeLien(val label: String) {
    Ethernet("Ethernet"),
    VLAN("VLAN"),
    Optical("Optical"),
    MicrowaveEthernet("Microwave Ethernet"),
    MLPPP("MLPPP"),
    LeasedLine("Leased Line"),
    Copper("Copper"),
    Microwave("Microwave"),
    SuperLink("SuperLink"),
    Jarretiere("Jarretiere"),
    Ima("Ima"),
    E1("N*E1 Bearer"),
    PDH("PDH Bearer")
}