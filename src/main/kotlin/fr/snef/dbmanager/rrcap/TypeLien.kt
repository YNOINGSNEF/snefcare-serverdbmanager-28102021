package fr.snef.dbmanager.rrcap

enum class TypeLien(val label: String, val alternatives: List<String> = emptyList()) {
    Ethernet("Ethernet", listOf("Ethernet Bearer")),
    VLAN("VLAN"),
    Optical("Optical", listOf("Optical Bearer")),
    MicrowaveEthernet("Microwave Ethernet", listOf("Microwave Ethernet Bearer")),
    MLPPP("MLPPP"),
    LeasedLine("Leased Line"),
    Copper("Copper"),
    Microwave("Microwave", listOf("Microwave Bearer")),
    SuperLink("SuperLink", listOf("Super Link")),
    Jarretiere("Jarretiere"),
    Ima("Ima"),
    PortChannel("Port Channel"),
    E1("N*E1 Bearer"),
    PDH("PDH Bearer");

    companion object {
        @Throws(TypeCastException::class)
        fun from(str: String) = if (str.isBlank()) {
            Jarretiere
        } else {
            TypeLien.values().firstOrNull { type ->
                type.alternatives.plus(type.label).containsIgnoreCase(str)
            } ?: throw TypeCastException()
        }

        private fun List<String>.containsIgnoreCase(str: String) = firstOrNull { str.equals(it, true) } != null
    }
}